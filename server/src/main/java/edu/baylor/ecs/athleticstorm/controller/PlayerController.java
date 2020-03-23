package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.model.player.CompositePlayer;
import edu.baylor.ecs.athleticstorm.model.player.PlayerInfoRequest;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    private CollegeFootballAPIService collegeFootballAPIService;

    @PostMapping("/getStats/")
    public CompositePlayer getPlayerById(@RequestBody PlayerInfoRequest request){
        return collegeFootballAPIService.getPlayerStats(request);
    }
}
