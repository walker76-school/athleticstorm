package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RosterPlayer {

    private int id;
    private String first_name;
    private String last_name;
    private int height;
    private int weight;
    private int jersey;
    private int year;
    private String position;
    private String city;
    private String state;
    private String country;

}
