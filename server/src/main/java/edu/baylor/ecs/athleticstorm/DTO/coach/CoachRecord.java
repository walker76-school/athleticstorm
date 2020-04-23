/*
 * Filename: CoachRecord.java
 * Author: Andrew Walker
 * Date Last Modified: 4/19/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.coach;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Record for a Coach
 *
 * @author Andrew Walker
 */
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
    private double rating;

    /**
     * Creates a CoachRecord from a given CoachDTO
     * @param coach a coach
     */
    public CoachRecord(CoachDTO coach){
        this.coachId = -1;
        this.name= coach.getFirst_name() + " " + coach.getLast_name();
        this.wins = 0;
        this.losses = 0;
        terms = new ArrayList<>();
    }

    /**
     * Adds a term to a coach's record
     * @param term the term to add
     */
    public void addTerm(Term term){
        this.terms.add(term);
        this.terms.sort(Comparator.comparingInt(Term::getStart_year).reversed());
        this.wins += term.getWins();
        this.losses += term.getLosses();
        this.ties += term.getTies();
    }
}
