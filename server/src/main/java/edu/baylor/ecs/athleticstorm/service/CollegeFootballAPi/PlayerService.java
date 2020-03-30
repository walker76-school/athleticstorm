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

import edu.baylor.ecs.athleticstorm.DTO.player.*;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.PlayerRepository;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public List<RosterPlayerDTO> getTeamRoster(Long teamId) {
        return roster(teamId, new Long(2020));
    }

    public List<RosterPlayerDTO> roster(Long teamId, Long year){
        Team team = teamRepository.getOne(teamId);
        return convertToDTO(team.getRosterPlayers());
    }

    public List<RosterPlayerDTO> getSeasonRoster(Long teamId, Long year) {
        return roster(teamId, year);
    }

    public CompositePlayer getPlayerStats(PlayerInfoRequest request) {
        String searchTerm = request.getFirst_name() + " " + request.getLast_name();

        Optional<Player> playerOpt = playerRepository.findPlayerByNameEquals(searchTerm);

        if(playerOpt.isPresent()){
            CompositePlayer compPlayer = new CompositePlayer(playerOpt.get(), new AdvancedPlayerDTO());
            compPlayer.setAdvancedPlayer(compPlayer.getAdvancedPlayer());
            return compPlayer;
        }
        return null;
    }

    public static List<RosterPlayerDTO> convertToDTO(List<RosterPlayer> rosterPlayer){
        return rosterPlayer.stream().map(RosterPlayerDTO::new).collect(Collectors.toList());
    }

}
