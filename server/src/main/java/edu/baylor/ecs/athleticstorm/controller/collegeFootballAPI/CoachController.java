package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.DTO.coach.RatedCoachDTO;
import edu.baylor.ecs.athleticstorm.model.coach.CoachStats;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.model.rating.RatingKey;
import edu.baylor.ecs.athleticstorm.repository.RatingRepository;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<RatedCoachDTO> getCoachesByTeamId(@PathVariable("teamId") Long teamId){
        return coachService.getCoachesByTeamId(teamId).stream().map(x -> {
            RatedCoachDTO ratedCoachDTO = new RatedCoachDTO(x);
            List<Rating> ratings = ratingRepository.findAllByKey_Name(x.getFirst_name() + " " + x.getLast_name());
            Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
            ratedCoachDTO.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
            return ratedCoachDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/byTeamId/{teamId}" )
    public List<RatedCoachDTO> getHistoricalCoachesByTeamId(@PathVariable("teamId") Long teamId){
        return coachService.getHistoricalCoachesByTeamId(teamId).stream().map(x -> {
            RatedCoachDTO ratedCoachDTO = new RatedCoachDTO(x);
            List<Rating> ratings = ratingRepository.findAllByKey_Name(x.getFirst_name() + " " + x.getLast_name());
            Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
            ratedCoachDTO.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
            return ratedCoachDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/byName/{name}")
    public RatedCoachDTO getCoachByName(@PathVariable("name") String name){
        CoachDTO x =  coachService.getCoachByName(name);
        RatedCoachDTO ratedCoachDTO = new RatedCoachDTO(x);
        List<Rating> ratings = ratingRepository.findAllByKey_Name(x.getFirst_name() + " " + x.getLast_name());
        Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
        ratedCoachDTO.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
        return ratedCoachDTO;
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

    @GetMapping("/allStats")
    public List<CoachStats> getAllCoachRecords(){
        return coachService.getAllCoaches().parallelStream().map(x -> {
            RatedCoachDTO ratedCoachDTO = new RatedCoachDTO(x);
            List<Rating> ratings = ratingRepository.findAllByKey_Name(x.getFirst_name() + " " + x.getLast_name());
            Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
            ratedCoachDTO.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
            return ratedCoachDTO;
        }).map(CoachStats::new).collect(Collectors.toList());
    }


}
