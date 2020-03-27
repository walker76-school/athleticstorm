package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CoachRecord {

    private int coachId;
    private String name;
    private int wins;
    private int losses;
    private int ties;
    private List<Term> terms;

    public CoachRecord(Coach coach){
        this.coachId = -1;
        this.name= coach.getName();
        this.wins = 0;
        this.losses = 0;
        terms = new ArrayList<>();
    }

    public void addTerm(Term term){
        this.terms.add(term);
        this.terms.sort(Comparator.comparingInt(Term::getStart_year).reversed());
        this.wins += term.getWins();
        this.losses += term.getLosses();
        this.ties += term.getTies();
    }
}
