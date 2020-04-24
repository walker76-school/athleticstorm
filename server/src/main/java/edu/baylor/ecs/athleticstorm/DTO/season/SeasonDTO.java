/*
 * Filename: SeasonDTO.java
 * Author: Ian Laird
 * Date Last Modified: 3/30/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.season;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a Season
 *
 * @author Ian Laird
 */
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

    /**
     * Constructs a SeasonDTO from a Season
     * @param s a Season
     */
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
