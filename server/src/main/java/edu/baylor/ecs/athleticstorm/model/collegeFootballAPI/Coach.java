package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity(name = "Coach")
@Table(name = "COACH")
@EqualsAndHashCode
public class Coach implements Comparable<Coach> {

    @Id
    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "COACH_TO_SEASON",
            joinColumns =
            @JoinColumn(name = "COACH_NAME", referencedColumnName = "NAME"),
            inverseJoinColumns = {
                    @JoinColumn(name = "SCHOOL", referencedColumnName = "SCHOOL"),
                    @JoinColumn(name = "YEAR", referencedColumnName = "YEAR")
            }
    )
    @EqualsAndHashCode.Exclude
    private Set<Season> seasons;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Team team;

    @Override
    public int compareTo(Coach coach) {
        return this.name.compareTo(coach.getName());
    }
}
