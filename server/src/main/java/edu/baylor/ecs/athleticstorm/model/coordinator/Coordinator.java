package edu.baylor.ecs.athleticstorm.model.coordinator;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COORDINATOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinator implements Comparable<Coordinator>{

    @Id
    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "POSITION")
    @EqualsAndHashCode.Exclude
    private String position;

    @Column(name = "START_YEAR")
    @EqualsAndHashCode.Exclude
    private int startYear;

    @Column(name = "END_YEAR")
    @EqualsAndHashCode.Exclude
    private int endYear;

    // the team the coach is currently coaching
    // will be null if the coach is not coaching a team in the last year
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID", nullable = true)
    @EqualsAndHashCode.Exclude
    private Team team = null;

    @Override
    public int compareTo(Coordinator o) {
        return this.name.compareTo(o.getName());
    }
}
