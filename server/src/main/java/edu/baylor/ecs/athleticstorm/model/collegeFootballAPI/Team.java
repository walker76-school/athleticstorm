/*
 * Filename: Team.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;

import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a College Football Team
 *
 * @author Ian Laird
 */

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "TEAM")
@EqualsAndHashCode
public class Team implements Comparable<Team>{

    public static List<String> FBS_CONFERENCES = Arrays.asList("SEC", "Big 12", "ACC", "Big Ten", "PAC-12", "Mountain West", "Sun Belt", "Mid_american", "FBS Independents", "Conference USA");

    // the id of the team
    @Id
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "IS_FBS")
    @EqualsAndHashCode.Exclude
    private boolean is_fbs;

    // the name of the school
    @Column(name = "SCHOOL")
    @EqualsAndHashCode.Exclude
    private String school;

    // the mascot
    @Column(name = "MASCOT")
    @EqualsAndHashCode.Exclude
    private String mascot;

    // the abbreviation for the team
    @Column(name = "ABBREVIATION")
    @EqualsAndHashCode.Exclude
    private String abbreviation;

    @Column(name = "ALT_NAME_1")
    @EqualsAndHashCode.Exclude
    private String alt_name_1;

    @Column(name = "ALT_NAME_2")
    @EqualsAndHashCode.Exclude
    private String alt_name_2;

    @Column(name = "ALT_NAME_3")
    @EqualsAndHashCode.Exclude
    private String alt_name_3;

    @Column(name = "CONFERENCE")
    @EqualsAndHashCode.Exclude
    private String conference;

    @Column(name = "DIVISION")
    @EqualsAndHashCode.Exclude
    private String division;

    @Column(name = "COLOR")
    @EqualsAndHashCode.Exclude
    private String color;

    @Column(name = "ALT_COLOR")
    @EqualsAndHashCode.Exclude
    private String alt_color;

    // url of the logos
    @ElementCollection
    @EqualsAndHashCode.Exclude
    private List<String> logos;

    // the current coaches of the team
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    @EqualsAndHashCode.Exclude
    private Set<Coach> coaches = new TreeSet<>();

    // the players for the current coordinators of the team
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    @EqualsAndHashCode.Exclude
    private Set<Coordinator> coordinators = new TreeSet<>();

    /**
     * {@inheritDoc}
     * @param team the team being compared to
     * @return the comparison of the teams
     */
    @Override
    public int compareTo(Team team) {
        return this.id.compareTo(team.getId());
    }

    /**
     * Creates a Team from a DTO
     * @param team the data
     */
    public Team(TeamDTO team){
        this.id = team.getId();
        this.school = team.getSchool();
        this.mascot = team.getMascot();
        this.abbreviation = team.getAbbreviation();
        this.alt_name_1 = team.getAlt_name_1();
        this.alt_name_2 = team.getAlt_name_2();
        this.alt_name_3 = team.getAlt_name_3();
        this.conference = team.getConference();
        this.division = team.getDivision();
        this.color = team.getColor();
        this.alt_color = team.getAlt_color();
        this.logos = team.getLogos();
        this.is_fbs = FBS_CONFERENCES.contains(this.conference);
    }
}
