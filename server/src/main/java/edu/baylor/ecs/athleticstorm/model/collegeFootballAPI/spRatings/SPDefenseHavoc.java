/*
 * Filename: SPDefenseHavoc.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.spRatings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SP+ defense havoc stats
 *
 * @author Andrew Walker
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPDefenseHavoc {

    private double total;
    private double frontSeven;
    private double db;
}
