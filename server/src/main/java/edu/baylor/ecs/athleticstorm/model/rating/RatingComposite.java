/*
 * Filename: RatingComposite.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Composite rating of all three types for a week
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
public class RatingComposite {
    private double coach;
    private double OC;
    private double DC;
}
