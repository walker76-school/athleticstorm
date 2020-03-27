package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "SEASON")
@IdClass(Season.SeasonID.class)
public class Season {

    public static class SeasonID implements Serializable {
        private String school;
        private int year;
    }

    @Id
    @Column(name = "SCHOOL", insertable = false, updatable = false)
    private String school;

    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    private int year;

    @ManyToMany(mappedBy = "seasons")
    private List<Coach> coaches;

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
