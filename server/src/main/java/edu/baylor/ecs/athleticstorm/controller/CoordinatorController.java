/*
 * Filename: CoordinatorController.java
 * Author: Andrew Walker
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.RatedCoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coordinator.CoordinatorDTO;
import edu.baylor.ecs.athleticstorm.DTO.coordinator.RatedCoordinatorDTO;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.repository.RatingRepository;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for Coordinator data
 *
 * @author Andrew Walker
 */
@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Returns a list of coordinators for a given team
     * @param teamId a team id
     * @return a list of coordinators for a given team
     */
    @GetMapping("/byTeamId/{teamId}")
    public List<CoordinatorDTO> getByTeamId(@PathVariable("teamId") int teamId) {
        return coordinatorService.getCoordinatorsByTeamId(teamId);
    }

    /**
     * Returns a coordinator by name
     * @param name the coordinator's name
     * @return the coordinator with the given name
     */
    @GetMapping("/byName/{name}")
    public RatedCoordinatorDTO getCoachByName(@PathVariable("name") String name){
        CoordinatorDTO x =  coordinatorService.getCoordinatorByName(name);
        RatedCoordinatorDTO ratedCoordinatorDTO = new RatedCoordinatorDTO(x);
        List<Rating> ratings = ratingRepository.findAllByKey_Name(x.getName());
        Optional<Rating> ratingOpt = ratings.stream().max(Rating::compareTo);
        ratedCoordinatorDTO.setRating(ratingOpt.isPresent() ? ratingOpt.get().getRating() : -1);
        return ratedCoordinatorDTO;
    }
}
