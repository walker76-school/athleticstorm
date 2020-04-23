/*
 * Filename: RosterController.java
 * Author: Andrew Walker
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.controller.data;

import edu.baylor.ecs.athleticstorm.DTO.player.RosterPlayerDTO;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Roster data
 *
 * @author Andrew Walker
 */
@RestController
@RequestMapping("/api/roster")
public class RosterController {

    @Autowired
    private PlayerService playerService;

    /**
     * Returns a roster for a team given by their Id
     * @param teamId a team's id
     * @return a roster for a team given by their Id
     */
    @GetMapping("/{teamId}")
    public List<RosterPlayerDTO> getSeasonRoster(@PathVariable("teamId") Long teamId){
        return playerService.getTeamRoster(teamId);
    }

    /**
     * Returns a roster for a team given by their Id and year
     * @param teamId a team's id
     * @param year a year
     * @return a roster for a team given by their Id and year
     */
    @GetMapping("/{teamId}/{year}")
    public List<RosterPlayerDTO> getSeasonRosterByYear(@PathVariable("teamId") Long teamId, @PathVariable("year") Integer year){
        return playerService.roster(teamId, year);
    }
}
