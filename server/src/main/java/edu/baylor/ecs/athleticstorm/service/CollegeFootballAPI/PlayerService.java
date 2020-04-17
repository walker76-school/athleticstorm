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

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.player.*;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.PlayerRepository;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.RosterPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private RosterPlayerRepository rosterPlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public List<RosterPlayerDTO> getTeamRoster(Long teamId) {
        return roster(teamId, 2019);
    }

    public List<RosterPlayerDTO> roster(Long teamId, Integer year){
        return convertToDTO(rosterPlayerRepository.findAllByYearAndTeam(year, teamId));
    }

//    public List<RosterPlayerDTO> getSeasonRoster(Long teamId, Long year) {
//        return roster(teamId, year);
//    }

    public CompositePlayer getPlayerStats(String firstName, String lastName, Long year) {
        Optional<Player> playerOpt = playerRepository.findPlayerByFirstAndLastName(firstName, lastName);
        if(playerOpt.isPresent()){
            CompositePlayer compPlayer = new CompositePlayer(playerOpt.get(), year);
            return compPlayer;
        }
        return null;
    }

    public static List<RosterPlayerDTO> convertToDTO(Collection<RosterPlayer> rosterPlayer){
        return rosterPlayer.stream().map(RosterPlayerDTO::new).collect(Collectors.toList());
    }

}
