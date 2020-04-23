/*
 * Filename: PlayerController.java
 * Author: Ian Laird
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.controller.data;

import edu.baylor.ecs.athleticstorm.DTO.player.CompositePlayer;
import edu.baylor.ecs.athleticstorm.payload.PlayerStatsRequest;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Player data
 *
 * @author Ian Laird
 */
@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    /**
     * Returns a player's stats given the specified request
     * @param request the specified request
     * @return a player's stats given the specified request
     */
    @PostMapping("/getStats/")
    public CompositePlayer getPlayerById(@RequestBody PlayerStatsRequest request){
        return playerService.getPlayerStats(request.getFirstName(), request.getLastName(), request.getYear());
    }
}
