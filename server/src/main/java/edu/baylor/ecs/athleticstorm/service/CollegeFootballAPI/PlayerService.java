/*
 * Filename: PlayerService.java
 * Author: Ian Laird
 * Date Last Modified: 4/22/2020
 */

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

/**
 * Service for interacting with players
 *
 * @author Ian Laird
 */
@Service
public class PlayerService {

    // jpa roster player repo
    @Autowired
    private RosterPlayerRepository rosterPlayerRepository;

    // jpa player repo
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Get the current team roster
     * @param teamId the id of the team
     * @return all roster players
     */
    public List<RosterPlayerDTO> getTeamRoster(Long teamId) {
        return roster(teamId, 2019);
    }

    /**
     * Gets the team roster for a specific year
     * @param teamId the id of the team
     * @param year the year
     * @return all roster players for indicated team and year
     */
    public List<RosterPlayerDTO> roster(Long teamId, Integer year){
        return convertToDTO(rosterPlayerRepository.findAllByYearAndTeam(year, teamId));
    }

    /**
     * Gets all available stats for a player
     * @param firstName the first name of the player
     * @param lastName the last name of the player
     * @param year the year desired
     * @return all available data for indicated year
     */
    public CompositePlayer getPlayerStats(String firstName, String lastName, Long year) {
        Optional<Player> playerOpt = playerRepository.findPlayerByFirstAndLastName(firstName, lastName);
        if(playerOpt.isPresent()){
            CompositePlayer compPlayer = new CompositePlayer(playerOpt.get(), year);
            return compPlayer;
        }
        return null;
    }

    /**
     * Converts a collection of {@link RosterPlayer} to {@link RosterPlayerDTO}
     * @param rosterPlayer the collection of roster players
     * @return the list of dto
     */
    public static List<RosterPlayerDTO> convertToDTO(Collection<RosterPlayer> rosterPlayer){
        return rosterPlayer.stream().map(RosterPlayerDTO::new).collect(Collectors.toList());
    }

}
