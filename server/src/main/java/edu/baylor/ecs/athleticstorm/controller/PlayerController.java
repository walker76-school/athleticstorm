package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.RatingRequest;
import edu.baylor.ecs.athleticstorm.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/rating/all")
    public void getPlayerRatingAll(@RequestBody RequestBody requestBody){
        //return playerService.getAllRatings(requestBody);
    }

    @GetMapping("/rating/{year}")
    public void getPlayerRatingYear(@PathVariable Integer year, @RequestBody RatingRequest ratingRequest) {
        //return playerService.getRatingYear(year, ratingRequest);
    }
}
