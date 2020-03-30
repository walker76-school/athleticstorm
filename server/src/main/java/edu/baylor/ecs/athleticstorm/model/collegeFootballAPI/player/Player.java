package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "PLAYER")
@EqualsAndHashCode
public class Player {

    @Id
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "TEAM")
    @EqualsAndHashCode.Exclude
    private String team;

    @Column(name = "NAME")
    @EqualsAndHashCode.Exclude
    private String name;

    @Column(name = "FIRST_NAME")
    @EqualsAndHashCode.Exclude
    private String firstName;

    @Column(name = "LAST_NAME")
    @EqualsAndHashCode.Exclude
    private String lastName;

    @Column(name = "HEIGHT")
    @EqualsAndHashCode.Exclude
    private int height;

    @Column(name = "WEIGHT")
    @EqualsAndHashCode.Exclude
    private int weight;

    @Column(name = "JERSEY")
    @EqualsAndHashCode.Exclude
    private int jersey;

    @Column(name = "POSITION")
    @EqualsAndHashCode.Exclude
    private String position;

    @Column(name = "HOMETOWN")
    @EqualsAndHashCode.Exclude
    private String hometown;

    @Column(name = "TEAM_COLOR")
    @EqualsAndHashCode.Exclude
    private String teamColor;

    @OneToOne(mappedBy = "player")
    @EqualsAndHashCode.Exclude
    private Usage usage;

    @OneToMany(mappedBy = "player")
    @EqualsAndHashCode.Exclude
    private List<RosterPlayer> rosterPlayerList;
}
