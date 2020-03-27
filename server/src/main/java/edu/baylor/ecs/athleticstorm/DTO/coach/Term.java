package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Term {

    Team team;
    private int wins;
    private int losses;
    private int ties;
    private int start_year;
    private int end_year;
    private List<Season> seasons;

    public void setSeasons(Set<Season> seasonSet){
        seasons = new ArrayList<>(seasonSet);
        seasons.sort(Comparator.comparingInt(Season::getYear).reversed());
        wins = seasons.stream().map(Season::getWins).mapToInt(Integer::intValue).sum();
        losses = seasons.stream().map(Season::getLosses).mapToInt(Integer::intValue).sum();
        ties = seasons.stream().map(Season::getTies).mapToInt(Integer::intValue).sum();
        start_year = seasons.stream().map(Season::getYear).mapToInt(Integer::intValue).min().orElse(-1);
        end_year = seasons.stream().map(Season::getYear).mapToInt(Integer::intValue).max().orElse(-1);
    }
}
