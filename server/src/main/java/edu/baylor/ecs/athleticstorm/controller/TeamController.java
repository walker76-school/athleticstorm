package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.TeamDTO;
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
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/fbs")
    public List<TeamDTO> getAllFBSTeams(){
        return teamService.getAllFBSTeams();
    }

    @GetMapping("/{teamId}")
    public TeamDTO getTeamByTeamId(@PathVariable("teamId") Long teamId){
        return teamService.getTeamById(teamId);
    }

}
