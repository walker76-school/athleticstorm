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
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
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

    // shows if the DB setup is done
    private static boolean setupComplete = false;

    @Autowired
    private TeamRepository teamRepository;

    private Set<Team> teams = null;
    private Set<Player> players = null;
    private Set<Coach> coaches = null;
    private Set<RosterPlayer> rosterPlayers = new TreeSet<>();

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){

        // see if already done or colors already exist in the DB
        if(setupComplete || teamRepository.count() > 0 ){
            setupComplete = true;
            return;
        }
        setup();
    }

    @Transactional
    public void setup(){

        // get all of the teams
        teams = getTeams();

        // get all of the coaches
        coaches = getCoaches();

        // get all of the playres
        players = getPlayers();

        // save each season
        saveSeasons();

        // get all of the team rosters
        getTeamRosters();

        // get all of the usage stats for players
        getPlayerUsage();

        persist();

        setupComplete = true;
    }

    @Transactional
    public Set<Team> getTeams(){
        TeamDTO[] teams = restTemplate.getForObject(TEAM_URL, TeamDTO[].class);
        return new TreeSet<>(Arrays.stream(teams).map(Team::new).collect(Collectors.toList()));
    }

    @Transactional
    public Set<Coach> getCoaches(){
        CoachDTO[] coaches = restTemplate.getForObject(COACH_URL, CoachDTO[].class);
        return new TreeSet<>(Arrays.stream(coaches).map(Coach::new).collect(Collectors.toList()));
    }

    @Transactional
    public Set<Player> getPlayers(){
        Set<Player> toReturn = new TreeSet<>();
        for(Team t: teams){
            PlayerDTO[] players = restTemplate.getForObject(playerByFullNameAndTeam("%", t.getSchool()), PlayerDTO[].class);
            toReturn.addAll(Arrays.stream(players).map(Player::new).collect(Collectors.toList()));
        }
        return toReturn;
    }

    @Transactional
    public void saveSeasons(){
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
    public void getTeamRosters(){
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
    }

    @Transactional
    public void getPlayerUsage(){
        for(Player p: players) {
            AdvancedPlayerDTO[] advancedPlayers = restTemplate.getForObject(playerUsage("2019", p.getId().toString()), AdvancedPlayerDTO[].class);
            if(advancedPlayers.length == 0){
                continue;
            }
            Usage u = new Usage(advancedPlayers[0].getUsage(), p);
            p.setUsage(u);
        }
    }

    @Transactional
    public void persist(){
        teamRepository.saveAll(teams);
    }

}