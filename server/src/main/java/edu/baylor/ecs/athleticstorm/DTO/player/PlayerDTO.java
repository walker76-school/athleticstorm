/*
 * Filename: PlayerDTO.java
 * Author: Ian Laird
 * Date Last Modified: 4/1/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Player
 *
 * @author Ian Laird
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String team;
    private String name;
    private String firstName;
    private String lastName;
    private int weight;
    private int height;
    private int jersey;
    private String position;
    private String hometown;
    private String teamColor;

    /**
     * Constructs a PlayerDTO from a player
     * @param p a player
     */
    public PlayerDTO(Player p){
        this.id = p.getId();
        this.team = p.getTeam();
        this.name = p.getName();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.height = p.getHeight();
        this.weight = p.getWeight();
        this.jersey = p.getJersey();
        this.position = p.getPosition();
        this.hometown = p.getHometown();
        this.teamColor = p.getTeamColor();
    }
}

