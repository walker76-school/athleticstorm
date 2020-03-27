package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "SEASON")
public class Season {

    @Embeddable
    public static class SeasonID implements Serializable {
        @Column(name = "SCHOOL")
        private String school;

        @Column(name = "YEAR")
        private int year;
    }

    @EmbeddedId
    private SeasonID seasonId;

    @Column(name = "GAMES")
    private int games;

    @Column(name = "WINS")
    private int wins;

    @Column(name = "LOSSES")
    private int losses;

    @Column(name = "TIES")
    private int ties;

    @Column(name = "PRESEASON_RANK")
    private int preseason_rank;

    @Column(name = "POSTSEASON_RANK")
    private int postseason_rank;

    @Transient
    public int getYear(){
        return this.seasonId.year;
    }
}
