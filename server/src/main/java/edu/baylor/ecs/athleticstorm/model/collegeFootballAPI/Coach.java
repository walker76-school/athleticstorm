/*
 * Filename: Coach.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a Coach of a Team
 *
 * @author Ian Laird
 */
@AllArgsConstructor
@Data
@Entity(name = "Coach")
@Table(name = "COACH")
@EqualsAndHashCode
@NoArgsConstructor
public class Coach implements Comparable<Coach> {

    // the name of the coach
    @Id
    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    // all of the seasons that this coach has coached
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    private Set<Season> seasons = new TreeSet<>();

    // the team the coach is currently coaching
    // will be null if the coach is not coaching a team in the last year
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID", nullable = true)
    @EqualsAndHashCode.Exclude
    private Team team = null;

    /**
     * {@inheritDoc}
     * @param coach the coach being compared to
     * @return the comparison of this coach and the other coach
     */
    @Override
    public int compareTo(Coach coach) {
        return this.name.compareTo(coach.getName());
    }

    /**
     * Creates a coach of a given name
     * @param name the name of the coach
     */
    public Coach(String name){
        this.name = name;
    }

    /**
     * Creates a coach from a DTO
     * @param coach the data to create the coach from
     * @param seasons the set of existing seasons for ALL coaches (this is used so that seasons are not duplicated)
     */
    public Coach(CoachDTO coach, Set<Season> seasons){
        this.name = coach.getFirst_name() + " " + coach.getLast_name();

        for(SeasonDTO seasonDTO: coach.getSeasons()){
            Optional<Season> opt = seasons.stream().filter(x -> x.getSchool().equalsIgnoreCase(seasonDTO.getSchool()) && x.getYear() == seasonDTO.getYear()).findAny();
            Season s;
            if(!opt.isPresent()){
                s = new Season(seasonDTO);
                seasons.add(s);
            }
            else{
                s = opt.get();
            }
            s.getCoaches().add(this);
            this.getSeasons().add(s);
        }
    }
}
