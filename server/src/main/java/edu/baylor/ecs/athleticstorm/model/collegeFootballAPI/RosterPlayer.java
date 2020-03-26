package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "ROSTER_PLAYER")
public class RosterPlayer {

    @ManyToOne
    private Player player;

    @Column(name = "YEAR")
    private int year;

    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "ID")
    private Team team;

}
