package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "ROSTER_PLAYER")
public class RosterPlayer {

    @Embeddable
    public static class RosterPlayerId implements Serializable {
        @Id
        @Column(name = "PLAYER_ID")
        private Long id;

        @Column(name = "YEAR")
        private int year;
    }

    @EmbeddedId
    private RosterPlayerId id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    private Team team;

}
