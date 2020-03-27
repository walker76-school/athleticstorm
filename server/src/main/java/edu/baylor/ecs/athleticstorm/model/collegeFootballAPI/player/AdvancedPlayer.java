package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Column(name = "ADVANCED_PLAYER")
public class AdvancedPlayer {

    @OneToOne
    @PrimaryKeyJoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    private Player player;

    @Id
    @Column(name = "ID")
    private Long id;

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
    @JoinColumn(name = "USAGE_ID", referencedColumnName = "ID")
    private Usage usage;
}
