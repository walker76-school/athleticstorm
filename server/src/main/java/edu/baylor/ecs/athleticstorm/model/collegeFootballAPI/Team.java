package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "MASCOT")
    private String mascot;

    @Column(name = "ABBREVIATION")
    private String abbreviation;

    @Column(name = "ALT_NAME_1")
    private String alt_name_1;

    @Column(name = "ALT_NAME_2")
    private String alt_name_2;

    @Column(name = "ALT_NAME_3")
    private String alt_name_3;

    @Column(name = "CONFERENCE")
    private String conference;

    @Column(name = "DIVISION")
    private String division;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "ALT_COLOR")
    private String alt_color;

    @ElementCollection
    private List<String> logos;
}
