package edu.baylor.ecs.athleticstorm.model.coach;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.RatedCoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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

    public CoachStats(RatedCoachDTO coach){
        this.first_name = coach.getFirst_name();
        this.last_name = coach.getLast_name();
        this.wins = coach.getSeasons().stream().map(SeasonDTO::getWins).mapToInt(Integer::intValue).sum();
        this.losses = coach.getSeasons().stream().map(SeasonDTO::getLosses).mapToInt(Integer::intValue).sum();
        this.winPercentage = (1.0 * this.wins) / (this.wins + this.losses);
        this.rating = coach.getRating();
    }
}
