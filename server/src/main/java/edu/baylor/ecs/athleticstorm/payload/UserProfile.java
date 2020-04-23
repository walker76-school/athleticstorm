/*
 * Filename: UserProfile.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * A user's profile data
 *
 * Mostly unused but could be extended in future for more data
 */
@Data
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private Instant joinedAt;
}
