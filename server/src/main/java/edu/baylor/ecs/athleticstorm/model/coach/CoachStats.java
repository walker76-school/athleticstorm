package edu.baylor.ecs.athleticstorm.model.coach;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachStats {

    private String firstName;
    private String lastName;
    private int wins;
    private int losses;
    private double winPercentage;

    public CoachStats(Coach coach){
        this.firstName = coach.getFirst_name();
        this.lastName = coach.getLast_name();
        this.wins = coach.getSeasons().stream().map(Season::getWins).mapToInt(Integer::intValue).sum();
        this.losses = coach.getSeasons().stream().map(Season::getLosses).mapToInt(Integer::intValue).sum();
        this.winPercentage = (1.0 * this.wins) / (this.wins + this.losses);
    }
}
