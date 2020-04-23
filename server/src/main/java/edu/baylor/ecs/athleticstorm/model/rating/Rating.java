/*
 * Filename: Rating.java
 * Author: Ian Laird
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Rating for a person
 *
 * @author Andrew Walker
 */

@Entity
@Table(name = "RATING")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Comparable<Rating>{

    @EmbeddedId
    private RatingKey key;

    @Column(name = "RATING")
    private Double rating;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    /**
     * {@inheritDoc}
     * @param o the other rating
     * @return the comparison
     */
    @Override
    public int compareTo(Rating o) {
        if(key.getYear().equals(o.getKey().getYear())){
            return Integer.compare(key.getWeek(), o.getKey().getWeek());
        }
        return Integer.compare(key.getYear(), o.getKey().getYear());
    }
}
