package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.model.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
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
    public List<Coach> getCoachesByTeamId(@PathVariable("teamId") int teamId){
        return collegeFootballAPIService.getCoachesByTeamId(teamId);
    }

    @GetMapping("/record/byName/{name}" )
    public CoachRecord getCoachRecordByName(@PathVariable("name") String name){
        String firstName = name.split("-")[0];
        String lastName = name.split("-")[1];
        Coach coach = collegeFootballAPIService.getCoachByName(firstName, lastName);
        return collegeFootballAPIService.buildRecordFromCoach(coach);
    }

}
