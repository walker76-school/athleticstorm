package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import edu.baylor.ecs.athleticstorm.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CollegeFootballAPIService collegeFootballAPIService;

    @GetMapping("/all")
    public List<Coach> getAllCoaches(){
        return collegeFootballAPIService.getAllCoaches();
    }

    @GetMapping("/byTeamId/{teamId}" )
    public List<Coach> getAllFBSTeams(@PathVariable("teamId") int teamId){
        return collegeFootballAPIService.getCoachesByTeamId(teamId);
    }

}
