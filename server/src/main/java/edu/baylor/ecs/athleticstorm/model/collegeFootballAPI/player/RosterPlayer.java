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
        private Long id;
        private int year;
    }

    @Id
    @Column(name = "PLAYER_ID", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer year;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    @MapsId
    @EqualsAndHashCode.Exclude
    private Player player;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Team team;

    public RosterPlayer(Long id, Team t) {
        this.id = new Long(id);
        this.team = t;
    }

    @Override
    public int compareTo(RosterPlayer rosterPlayer) {
        int idCompare = this.id.compareTo(rosterPlayer.getId());
        return idCompare != 0 ? idCompare : this.year.compareTo(rosterPlayer.getYear());
    }
}
