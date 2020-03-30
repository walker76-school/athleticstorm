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
public class RosterPlayer {

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
    private int year;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Player player;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Team team;

}
