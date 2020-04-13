package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.player.CompositePlayer;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/getStats/")
    public CompositePlayer getPlayerById(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName){
        return playerService.getPlayerStats(firstName, lastName);
    }
}
