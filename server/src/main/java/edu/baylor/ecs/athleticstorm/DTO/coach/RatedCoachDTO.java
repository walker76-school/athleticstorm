/*
 * Filename: RatedCoachDTO.java
 * Author: Andrew Walker
 * Date Last Modified: 4/19/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * DTO for a Coach with rating
 *
 * @author Andrew Walker
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RatedCoachDTO {

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    private double rating;

    private List<SeasonDTO> seasons;

    /**
     * Creates a RatedCoachDTO from a CoachDTO
     * @param c a given coach
     */
    public RatedCoachDTO(CoachDTO c){
        this.first_name = c.getFirst_name();
        this.last_name = c.getLast_name();
        this.seasons = c.getSeasons();
        this.rating = -1;
    }
}
