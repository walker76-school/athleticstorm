/******************************************************************************
 *
 * DataPopulator.java
 *
 * author: Ian laird
 *
 * Created 4/4/20
 *
 * © 2020
 *
 ******************************************************************************/


package edu.baylor.ecs.athleticstorm.startUp;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coordinator.CoordinatorDTO;
import edu.baylor.ecs.athleticstorm.DTO.player.AdvancedPlayerDTO;
import edu.baylor.ecs.athleticstorm.DTO.player.PlayerDTO;
import edu.baylor.ecs.athleticstorm.DTO.player.RosterPlayerDTO;
import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.*;
import edu.baylor.ecs.athleticstorm.repository.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static edu.baylor.ecs.athleticstorm.startUp.Constants.*;

/**
 * populates initial data in the DB
 */
@Component
public class DataPopulator implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.logger(DataPopulator.class);

    // shows if the DB setup is done
    private static boolean setupComplete = false;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private RosterPlayerRepository rosterPlayerRepository;

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private Set<Team> teams = null;
    private Set<Player> players = null;
    private Set<Coach> coaches = null;
    private Set<RosterPlayer> rosterPlayers = new TreeSet<>();
    private Set<Season> seasons = new TreeSet<>();

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // see if already done or colors already exist in the DB
        if(setupComplete || (coordinatorRepository.count() > 0)){
            setupComplete = true;
            return;
        }
        setup();
    }

    @Transactional
    public void setup() {

        logger.info("Beginning Setup");

        // get all of the teams
        teams = getTeams();

        // get all of the coaches
        coaches = getCoaches();

        // get all of the playres
        players = getPlayers();

        // get all of the team rosters
        getTeamRosters();

        // get all of the usage stats for players
        getPlayerUsage();

        getCoachRatings();

        getCoordinators();

        logger.info("End Setup");

        setupComplete = true;
    }

    @Transactional
    public Set<Team> getTeams(){
        logger.info("Getting teams");
        if(teamRepository.count() == 0) {
            TeamDTO[] teams = restTemplate.getForObject(TEAM_URL, TeamDTO[].class);
            List<Team> t = Arrays.stream(teams).map(Team::new).collect(Collectors.toList());
            teamRepository.saveAll(t);
            teamRepository.flush();
            return new TreeSet<>(t);
        }else{
            return new TreeSet<>(teamRepository.findAll());
        }
    }

    @Transactional
    public Set<Coach> getCoaches(){
        logger.info("Getting coaches");
        if(coachRepository.count() == 0) {
            CoachDTO[] coachDTOS = restTemplate.getForObject(COACH_URL, CoachDTO[].class);
            List<Coach> c = Arrays.stream(coachDTOS).map(x -> new Coach(x, this.seasons)).collect(Collectors.toList());
            coaches = new TreeSet<Coach>(c);
            saveSeasons();
            coachRepository.saveAll(coaches);
            coachRepository.flush();
            return coaches;
        }else{
            return new TreeSet<>(coachRepository.findAll());
        }
    }

    @Transactional
    public void saveSeasons(){
        logger.info("Getting seasons");
        for(Coach c: coaches){
            Season record = c.getSeasons().stream().filter(x -> x.getYear() == 2019).findAny().orElse(null);
            if(Objects.isNull(record) || c.getSeasons().isEmpty()){
                continue;
            }

            //find the team for this coach
            Season finalRecord = record;
            Team t = teams.stream().filter(x -> x.getSchool().equals(finalRecord.getSchool())).findFirst().orElse(null);

            c.setTeam(t);
            assert t != null;
            t.getCoaches().add(c);
        }
    }


    @Transactional
    public Set<Player> getPlayers(){
        logger.info("Getting players");
        if(playerRepository.count() == 0) {
            Set<Player> toReturn = new TreeSet<>();
            for (Team t : teams) {
                PlayerDTO[] players = restTemplate.getForObject(playerByFullNameAndTeam("%", t.getSchool()), PlayerDTO[].class);
                toReturn.addAll(Arrays.stream(players).map(Player::new).collect(Collectors.toList()));
            }
            playerRepository.saveAll(toReturn);
            playerRepository.flush();
            return toReturn;
        }
        return new TreeSet<>(playerRepository.findAll());
    }

    @Transactional
    public void getTeamRosters(){
        logger.info("Getting rosters");
        if(rosterPlayerRepository.count() > 0){
            rosterPlayers = new TreeSet<>(rosterPlayerRepository.findAll());
            return;
        }
        for(Team t : teams){
            String url = Objects.requireNonNull(rosterByTeamAndYear(t.getSchool(), null));
            RosterPlayerDTO[] rosterPlayerDTOS = restTemplate.getForObject(url, RosterPlayerDTO[].class);

            // for each roster player associate it with the correct player
            List<RosterPlayerDTO> rosterPlayerList = Arrays.asList(rosterPlayerDTOS);

            for(RosterPlayerDTO rosterPlayer : rosterPlayerList){

                // get the correct player for this roster player
                String fullName = rosterPlayer.getFirst_name() + " " + rosterPlayer.getLast_name();
                Player p = players.stream().filter(x -> x.getName().equalsIgnoreCase(fullName)).findAny().orElse(null);
                if(Objects.isNull(p)){
                    continue;
                }
                RosterPlayer rp = new RosterPlayer(2019, p, t);
                t.getRosterPlayers().add(rp);
                p.getRosterPlayerList().add(rp);
                rosterPlayers.add(rp);
            }
        }
        rosterPlayerRepository.saveAll(rosterPlayers);
        rosterPlayerRepository.flush();
    }



    @Transactional
    public void getPlayerUsage(){
        if(usageRepository.count() > 0){
            return;
        }

        for(String year : new String[]{"2017","2018","2019"}){
            AdvancedPlayerDTO [] usages = restTemplate.getForObject(playerUsage(year, null), AdvancedPlayerDTO[].class);

            Long yearLong = new Long(year);
            List<Usage> u =
                    Arrays.stream(usages).map(x -> {
                        Optional<Player> temp = playerRepository.findById(x.getId());
                        return temp.isPresent() ? new Usage(x.getUsage(), temp.get(), yearLong) : null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

            usageRepository.saveAll(u);
        }
        usageRepository.flush();
    }

    @Transactional
    public void getCoachRatings(){
        logger.info("Getting ratings");
        for(Coach c: coaches){

        }
    }

    @Transactional
    public void getCoordinators() {

        if(coordinatorRepository.count() > 0){
            return;
        }

        try {
            List<Coordinator> coordinators = new ArrayList<>();

            Resource resource = resourceLoader.getResource("classpath:data/OC.csv");
            InputStream inputStream = resource.getInputStream();
            Scanner sc = new Scanner(inputStream);
            parseFile(sc, coordinators, "OC");
            sc.close();

            resource = resourceLoader.getResource("classpath:data/DC.csv");
            inputStream = resource.getInputStream();
            sc = new Scanner(inputStream);
            parseFile(sc, coordinators, "DC");
            sc.close();

            coordinatorRepository.saveAll(coordinators);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void parseFile(Scanner sc, List<Coordinator> coordinators, String key){
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(",");
            Map<String, List<Integer>> nameToYears = new HashMap<>();
            getCoordinatorsByYear(nameToYears, tokens, 1, 2017);
            getCoordinatorsByYear(nameToYears, tokens, 2, 2018);
            getCoordinatorsByYear(nameToYears, tokens, 3, 2019);

            for(Map.Entry<String, List<Integer>> entry : nameToYears.entrySet()){
                List<Integer> years = entry.getValue();
                Integer startYear = years.stream().min(Integer::compareTo).get();
                Integer endYear = years.stream().max(Integer::compareTo).get();
                Team t = teams.stream().filter(x -> x.getSchool().equals(tokens[0])).findFirst().orElse(null);
                if(t != null) {
                    coordinators.add(new Coordinator(entry.getKey(), key, startYear, endYear, t));
                }

            }
        }
    }

    private void getCoordinatorsByYear(Map<String, List<Integer>> nameToYears, String[] tokens, int index, int year){
        String token = tokens[index];
        String[] coordinators = token.split("/");
        for(String name : coordinators){
            List<Integer> years = nameToYears.getOrDefault(name, new ArrayList<>());
            years.add(year);
            nameToYears.put(name, years);
        }
    }

}