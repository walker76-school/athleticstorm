/******************************************************************************
 *
 * PlayerService.java
 *
 * author: Ian laird
 *
 * Created 3/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.DTO.player.CompositePlayer;
import edu.baylor.ecs.athleticstorm.DTO.player.PlayerInfoRequest;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.PlayerRepository;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public List<RosterPlayer> getTeamRoster(Long teamId) {
        return roster(teamId, new Long(2020));
    }

    public List<RosterPlayer> roster(Long teamId, Long year){
        Team team = teamRepository.getOne(teamId);
        return team.getRosterPlayers();
    }

    public List<RosterPlayer> getSeasonRoster(Long teamId, Long year) {
        return roster(teamId, year);
    }

    public CompositePlayer getPlayerStats(PlayerInfoRequest request) {
        String searchTerm = request.getFirst_name() + " " + request.getLast_name();

        Optional<Player> playerOpt = playerRepository.findPlayerByNameEquals(searchTerm);

        if(playerOpt.isPresent()){
            CompositePlayer compPlayer = new CompositePlayer();
            compPlayer.setPlayer(playerOpt.get());
            compPlayer.setAdvancedPlayer(compPlayer.getAdvancedPlayer());
            return compPlayer;
        }
        return null;
    }

}
