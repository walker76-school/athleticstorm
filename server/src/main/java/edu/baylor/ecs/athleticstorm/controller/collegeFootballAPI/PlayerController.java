package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.player.CompositePlayer;
import edu.baylor.ecs.athleticstorm.payload.PlayerStatsRequest;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/getStats/")
    public CompositePlayer getPlayerById(@RequestBody PlayerStatsRequest request){
        return playerService.getPlayerStats(request.getFirstName(), request.getLastName(), request.getYear());
    }
}
