package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.player.RosterPlayerDTO;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roster")
public class RosterController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{teamId}")
    public List<RosterPlayerDTO> getSeasonRoster(@PathVariable("teamId") Long teamId){
        return playerService.getTeamRoster(teamId);
    }

    @GetMapping("/{teamId}/{year}")
    public List<RosterPlayerDTO> getSeasonRosterByYear(@PathVariable("teamId") Long teamId, @PathVariable("year") Integer year){
        return playerService.roster(teamId, year);
    }
}
