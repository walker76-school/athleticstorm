/******************************************************************************
 *
 * RatingResponse.java
 *
 * author: Ian laird
 *
 * Created 2/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.DTO;

import lombok.Data;

@Data
public class RatingResponse {

    private String name;
    private Integer year;
    private Double rating;
    private String team;
}
