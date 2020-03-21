package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Player {
    private int id;
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
}
