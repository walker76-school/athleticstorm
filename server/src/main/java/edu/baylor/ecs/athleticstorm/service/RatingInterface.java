package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.DTO.RatingRequest;

public abstract class RatingInterface {

    public abstract void getAllRatings(RatingRequest ratingRequest);

    public abstract void getRatingYear(Integer year, RatingRequest ratingRequest);
}
