package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.model.rating.RatingKey;
import edu.baylor.ecs.athleticstorm.repository.RatingRepository;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private RatingRepository ratingRepository;

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
        CoachRecord record = coachService.buildRecordFromCoach(coach);
        List<Rating> ratings = ratingRepository.findAllByKey_Name(coach.getFirst_name() + " " + coach.getLast_name());
        Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
        record.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
        return record;
    }

}
