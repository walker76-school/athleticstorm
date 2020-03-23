package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Season {
    private String school;
    private int year;
    private int games;
    private int wins;
    private int losses;
    private int ties;
    private int preseason_rank;
    private int postseason_rank;
}
