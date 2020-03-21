package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import edu.baylor.ecs.athleticstorm.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private CollegeFootballAPIService collegeFootballAPIService;

    @GetMapping("/all")
    public List<Team> getAllTeams(){
        return collegeFootballAPIService.getAllTeams();
    }

    @GetMapping("/fbs")
    public List<Team> getAllFBSTeams(){
        return collegeFootballAPIService.getAllFBSTeams();
    }

    @GetMapping("/{teamId}")
    public Team getTeamByTeamId(@PathVariable("teamId") int teamId){
        return collegeFootballAPIService.getAllTeams().stream()
                                            .filter(x -> x.getId() == teamId)
                                            .findFirst()
                                            .orElse(null);
    }

    @GetMapping("/color")
    public TeamResponse getColor(@RequestParam("team") String team){
        return teamService.getTeamColor(team);
    }
}
