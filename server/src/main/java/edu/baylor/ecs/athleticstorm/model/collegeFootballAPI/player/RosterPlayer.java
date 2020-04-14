/******************************************************************************
 *
 * RosterPlayer.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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

    public RosterPlayer(Player p, Team t) {
        this.player = p;
        this.team = t;
    }

    @Override
    public int compareTo(RosterPlayer rosterPlayer) {
        int idCompare = this.player.compareTo(rosterPlayer.getPlayer());
        return idCompare != 0 ? idCompare : this.year.compareTo(rosterPlayer.getYear());
    }
}
