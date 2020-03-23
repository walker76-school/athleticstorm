/******************************************************************************
 *
 * TeamResponse.java
 *
 * author: Ian laird
 *
 * Created 2/27/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.DTO;

import edu.baylor.ecs.athleticstorm.model.team.Color;
import lombok.Data;

@Data
public class TeamResponse {

    private String color;
    private String alt_color;
    private String logo;

    public TeamResponse(Color c){
        this.color = c.getColor();
        this.alt_color = c.getAltColor();
        this.logo = c.getLogo();
    }
}
