/*
 * Filename: SPDefense.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.spRatings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SP+ defense stats
 *
 * @author Andrew Walker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPDefense {

    private double rating;
    private double success;
    private double explosiveness;
    private double rushing;
    private double passing;
    private double standardDowns;
    private double passingDowns;
    private double runRate;
    private double pace;
    SPDefenseHavoc havoc;
}