package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.RosterPlayer;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roster")
public class RosterController {

    @Autowired
    private CollegeFootballAPIService collegeFootballAPIService;

    @GetMapping("/{teamId}/{year}")
    public List<RosterPlayer> getSeasonRoster(@PathVariable("teamId") int teamId, @PathVariable("year") int year){
        return collegeFootballAPIService.getSeasonRoster(teamId, year);
    }
}
