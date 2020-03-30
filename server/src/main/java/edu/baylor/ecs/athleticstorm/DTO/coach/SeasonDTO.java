/******************************************************************************
 *
 * SeasonDTO.java
 *
 * author: Ian laird
 *
 * Created 3/29/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SeasonDTO {

    private String school;
    private int year;
    private int games;
    private int wins;
    private int losses;
    private int ties;
    private int preseason_rank;
    private int postseason_rank;

    public SeasonDTO(Season s){
        this.school = s.getSchool();
        this.year = s.getYear();
        this.games = s.getGames();
        this.wins = s.getWins();
        this.losses = s.getLosses();
        this.ties = s.getTies();
        this.preseason_rank = s.getPreseason_rank();
        this.postseason_rank = s.getPostseason_rank();
    }
}
