package edu.baylor.ecs.athleticstorm.model.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM_COLOR")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Color {

    @Id
    @Column(name = "TEAM_NAME")
    private String teamName;

    @Column(name = "COLOR")
    private String color;
}
