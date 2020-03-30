/******************************************************************************
 *
 * CoachDTO.java
 *
 * author: Ian laird
 *
 * Created 3/29/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.DTO.SeasonDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CoachDTO {

    private String first_name;
    private String last_name;
    private List<SeasonDTO> seasons;

    public CoachDTO(Coach c){
        this.first_name = c.getName().split("\\s+")[0];
        this.last_name = c.getName().split("\\s+")[1];
        this.seasons = new LinkedList<>();
        c.getSeasons().forEach(x -> this.seasons.add(new SeasonDTO(x)));
    }
}

