/*
 * Filename: RosterPlayer.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
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
        private Season.SeasonID season;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Player player;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "YEAR", referencedColumnName = "YEAR"),
            @JoinColumn(name = "SCHOOL", referencedColumnName = "SCHOOL")
    })
    @EqualsAndHashCode.Exclude
    private Season season;

    public RosterPlayer(Player p, Season s){
        this.player = p;
        this.season = s;
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
        return idCompare != 0 ? idCompare : this.season.compareTo(rosterPlayer.getSeason());
    }
}
