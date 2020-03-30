package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.player.CompositePlayer;
import edu.baylor.ecs.athleticstorm.DTO.player.PlayerInfoRequest;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/getStats/")
    public CompositePlayer getPlayerById(@RequestBody PlayerInfoRequest request){
        return playerService.getPlayerStats(request);
    }
}
