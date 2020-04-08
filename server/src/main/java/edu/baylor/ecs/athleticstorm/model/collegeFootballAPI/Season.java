/******************************************************************************
 *
 * Season.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Season
 *
 * Represents a season which is a year for a specific team
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "SEASON")
@IdClass(Season.SeasonID.class)
@EqualsAndHashCode
public class Season implements Comparable<Season> {

    @Data
    public static class SeasonID implements Serializable {
        private String school;
        private int year;
    }

    // the name of the school that this team plays for
    @Id
    @Column(name = "SCHOOL", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String school;

    // the year of the season
    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer year;

    // the coaches for this season
    @ManyToMany(mappedBy = "seasons", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Coach> coaches = new TreeSet<>();

    // the number of games played
    @Column(name = "GAMES")
    @EqualsAndHashCode.Exclude
    private int games;

    // the number of wins
    @Column(name = "WINS")
    @EqualsAndHashCode.Exclude
    private int wins;

    // the number of losses
    @Column(name = "LOSSES")
    @EqualsAndHashCode.Exclude
    private int losses;

    // the number of ties
    @Column(name = "TIES")
    @EqualsAndHashCode.Exclude
    private int ties;

    // the pre season rank
    @Column(name = "PRESEASON_RANK")
    @EqualsAndHashCode.Exclude
    private int preseason_rank;

    // the post season rank
    @Column(name = "POSTSEASON_RANK")
    @EqualsAndHashCode.Exclude
    private int postseason_rank;

    @Override
    public int compareTo(Season season) {
        int schoolCompare = this.school.compareTo(season.getSchool());
        return schoolCompare != 0 ? schoolCompare : this.year.compareTo(season.getYear());
    }

    /**
     * creates a Season from a DTO
     * @param s the data
     */
    public Season(SeasonDTO s){
        this.games = s.getGames();
        this.wins = s.getWins();
        this.losses = s.getLosses();
        this.ties = s.getTies();
        this.preseason_rank = s.getPreseason_rank();
        this.postseason_rank = s.getPostseason_rank();
        this.year = s.getYear();
        this.school = s.getSchool();
    }
}
