/*
 * Filename: DataPopulator.java
 * Author: Ian Laird
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.startUp;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.player.AdvancedPlayerDTO;
import edu.baylor.ecs.athleticstorm.DTO.player.PlayerDTO;
import edu.baylor.ecs.athleticstorm.DTO.player.RosterPlayerDTO;
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

import static edu.baylor.ecs.athleticstorm.startUp.URLConstants.*;

/**
 * Populates initial data in the DB
 *
 * @author Ian Laird
 */
@Component
public class DataPopulator implements ApplicationListener<ContextRefreshedEvent> {

    // rest template
    @Autowired
    private RestTemplate restTemplate;

    // logger
    Logger logger = LoggerFactory.logger(DataPopulator.class);

    // shows if the DB setup is done
    private static boolean setupComplete = false;

    private static String [] ACTIVE_YEARS = new String[]{"2017","2018","2019"};

    // jpa team repo
    @Autowired
    private TeamRepository teamRepository;

    // jpa player repo
    @Autowired
    private PlayerRepository playerRepository;

    // jpa coach repo
    @Autowired
    private CoachRepository coachRepository;

    // jpa roster player repo
    @Autowired
    private RosterPlayerRepository rosterPlayerRepository;

    // jpa usage repo
    @Autowired
    private UsageRepository usageRepository;

    // jpa rating repo
    @Autowired
    private RatingRepository ratingRepository;

    // rating service
    @Autowired
    private RatingService ratingService;

    // jpa coordinator repo
    @Autowired
    private CoordinatorRepository coordinatorRepository;

    // resource loader
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private SeasonRepository seasonRepository;

    // all teams
    private Set<Team> teams = null;

    // all players
    private Set<Player> players = null;

    // all coaches
    private Set<Coach> coaches = null;

    // all roster players
    private Set<RosterPlayer> rosterPlayers = new TreeSet<>();

    // all seasons
    private Set<Season> seasons = new TreeSet<>();

    /**
     * Populates the DB if empty on Context Refreshed Event
     * @param event the refresh events
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // see if already done or colors already exist in the DB
        if(setupComplete || ratingRepository.count() > 0){
            return;
        }

        logger.info("The AthleticStorm Server is about to start the process of pulling all necessary information to populate the database.");
        logger.info("This process can take upwards of 20 minutes.");
        logger.info("Please be patient.");

        // setup the DB
        // keep in mind this is transactional
        setup();
    }

    /**
     * Setups the DB
     */
    @Transactional
    public void setup() {

        logger.info("Beginning Setup ... ");

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

        // gets all coordinators
        getCoordinators();

        // get ratings
        getRatings();

        logger.info("End Setup");

        // set this so that this does not run again while the server is running
        setupComplete = true;
    }

    /**
     * Setups the DB
     */
    @Transactional
    public void refresh() {

        logger.info("Beginning Refresh ... ");

        setupComplete = false;

        ratingRepository.deleteAll();
        ratingRepository.flush();
        logger.info("Removed all Ratings ... ");

        rosterPlayerRepository.deleteAll();
        rosterPlayerRepository.flush();
        logger.info("Removed all Roster Players ... ");

        usageRepository.deleteAll();
        usageRepository.flush();
        logger.info("Removed all Usage ... ");

        playerRepository.deleteAll();
        playerRepository.flush();
        logger.info("Removed all Players ... ");

        coachRepository.deleteAll();
        coachRepository.flush();
        logger.info("Removed all Coaches ... ");

        seasonRepository.deleteAll();
        seasonRepository.flush();
        logger.info("Removed all Seasons ... ");

        coordinatorRepository.deleteAll();
        coordinatorRepository.flush();
        logger.info("Removed all Coordinators ... ");

        teamRepository.deleteAll();
        teamRepository.flush();
        logger.info("Removed all Teams ... ");

        setup();
    }

    /**
     * Gets all teams
     * @return all teams
     */
    @Transactional
    public Set<Team> getTeams(){
        logger.info("Getting Teams ... ");
        if(teamRepository.count() == 0) {
            TeamDTO[] teams = restTemplate.getForObject(TEAM_URL, TeamDTO[].class);
            List<Team> t = Arrays.stream(teams).map(Team::new).collect(Collectors.toList());
            teamRepository.saveAll(t);
            teamRepository.flush();
            return new TreeSet<>(t);
        } else {
            return new TreeSet<>(teamRepository.findAll());
        }
    }

    /**
     * Gets all coaches
     * @return all coaches
     */
    @Transactional
    public Set<Coach> getCoaches(){
        logger.info("Getting Coaches ... ");
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

    /**
     * Associates coaches with current teams
     */
    @Transactional
    public void saveSeasons(){
        logger.info("Getting Seasons ... ");

        // for each coach
        for(Coach c: coaches){

            // get the season for the current year
            Season record = c.getSeasons().stream().filter(x -> x.getYear() == 2019).findAny().orElse(null);
            if(Objects.isNull(record) || c.getSeasons().isEmpty()){
                continue;
            }

            //find the team for this coach
            Season finalRecord = record;
            Team t = teams.stream().filter(x -> x.getSchool().equals(finalRecord.getSchool())).findFirst().orElse(null);

            // set the team for coach
            c.setTeam(t);
            assert t != null;

            // add coach to the team
            t.getCoaches().add(c);
        }
    }


    /**
     * Gets all players from either the DB or the online API
     * @return all players
     */
    @Transactional
    public Set<Player> getPlayers(){
        logger.info("Getting Players ... ");
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

    /**
     * Get rosters for every team
     */
    @Transactional
    public void getTeamRosters(){
        logger.info("Getting Rosters ... ");
        if(rosterPlayerRepository.count() > 0){
            rosterPlayers = new TreeSet<>(rosterPlayerRepository.findAll());
            return;
        }
        for(String year: ACTIVE_YEARS) {
            for (Team t : teams) {
                Optional<Season> optional = seasons.stream().filter(x -> x.getSchool().equalsIgnoreCase(t.getSchool()) && x.getYear() == Integer.parseInt(year)).findAny();
                if(!optional.isPresent()){
                    //logger.info("No present" + t.getSchool());
                    continue;
                }
                Season s = optional.get();
                String url = Objects.requireNonNull(rosterByTeamAndYear(t.getSchool(), year));
                RosterPlayerDTO[] rosterPlayerDTOS = restTemplate.getForObject(url, RosterPlayerDTO[].class);

                // for each roster player associate it with the correct player
                List<RosterPlayerDTO> rosterPlayerList = Arrays.asList(rosterPlayerDTOS);

                for (RosterPlayerDTO rosterPlayer : rosterPlayerList) {

                    // get the correct player for this roster player
                    String fullName = rosterPlayer.getFirst_name() + " " + rosterPlayer.getLast_name();
                    Player p = players.stream().filter(x -> x.getName().equalsIgnoreCase(fullName)).findAny().orElse(null);
                    if (Objects.isNull(p)) {
                        continue;
                    }
                    RosterPlayer rp = new RosterPlayer(p, s);
                    s.getRosterPlayers().add(rp);
                    p.getRosterPlayerList().add(rp);
                    rosterPlayers.add(rp);
                }
            }
        }
        rosterPlayerRepository.saveAll(rosterPlayers);
        rosterPlayerRepository.flush();
    }

    /**
     * Gets usage(s) for every player
     */
    @Transactional
    public void getPlayerUsage(){

        logger.info("Getting Player Usage ... ");

        if(usageRepository.count() > 0){
            return;
        }

        for(String year : ACTIVE_YEARS){
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

    /**
     * Gets all coordinators
     */
    @Transactional
    public void getCoordinators() {
        logger.info("Getting Coordinators ... ");

        if(coordinatorRepository.count() > 0){
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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Helper method to parse a file
     * @param sc the scanner object
     * @param coordinators the coordinator set that new ones should be added to
     * @param key the key
     */
    private void parseFile(Scanner sc, Set<Coordinator> coordinators, String key){
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(",");
            Map<String, List<Integer>> nameToYears = new HashMap<>();

            populateYears(nameToYears, tokens[1].split("/"), 2017);
            populateYears(nameToYears, tokens[2].split("/"), 2018);
            populateYears(nameToYears, tokens[3].split("/"), 2019);

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

    /**
     * Helper method to populate years
     * @param nameToYears maps names to years
     * @param coordinators the array of coorinator names
     * @param year the year
     */
    private void populateYears(Map<String, List<Integer>> nameToYears, String[] coordinators, int year){
        for(String s : coordinators) {
            List<Integer> yearsOrDefault = nameToYears.getOrDefault(s, new ArrayList<>());
            yearsOrDefault.add(year);
            nameToYears.put(s, yearsOrDefault);
        }
    }

    /**
     * Gets ratings
     */
    @Transactional
    public void getRatings() {
        logger.info("Getting Ratings ... ");

        if(ratingRepository.count() > 0){
            return;
        }

        Set<Rating> ratings = new HashSet<>();

        for(Coach coach : coaches){

            int endYear = coach.getSeasons().stream().max(Comparator.comparing(Season::getYear)).get().getYear();
            if(endYear < 2017){
                continue; // We can't calculate for them
            }

            double coachScore = 50;

            for(int year = 2017; year <= Math.min(endYear, 2019); year++){

                int finalYear = year;
                Map<String, Double> ocRatings = new HashMap<>();
                Map<String, Double> dcRatings = new HashMap<>();

                String teamName = coach.getSeasons().stream().max(Comparator.comparing(Season::getYear)).get().getSchool();

                Team team = coach.getTeam();
                if(team == null) {
                    team = teamRepository.findTeamBySchool(teamName).get();
                }

                // TODO - Multiple OC and DC per Year
                List<Coordinator> ocs = team.getCoordinators().stream()
                        .filter(x -> x.getStartYear() <= finalYear && x.getEndYear() >= finalYear && x.getPosition().equalsIgnoreCase("oc"))
                        .collect(Collectors.toList());

                if(ocs.isEmpty()){
                    continue;
                }

                for(Coordinator oc : ocs){
                    List<Rating> oldRatings = ratingRepository.findAllByKey_Name(oc.getName());
                    Optional<Rating> ratingOpt = oldRatings.stream().max(Rating::compareTo);
                    ocRatings.put(oc.getName(), ratingOpt.isPresent() ? ratingOpt.get().getRating() : 50);
                }

                List<Coordinator> dcs = team.getCoordinators().stream()
                        .filter(x -> x.getStartYear() <= finalYear && x.getEndYear() >= finalYear && x.getPosition().equalsIgnoreCase("dc"))
                        .collect(Collectors.toList());

                if(dcs.isEmpty()){
                    continue;
                }

                for(Coordinator dc : dcs){
                    List<Rating> oldRatings = ratingRepository.findAllByKey_Name(dc.getName());
                    Optional<Rating> ratingOpt = oldRatings.stream().max(Rating::compareTo);
                    dcRatings.put(dc.getName(), ratingOpt.isPresent() ? ratingOpt.get().getRating() : 50);
                }


                // Get all the games and calculate the home/away percentages
                Game[] games = restTemplate.getForObject(gamesPerTeamAndYear(year, teamName), Game[].class);

                List<Game> homeGames = Arrays.stream(games).filter(x -> x.getHome_team().equals(teamName)).collect(Collectors.toList());
                List<Game> awayGames = Arrays.stream(games).filter(x -> !x.getHome_team().equals(teamName)).collect(Collectors.toList());

                double hwp = homeGames.stream().filter(x -> x.getAway_points() < x.getHome_points()).count() * 1.0 / homeGames.size() * 100;
                double awp = awayGames.stream().filter(x -> x.getAway_points() > x.getHome_points()).count() * 1.0 / homeGames.size() * 100;

                for(Game game : games){ // TODO - Need to be for every game, not every week cause missing
                    int week = game.getWeek();

                    RatingComposite composite = ratingService.getRatings(teamName, year, week, hwp, awp);
                    if(composite == null){
                        continue;
                    }

                    // Adjust Scores and Save
                    coachScore = scaleRating(coachScore, composite.getCoach());
                    ratings.add(new Rating(new RatingKey(coach.getName(), year, week,  PersonType.COACH), !Double.isFinite(coachScore) ? 50.0 : coachScore));

                    for(String oc : ocRatings.keySet()) {
                        double ocScore = scaleRating(ocRatings.get(oc), composite.getOC());
                        ratings.add(new Rating(new RatingKey(oc, year, week, PersonType.OFFENSIVE), !Double.isFinite(ocScore) ? 50.0 : ocScore));
                    }

                    for(String dc : dcRatings.keySet()) {
                        double dcScore = scaleRating(dcRatings.get(dc), composite.getDC());
                        ratings.add(new Rating(new RatingKey(dc, year, week, PersonType.DEFENSIVE), !Double.isFinite(dcScore) ? 50.0 : dcScore));
                    }

                }
            }
        }

        ratingRepository.saveAll(ratings);
    }

    /**
     * Scales the rating
     * @param currentRating the current rating
     * @param weeklyRating the new rating for the week
     * @return the scaled rating
     */
    private double scaleRating(double currentRating, double weeklyRating){
        final double SCALE = 1.0;
        double weeklyRatingFinal = weeklyRating;
        if(Double.isNaN(weeklyRating)){
            weeklyRatingFinal = 50;
        }
        return (currentRating + (weeklyRatingFinal * SCALE)) / (1 + SCALE);
    }
}