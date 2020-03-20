package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import edu.baylor.ecs.athleticstorm.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/color")
    public TeamResponse getColor(@RequestParam("team") String team){
        return teamService.getTeamColor(team);
    }
}
