/******************************************************************************
 *
 * PlayerDTO.java
 *
 * author: Ian laird
 *
 * Created 3/30/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String team;
    private String name;
    private String firstName;
    private String lastName;
    private int height;
    private int weight;
    private int jersey;
    private String position;
    private String hometown;
    private String teamColor;

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

