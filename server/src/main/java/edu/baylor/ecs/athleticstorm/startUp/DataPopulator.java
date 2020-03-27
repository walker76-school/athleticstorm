package edu.baylor.ecs.athleticstorm.startUp;

import edu.baylor.ecs.athleticstorm.DTO.player.AdvancedPlayer;
import edu.baylor.ecs.athleticstorm.DTO.player.RosterPlayerDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){

        // see if already done or colors already exist in the DB
        if(setupComplete || teamRepository.count() > 0){
            setupComplete = true;
            return;
        }

        setup();
    }

    @Transactional
    public void setup(){

        // get all of the teams
        Set<Team> teams = getTeams();

        // get all of the coaches
        Set<Coach> coaches = getCoaches();

        // get all of the playres
        Set<Player> players = getPlayers();

        Set<RosterPlayer> rosterPlayers = new TreeSet<>();

        // save each season
        saveSeasons(coaches, teams);

        // get all of the team rosters
        for(Team t : teams){
            String url = Objects.requireNonNull(rosterByTeamAndYear(t.getSchool(), null));
            RosterPlayerDTO[] rosterPlayerDTOS = restTemplate.getForObject(url, RosterPlayerDTO[].class);

            // for each roster player associate it with the correct player
            List<RosterPlayerDTO> rosterPlayerList = Arrays.asList(rosterPlayerDTOS);

            for(RosterPlayerDTO rosterPlayer : rosterPlayerList){

                // get the correct player for this roster player
                String fullName = rosterPlayer.getFirst_Name() + " " + rosterPlayer.getLast_Name();
                Player p = players.stream().filter(x -> x.getName().equals(fullName)).findAny().orElse(null);
                RosterPlayer rp = new RosterPlayer(p.getId(), 2019, p, t);
                t.getRosterPlayers().add(rp);
                p.getRosterPlayerList().add(rp);
                rosterPlayers.add(rp);
            }
        }

        // TODO finish this up
        // get all of the usage stats for players
        for(Player p: players) {
            AdvancedPlayer[] advancedPlayers = restTemplate.getForObject(playerUsage("2019", p.getId().toString()), AdvancedPlayer[].class);
        }

        setupComplete = true;
    }

    @Transactional
    public Set<Team> getTeams(){
        Team[] teams = restTemplate.getForObject(TEAM_URL, Team[].class);
        return new TreeSet<>(Arrays.asList(teams));
    }

    @Transactional
    public Set<Coach> getCoaches(){
        Coach[] coaches = restTemplate.getForObject(COACH_URL, Coach[].class);
        return new TreeSet<>(Arrays.asList(coaches));
    }

    @Transactional
    public Set<Player> getPlayers(){
        Player[] players = restTemplate.getForObject(playerByFullName("%"), Player[].class);
        return new TreeSet<>(Arrays.asList(players));
    }

    @Transactional
    public void saveSeasons(Set<Coach> coaches, Set<Team> teams){
        for(Coach c: coaches){
            Season record;
            for(Season s : c.getSeasons()){
                record = s;
                s.getCoaches().add(c);
            }

            //find the team for this coach
            Team t = teams.stream().filter(x -> x.equals(record.getSeasonId().getSchool())).findFirst().orElse(null);

            c.setTeam(t);
            t.getCoaches().add(c);
        }
    }

}