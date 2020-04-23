/*
 * Filename: CoachStats.java
 * Author: Andrew Walker
 * Date Last Modified: 4/19/2020
 */

package edu.baylor.ecs.athleticstorm.model.coach;

import edu.baylor.ecs.athleticstorm.DTO.coach.RatedCoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Condensed stats for a Coach
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachStats {
    @NotBlank
    private String first_name;
    @NotBlank
    private String last_name;
    private int wins;
    private int losses;
    private double winPercentage;
    private double rating;

    /**
     * Creates a CoachStats from a given RatedCoachDTO
     * @param coach a given coach
     */
    public CoachStats(RatedCoachDTO coach){
        this.first_name = coach.getFirst_name();
        this.last_name = coach.getLast_name();
        this.wins = coach.getSeasons().stream().map(SeasonDTO::getWins).mapToInt(Integer::intValue).sum();
        this.losses = coach.getSeasons().stream().map(SeasonDTO::getLosses).mapToInt(Integer::intValue).sum();
        this.winPercentage = (1.0 * this.wins) / (this.wins + this.losses);
        this.rating = coach.getRating();
    }
}
