package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    @Id
    @Column(name = "SCHOOL", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String school;

    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long year;

    @ManyToMany(mappedBy = "seasons")
    @EqualsAndHashCode.Exclude
    private Set<Coach> coaches;

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

    @Override
    public int compareTo(Season season) {
        int schoolCompare = this.school.compareTo(season.getSchool());
        return schoolCompare != 0 ? schoolCompare : this.year.compareTo(season.getYear());
    }
}
