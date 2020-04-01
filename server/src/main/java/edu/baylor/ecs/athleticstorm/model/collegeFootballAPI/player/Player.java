package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.DTO.player.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "PLAYER")
@EqualsAndHashCode
public class Player implements Comparable<Player>{

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
    private Usage usage = null;

    @OneToMany(mappedBy = "player")
    @EqualsAndHashCode.Exclude
    private Set<RosterPlayer> rosterPlayerList = new TreeSet<>();

    @Override
    public int compareTo(Player player) {
        return this.id.compareTo(player.getId());
    }

    public Player(PlayerDTO player){
        this.id = player.getId();
        this.team = player.getTeam();
        this.name = player.getName();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.height = player.getHeight();
        this.weight = player.getWeight();
        this.jersey = player.getJersey();
        this.position = player.getPosition();
        this.hometown = player.getHometown();
        this.teamColor = player.getTeamColor();
    }
}
