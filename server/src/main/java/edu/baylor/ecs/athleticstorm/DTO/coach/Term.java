package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
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

    TeamDTO team;
    private int wins;
    private int losses;
    private int ties;
    private int start_year;
    private int end_year;
    private List<SeasonDTO> seasons;

    public void setSeasons(Set<SeasonDTO> seasonSet){
        seasons = new ArrayList<>(seasonSet);
        seasons.sort(Comparator.comparingLong(SeasonDTO::getYear).reversed());
        wins = seasons.stream().map(SeasonDTO::getWins).mapToInt(Integer::intValue).sum();
        losses = seasons.stream().map(SeasonDTO::getLosses).mapToInt(Integer::intValue).sum();
        ties = seasons.stream().map(SeasonDTO::getTies).mapToInt(Integer::intValue).sum();
        start_year = seasons.stream().map(SeasonDTO::getYear).mapToInt(Integer::intValue).min().orElse(-1);
        end_year = seasons.stream().map(SeasonDTO::getYear).mapToInt(Integer::intValue).max().orElse(-1);
    }
}
