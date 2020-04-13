package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.model.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.model.coach.CoachStats;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CollegeFootballAPIService collegeFootballAPIService;

    @GetMapping("/all")
    public List<Coach> getAllCoaches(){
        return collegeFootballAPIService.getAllCoaches();
    }

    @GetMapping("/allStats")
    public List<CoachStats> getAllCoachRecords(){
        return collegeFootballAPIService.getAllCoaches().parallelStream().map(CoachStats::new).collect(Collectors.toList());
    }

    @GetMapping("/byTeamId/{teamId}" )
    public List<Coach> getCoachesByTeamId(@PathVariable("teamId") int teamId){
        return collegeFootballAPIService.getCoachesByTeamId(teamId);
    }

    @GetMapping("/record/byName/{name}" )
    public CoachRecord getCoachRecordByName(@PathVariable("name") String name){
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        Coach coach = collegeFootballAPIService.getCoachByName(firstName, lastName);
        return collegeFootballAPIService.buildRecordFromCoach(coach);
    }

}
