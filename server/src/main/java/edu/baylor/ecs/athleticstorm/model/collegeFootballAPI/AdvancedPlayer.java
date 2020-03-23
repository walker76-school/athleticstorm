package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdvancedPlayer {
    private int season;
    private int id;
    private String name;
    private String position;
    private String team;
    private String conference;
    private Usage usage;
}
