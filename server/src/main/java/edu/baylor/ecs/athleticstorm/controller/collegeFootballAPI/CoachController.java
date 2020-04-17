package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping("/all")
    public List<CoachDTO> getAllCoaches(){
        return coachService.getAllCoaches();
    }

    @GetMapping("/currentCoaches/{teamId}" )
    public List<CoachDTO> getCoachesByTeamId(@PathVariable("teamId") Long teamId){
        return coachService.getCoachesByTeamId(teamId);
    }

    @GetMapping("/byTeamId/{teamId}" )
    public List<CoachDTO> getHistoricalCoachesByTeamId(@PathVariable("teamId") Long teamId){
        return coachService.getHistoricalCoachesByTeamId(teamId);
    }

    @GetMapping("/byName/{name}")
    public CoachDTO getCoachByName(@PathVariable("name") String name){
        return coachService.getCoachByName(name);
    }

    @GetMapping("/record/byName/{name}" )
    public CoachRecord getCoachRecordByName(@PathVariable("name") String name){
        CoachDTO coach = coachService.getCoachByName(name);
        return coachService.buildRecordFromCoach(coach);
    }

}
