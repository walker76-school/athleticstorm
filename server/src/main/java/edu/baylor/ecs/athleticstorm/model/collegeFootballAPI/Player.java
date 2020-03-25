package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Column(name = "PLAYER")
public class Player {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEAM")
    private String team;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "HEIGHT")
    private int height;

    @Column(name = "WEIGHT")
    private int weight;

    @Column(name = "JERSEY")
    private int jersey;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "HOMETOWN")
    private String hometown;

    @Column(name = "TEAM_COLOR")
    private String teamColor;
}
