package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.spRatings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPOffense {

    private double rating;
    private double success;
    private double explosiveness;
    private double rushing;
    private double passing;
    private double standardDowns;
    private double passingDowns;
    private double runRate;
    private double pace;
}
