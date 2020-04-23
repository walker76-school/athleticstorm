/*
 * Filename: RosterPlayer.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Relates a player to team for a specific year
 *
 * @author Ian Laird
 */

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "ROSTER_PLAYER")
@IdClass(RosterPlayer.RosterPlayerId.class)
@EqualsAndHashCode
public class RosterPlayer implements Comparable<RosterPlayer> {

    @Data
    @EqualsAndHashCode
    public static class RosterPlayerId implements Serializable {
        private Long player;
        private int year;
    }

    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer year;

    @Id
    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Player player;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Team team;

    /**
     * Constructs a RosterPlayer from a Player and Team
     * @param p the player
     * @param t the team
     */
    public RosterPlayer(Player p, Team t) {
        this.player = p;
        this.team = t;
    }

    /**
     * Compares player to another player
     * @param rosterPlayer the other player
     * @return comparison
     *
     * {@inheritDoc}
     *
     */
    @Override
    public int compareTo(RosterPlayer rosterPlayer) {
        int idCompare = this.player.compareTo(rosterPlayer.getPlayer());
        return idCompare != 0 ? idCompare : this.year.compareTo(rosterPlayer.getYear());
    }
}
