package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.ppa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPA {

    private int gameId;
    private int season;
    private int week;
    private String conference;
    private String team;
    private String opponent;
    private PPAStats offense;
    private PPAStats defense;
}
