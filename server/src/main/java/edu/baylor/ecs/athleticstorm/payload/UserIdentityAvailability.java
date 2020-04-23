/*
 * Filename: UserIdentityRequest.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The availability of a username
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
public class UserIdentityAvailability {
    private Boolean available;
}
