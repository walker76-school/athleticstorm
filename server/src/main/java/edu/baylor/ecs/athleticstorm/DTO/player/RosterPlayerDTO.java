/******************************************************************************
 *
 * RosterPlayerDTO.java
 *
 * author: Ian laird
 *
 * Created 3/27/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RosterPlayerDTO {

    private Long id;
    private String first_name;
    private String last_name;
    private int height;
    private int weight;
    private int jersey;
    private int year;
    private String position;
    private String city;
    //private String state;
    //private String country;

    public RosterPlayerDTO(RosterPlayer rosterPlayer){
        this.id = rosterPlayer.getId();
        this.first_name = rosterPlayer.getPlayer().getFirstName();
        this.last_name = rosterPlayer.getPlayer().getLastName();
        this.height = rosterPlayer.getPlayer().getHeight();
        this.weight = rosterPlayer.getPlayer().getWeight();
        this.jersey = rosterPlayer.getPlayer().getJersey();
        this.year = rosterPlayer.getYear();
        this.position = rosterPlayer.getPlayer().getPosition();
        this.city = rosterPlayer.getPlayer().getHometown();
    }

}
