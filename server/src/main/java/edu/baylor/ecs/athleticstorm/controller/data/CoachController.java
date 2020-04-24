/*
 * Filename: CoachController.java
 * Author: Ian Laird
 * Date Last Modified: 4/19/2020
 */

package edu.baylor.ecs.athleticstorm.controller.data;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.DTO.coach.RatedCoachDTO;
import edu.baylor.ecs.athleticstorm.model.coach.CoachStats;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.repository.RatingRepository;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for Coach data
 *
 * @author Ian Laird
 */
@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Returns all coaches
     * @return all coaches
     */
    @GetMapping("/all")
    public List<CoachDTO> getAllCoaches(){
        return coachService.getAllCoaches();
    }

    /**
     * Returns all current coaches of a team given by a teamId
     * @param teamId the current team's id
     * @return all current coaches of a team given by the teamId
     */
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

    /**
     * Returns all historical coaches given a teamId
     * @param teamId the team's id
     * @return all historical coaches given a teamId
     */
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

    /**
     * Returns a coach by name
     * @param name the coach's name
     * @return the coach with the given name
     */
    @GetMapping("/byName/{name}")
    public RatedCoachDTO getCoachByName(@PathVariable("name") String name){
        CoachDTO x =  coachService.getCoachByName(name);
        RatedCoachDTO ratedCoachDTO = new RatedCoachDTO(x);
        List<Rating> ratings = ratingRepository.findAllByKey_Name(x.getFirst_name() + " " + x.getLast_name());
        Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
        ratedCoachDTO.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
        return ratedCoachDTO;
    }

    /**
     * Returns a coach's record by name
     * @param name the coach's name
     * @return the coach's record with the given name
     */
    @GetMapping("/record/byName/{name}" )
    public CoachRecord getCoachRecordByName(@PathVariable("name") String name){
        CoachDTO coach = coachService.getCoachByName(name);
        CoachRecord record = coachService.buildRecordFromCoach(coach);
        List<Rating> ratings = ratingRepository.findAllByKey_Name(coach.getFirst_name() + " " + coach.getLast_name());
        Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
        record.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
        return record;
    }

    /**
     * Returns a list of condensed coach stats for ranking
     * @return a list of condensed coach stats for ranking
     */
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
