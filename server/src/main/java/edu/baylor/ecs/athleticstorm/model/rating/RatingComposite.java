package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * composite rating
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
