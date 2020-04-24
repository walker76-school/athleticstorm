/*
 * Filename: ApiResponse.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a response from the API
 */
@AllArgsConstructor
@Data
public class ApiResponse {
    private Boolean success;
    private String message;
}
