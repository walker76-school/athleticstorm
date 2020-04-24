/*
 * Filename: RosterPlayerDTO
 * Author: Ian Laird
 * Date Last Modified: 4/14/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a RosterPlayer
 *
 * @author Ian Laird
 */
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

    /**
     * Constructs a RosterPlayerDTO from a RosterPlayer
     * @param rosterPlayer a RosterPlayer
     */
    public RosterPlayerDTO(RosterPlayer rosterPlayer){
        this.id = rosterPlayer.getPlayer().getId();
        this.first_name = rosterPlayer.getPlayer().getFirstName();
        this.last_name = rosterPlayer.getPlayer().getLastName();
        this.height = rosterPlayer.getPlayer().getHeight();
        this.weight = rosterPlayer.getPlayer().getWeight();
        this.jersey = rosterPlayer.getPlayer().getJersey();
        this.year = rosterPlayer.getSeason().getYear();
        this.position = rosterPlayer.getPlayer().getPosition();
        this.city = rosterPlayer.getPlayer().getHometown();
    }

}
