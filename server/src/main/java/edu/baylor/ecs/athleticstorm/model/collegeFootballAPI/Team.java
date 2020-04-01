package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    private Set<Coach> coaches;

    @OneToMany(mappedBy = "team")
    @EqualsAndHashCode.Exclude
    private Set<RosterPlayer> rosterPlayers;

    @Override
    public int compareTo(Team team) {
        return this.id.compareTo(team.getId());
    }
}
