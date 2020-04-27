/*
 * Filename: RatingKey.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * The id of a rating
 *
 * @author Andrew Walker
 */

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RatingKey implements Serializable, Comparable<RatingKey> {

    @EqualsAndHashCode.Include
    @Column(name = "NAME")
    private String name;

    @EqualsAndHashCode.Include
    @Column(name = "YEAR")
    private Integer year;

    @EqualsAndHashCode.Include
    @Column(name = "WEEK")
    private Integer week;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private PersonType type;

    @Override
    public int compareTo(RatingKey ratingKey) {
        int one = this.name.compareTo(ratingKey.getName());
        if(one != 0){
            return one;
        }
        int two = this.year.compareTo(ratingKey.getYear());
        if(two != 0){
            return two;
        }
        int three = this.type.compareTo(ratingKey.getType());
        if(three != 0){
            return three;
        }
        return this.week.compareTo(ratingKey.getWeek());
    }
}
