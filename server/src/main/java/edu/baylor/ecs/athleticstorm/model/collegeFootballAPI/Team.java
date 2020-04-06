package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "TEAM")
@EqualsAndHashCode
public class Team implements Comparable<Team>{

    @Id
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SCHOOL")
    @EqualsAndHashCode.Exclude
    private String school;

    @Column(name = "MASCOT")
    @EqualsAndHashCode.Exclude
    private String mascot;

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

    @ElementCollection
    @EqualsAndHashCode.Exclude
    private List<String> logos;

    @OneToMany(mappedBy = "team")
    @EqualsAndHashCode.Exclude
    private Set<Coach> coaches = new TreeSet<>();

    @OneToMany(mappedBy = "team")
    @EqualsAndHashCode.Exclude
    private Set<RosterPlayer> rosterPlayers = new TreeSet<>();

    @Override
    public int compareTo(Team team) {
        return this.id.compareTo(team.getId());
    }

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
    }
}
