/*
 * Filename: SPRating.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.spRatings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SP+ rating from CollegeFootballAPI
 *
 * @author Andrew Walker
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPRating {
    private int year;
    private String team;
    private String conference;
    private int rating;
    private int secondOrderWins;
    private int sos;
    private SPOffense offense;
    private SPDefense defense;
    private SPSpecial specialTeams;
}
