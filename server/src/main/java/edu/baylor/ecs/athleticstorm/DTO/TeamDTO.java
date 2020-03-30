package edu.baylor.ecs.athleticstorm.DTO; /******************************************************************************
 *
 * TeamDTO.java
 *
 * author: Ian laird
 *
 * Created 3/29/20
 *
 * © 2020
 *
 ******************************************************************************/

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TeamDTO {
    private Long id;
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

    public TeamDTO(Team team){
        this.id = team.getId();
        this.school = team.getSchool();
        this.mascot = team.getMascot();
        this.abbreviation = team.getAbbreviation();
        this.alt_name_1 = team.getAlt_name_1();
        this.alt_name_2 = team.getAlt_name_2();
        this.alt_name_3 = team.getAlt_name_3();
        this.conference = team.getConference();
        this.division = team.getDivision();
        this.color = team.getColor();
        this.alt_color = team.getAlt_color();
        this.logos = new LinkedList<>(team.getLogos());
    }
}
