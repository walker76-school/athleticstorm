package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    public List<Team> getAllTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/fbs")
    public List<Team> getAllFBSTeams(){
        return teamService.getAllFBSTeams();
    }

    @GetMapping("/{teamId}")
    public Team getTeamByTeamId(@PathVariable("teamId") Long teamId){
        return teamService.getTeamById(teamId);
    }

    @GetMapping("/color")
    public TeamResponse getColor(@RequestParam("team") String team){
        return teamService.getTeamColor(team);
    }
}
