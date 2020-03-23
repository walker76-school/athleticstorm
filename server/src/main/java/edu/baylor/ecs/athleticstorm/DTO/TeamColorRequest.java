package edu.baylor.ecs.athleticstorm.DTO;

import lombok.Data;

@Data
public class TeamColorRequest {

    private String school;
    private String color;
    private String alt_color;
    private String [] logos;
}
