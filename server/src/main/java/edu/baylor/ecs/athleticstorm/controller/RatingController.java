package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.RatingRequest;
import edu.baylor.ecs.athleticstorm.DTO.RatingResponse;
import edu.baylor.ecs.athleticstorm.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService coachService;

    @GetMapping("/rating/all/{year}")
    public List<RatingResponse> getRatingAll(@PathVariable Integer year){
        //return coachService.getAllRatings(ratingRequest);
        return null;
    }

    @GetMapping("/rating/allPlayer/{year}")
    public List<RatingResponse> getRatingAllPlayers(@PathVariable Integer year){
        //return coachService.getAllRatings(ratingRequest);
        return null;
    }

    @GetMapping("/rating/allCoaches/{year}")
    public List<RatingResponse> getRatingAllCoaches(@PathVariable Integer year){
        //return coachService.getAllRatings(ratingRequest);
        return null;
    }

    @GetMapping("/rating/{year}")
    public RatingResponse getIndividualRatingYear(@PathVariable Integer year, @RequestBody RatingRequest ratingRequest) {
        //return coachService.getRatingYear(year, ratingRequest);
        return null;
    }
}
