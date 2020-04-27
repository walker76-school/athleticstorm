/*
 * Filename: Rating.java
 * Author: Ian Laird
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Rating implements Comparable<Rating>{

    @EmbeddedId
    @EqualsAndHashCode.Include
    private RatingKey key;

    @Column(name = "RATING")
    @EqualsAndHashCode.Exclude
    private Double rating;

    /**
     * {@inheritDoc}
     * @param o the other rating
     * @return the comparison
     */
    @Override
    public int compareTo(Rating o) {
        return key.compareTo(o.getKey());
    }
}
