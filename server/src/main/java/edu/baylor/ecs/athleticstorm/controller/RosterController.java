package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roster")
public class RosterController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{teamId}/{year}")
    public List<RosterPlayer> getSeasonRoster(@PathVariable("teamId") Long teamId, @PathVariable("year") Long year){
        return playerService.getSeasonRoster(teamId, year);
    }
}
