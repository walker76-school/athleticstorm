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
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.*;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    private Set<Team> teams = null;
    private Set<Player> players = null;
    private Set<Coach> coaches = null;
    private Set<RosterPlayer> rosterPlayers = new TreeSet<>();
    private Set<Season> seasons = new TreeSet<>();

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){

        // see if already done or colors already exist in the DB
        if(setupComplete || (teamRepository.count() > 0)){
            setupComplete = true;
            return;
        }
        setup();
    }

    @Transactional
    public void setup(){

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
            if(c.getSeasons().isEmpty()){
                continue;
            }
            Season record = null;
            for(Season s : c.getSeasons()){
                record = s;
                s.getCoaches().add(c);
            }

            //find the team for this coach
            Season finalRecord = record;
            Team t = teams.stream().filter(x -> x.equals(finalRecord.getSchool())).findFirst().orElse(null);

            c.setTeam(t);
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
                RosterPlayer rp = new RosterPlayer(p.getId(), 2019, p, t);
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
        logger.info("Getting player usage");
        for(Player p: players) {
            AdvancedPlayerDTO[] advancedPlayers = restTemplate.getForObject(playerUsage("2019", p.getId().toString()), AdvancedPlayerDTO[].class);
            if(advancedPlayers.length == 0){
                continue;
            }
            logger.info(Arrays.toString(advancedPlayers));
            Usage u = new Usage(advancedPlayers[0].getUsage(), p);
            playerRepository.save(p);
            usageRepository.save(u);
        }
        usageRepository.flush();;
    }

}