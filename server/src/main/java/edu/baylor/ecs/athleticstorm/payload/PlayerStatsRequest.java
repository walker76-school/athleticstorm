/*
 * Filename: PlayerStatsRequest.java
 * Author: Andrew Walker
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A request for player stats
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatsRequest {

    private String firstName;
    private String lastName;
    private String team;
    private long year;
}
