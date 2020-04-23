/*
 * Filename: CoachDTO.java
 * Author: Ian Laird
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * DTO for a Coach
 *
 * @author Ian Laird
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CoachDTO {

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    private List<SeasonDTO> seasons;

    /**
     * Creates a CoachDTO from a given Coach
     * @param c the given Coach
     */
    public CoachDTO(Coach c){
        this.first_name = c.getName().split("\\s+")[0];
        this.last_name = c.getName().split("\\s+")[1];
        this.seasons = new LinkedList<>();
        c.getSeasons().forEach(x -> this.seasons.add(new SeasonDTO(x)));
        this.seasons.sort(Comparator.comparing(SeasonDTO::getYear).reversed());
    }
}

