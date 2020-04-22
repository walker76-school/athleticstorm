package edu.baylor.ecs.athleticstorm.model.coordinator;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Coordinator
 *
 * represents a college football coordinator
 *
 * @author Andrew Walker
 */
@Entity
@Table(name = "COORDINATOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Coordinator.CoordinatorID.class)
@EqualsAndHashCode
public class Coordinator implements Comparable<Coordinator>{

    /**
     * the id of a coordinator
     *
     * @author Andrew Walker
     */
    @Data
    public static class CoordinatorID implements Serializable {
        private String name;
        private int startYear;
    }

    @Id
    @Column(name = "NAME", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "POSITION")
    @EqualsAndHashCode.Exclude
    private String position;

    @Id
    @Column(name = "START_YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
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

    /**
     * {@inheritDoc}
     * @param o the coordinator being compared to
     * @return the comparison of the two coordinators
     */
    @Override
    public int compareTo(Coordinator o) {
        if(!this.name.equalsIgnoreCase(o.getName())) {
            return this.name.compareTo(o.getName());
        }
        return Integer.compare(this.startYear, o.getStartYear());
    }
}
