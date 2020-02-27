package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.RatingRequest;
import edu.baylor.ecs.athleticstorm.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping("/rating/all")
    public void getCoachRatingAll(@RequestBody RatingRequest ratingRequest){
        //return coachService.getAllRatings(ratingRequest);
    }

    @GetMapping("/rating/{year}")
    public void getCoachRatingYear(@PathVariable Integer year, @RequestBody RatingRequest ratingRequest) {
        //return coachService.getRatingYear(year, ratingRequest);
    }
}
