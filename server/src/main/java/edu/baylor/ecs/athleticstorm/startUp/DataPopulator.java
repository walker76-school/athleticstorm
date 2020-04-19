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
import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.game.Game;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import edu.baylor.ecs.athleticstorm.model.rating.PersonType;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.model.rating.RatingComposite;
import edu.baylor.ecs.athleticstorm.model.rating.RatingKey;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.*;
import edu.baylor.ecs.athleticstorm.repository.RatingRepository;
import edu.baylor.ecs.athleticstorm.service.RatingService;
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
    private RatingService ratingService;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private Set<Team> teams = null;
    private Set<Player> players = null;
    private Set<Coach> coaches = null;
    private Set<RosterPlayer> rosterPlayers = new TreeSet<>();
    private Set<Season> seasons = new TreeSet<>();
    private Set<Coordinator> coordinators = new TreeSet<>();

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // see if already done or colors already exist in the DB
        if(setupComplete || ratingRepository.count() > 0){
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

        getCoordinators();

        getRatings();

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
    public void getCoordinators() {
        logger.info("Getting coordinators");

        if(coordinatorRepository.count() > 0){
            this.coordinators = new TreeSet<>(coordinatorRepository.findAll());
            return;
        }

        try {
            Set<Coordinator> coordinators = new HashSet<>();

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
            this.coordinators = coordinators;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void parseFile(Scanner sc, Set<Coordinator> coordinators, String key){
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(",");
            Map<String, List<Integer>> nameToYears = new HashMap<>();

            List<Integer> yearsOrDefault = nameToYears.getOrDefault(tokens[1], new ArrayList<>());
            yearsOrDefault.add(2017);
            nameToYears.put(tokens[1], yearsOrDefault);

            yearsOrDefault = nameToYears.getOrDefault(tokens[2], new ArrayList<>());
            yearsOrDefault.add(2018);
            nameToYears.put(tokens[2], yearsOrDefault);

            yearsOrDefault = nameToYears.getOrDefault(tokens[3], new ArrayList<>());
            yearsOrDefault.add(2019);
            nameToYears.put(tokens[3], yearsOrDefault);

            for(Map.Entry<String, List<Integer>> entry : nameToYears.entrySet()){
                List<Integer> years = entry.getValue();
                Integer startYear = years.stream().min(Integer::compareTo).get();
                Integer endYear = years.stream().max(Integer::compareTo).get();
                Team t = teams.stream().filter(x -> x.getSchool().equals(tokens[0])).findFirst().orElse(null);
                if(t != null) {
                    Coordinator coordinator = new Coordinator(entry.getKey(), key, startYear, endYear, t);
                    //coordinatorRepository.save(coordinator);
                    t.getCoordinators().add(coordinator);
                    coordinators.add(coordinator);
                }

            }
        }
    }

    @Transactional
    public void getRatings() {

//        if(ratingRepository.count() > 0){
//            return;
//        }
//
//        List<Rating> ratings = new ArrayList<>();
//
//        for(Coach coach : coaches){
//
//            if(coach.getTeam() == null){
//                System.err.println(coach.getName());
//                continue; // Some error
//            }
//
//            int endYear = coach.getSeasons().stream().max(Comparator.comparing(Season::getYear)).get().getYear();
//            if(endYear < 2017){
//                continue; // We can't calculate for them
//            }
//
//            double coachScore = 50;
//            double ocScore = 50;
//            double dcScore = 50;
//
//            for(int year = 2017; year <= Math.min(endYear, 2019); year++){
//
//                int finalYear = year;
//
//                //String oc = coordinators.stream().filter(x -> x.getTeam().getId() == teamId && x.getStartYear() <= finalYear && x.getEndYear() >= finalYear && x.getPosition().equalsIgnoreCase("oc")).findFirst().get().getName();
//                //String dc = coordinators.stream().filter(x -> x.getTeam().getId() == teamId && x.getStartYear() <= finalYear && x.getEndYear() >= finalYear && x.getPosition().equalsIgnoreCase("dc")).findFirst().get().getName();
//
//                // TODO - Get Team from Seasons of Coach using year
//
//                // TODO - Multiple OC and DC per Year
//                Optional<Coordinator> ocOpt = coach.getTeam().getCoordinators().stream()
//                        .filter(x -> x.getStartYear() <= finalYear && x.getEndYear() >= finalYear && x.getPosition().equalsIgnoreCase("oc"))
//                        .findFirst();
//
//                if(!ocOpt.isPresent()){
//                    continue;
//                }
//
//                Optional<Coordinator> dcOpt = coach.getTeam().getCoordinators().stream()
//                        .filter(x -> x.getStartYear() <= finalYear && x.getEndYear() >= finalYear && x.getPosition().equalsIgnoreCase("dc"))
//                        .findFirst();
//
//                if(!dcOpt.isPresent()){
//                    continue;
//                }
//
//                String oc = ocOpt.get().getName();
//                String dc = dcOpt.get().getName();
//                String team = coach.getTeam().getSchool();
//
//                // Get all the games and calculate the home/away percentages
//                Game[] games = restTemplate.getForObject(gamesPerTeamAndYear(year, team), Game[].class);
//
//                List<Game> homeGames = Arrays.stream(games).filter(x -> x.getHome_team().equals(team)).collect(Collectors.toList());
//                List<Game> awayGames = Arrays.stream(games).filter(x -> !x.getHome_team().equals(team)).collect(Collectors.toList());
//
//                double hwp = homeGames.stream().filter(x -> x.getAway_points() < x.getHome_points()).count() * 1.0 / homeGames.size() * 100;
//                double awp = awayGames.stream().filter(x -> x.getAway_points() > x.getHome_points()).count() * 1.0 / homeGames.size() * 100;
//
//                for(Game game : games){ // TODO - Need to be for every game, not every week cause missing
//                    int week = game.getWeek();
//
//                    RatingComposite composite = ratingService.getRatings(team, year, week, hwp, awp);
//                    if(composite == null){
//                        continue;
//                    }
//
//                    // Adjust Scores and Save
//                    coachScore = scaleRating(coachScore, composite.getCoach());
//                    ratings.add(new Rating(new RatingKey(coach.getName(), year, week), !Double.isFinite(coachScore) ? 50.0 : coachScore, PersonType.COACH));
//
//                    ocScore = scaleRating(ocScore, composite.getOC());
//                    ratings.add(new Rating(new RatingKey(oc, year, week), !Double.isFinite(ocScore) ? 50.0 : ocScore, PersonType.OFFENSIVE));
//
//                    dcScore = scaleRating(dcScore, composite.getDC());
//                    ratings.add(new Rating(new RatingKey(dc, year, week), !Double.isFinite(dcScore) ? 50.0 : dcScore, PersonType.DEFENSIVE));
//
//                }
//            }
//        }
//
//        ratingRepository.saveAll(ratings);
    }

    private double scaleRating(double currentRating, double weeklyRating){
        final double SCALE = 1.0;
        double weeklyRatingFinal = weeklyRating;
        if(Double.isNaN(weeklyRating)){
            weeklyRatingFinal = 50;
        }
        return (currentRating + (weeklyRatingFinal * SCALE)) / (1 + SCALE);
    }
}