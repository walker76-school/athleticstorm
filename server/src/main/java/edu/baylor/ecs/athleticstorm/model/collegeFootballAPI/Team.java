package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Team {
    private int id;
    private String school;
    private String mascot;
    private String abbreviation;
    private String alt_name_1;
    private String alt_name_2;
    private String alt_name_3;
    private String conference;
    private String division;
    private String color;
    private String alt_color;
    private List<String> logos;
}
