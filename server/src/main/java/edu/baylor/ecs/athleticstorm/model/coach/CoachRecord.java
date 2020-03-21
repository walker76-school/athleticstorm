package edu.baylor.ecs.athleticstorm.model.coach;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CoachRecord {

    private int coachId;
    private String first_name;
    private String last_name;
    private int wins;
    private int losses;
    private int ties;
    private List<Term> terms;

    public CoachRecord(Coach coach){
        this.coachId = -1;
        this.first_name = coach.getFirst_name();
        this.last_name = coach.getLast_name();
        this.wins = 0;
        this.losses = 0;
        terms = new ArrayList<>();
    }

    public void addTerm(Term term){
        this.terms.add(term);
        this.wins += term.getWins();
        this.losses += term.getLosses();
        this.ties += term.getTies();
    }
}
