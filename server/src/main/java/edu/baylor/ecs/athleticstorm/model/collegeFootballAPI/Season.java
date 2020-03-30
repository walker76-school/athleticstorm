package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Season {

    @Data
    public static class SeasonID implements Serializable {
        private String school;
        private int year;
    }

    @Id
    @Column(name = "SCHOOL", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String school;

    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private int year;

    @ManyToMany(mappedBy = "seasons")
    @EqualsAndHashCode.Exclude
    private List<Coach> coaches;


    @Column(name = "GAMES")
    @EqualsAndHashCode.Exclude
    private int games;

    @Column(name = "WINS")
    @EqualsAndHashCode.Exclude
    private int wins;

    @Column(name = "LOSSES")
    @EqualsAndHashCode.Exclude
    private int losses;

    @Column(name = "TIES")
    @EqualsAndHashCode.Exclude
    private int ties;

    @Column(name = "PRESEASON_RANK")
    @EqualsAndHashCode.Exclude
    private int preseason_rank;

    @Column(name = "POSTSEASON_RANK")
    @EqualsAndHashCode.Exclude
    private int postseason_rank;

}
