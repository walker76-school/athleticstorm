package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "ADVANCED_PLAYER")
public class AdvancedPlayer {

    @Id
    @Column(name = "PLAYER_ID")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    private Player player;

    @Column(name = "SEASON")
    private int season;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "TEAM")
    private String team;

    @Column(name = "CONFERENCE")
    private String conference;

    @OneToOne
    @JoinColumn(name = "USAGE_ID", referencedColumnName = "PLAYER_ID")
    private Usage usage;
}
