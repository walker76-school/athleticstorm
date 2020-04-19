/******************************************************************************
 *
 * Rating.java
 *
 * author: Ian laird
 *
 * Created 2/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Override
    public int compareTo(Rating o) {
        if(key.getYear().equals(o.getKey().getYear())){
            return Integer.compare(key.getWeek(), o.getKey().getWeek());
        }
        return Integer.compare(key.getYear(), o.getKey().getYear());
    }
}
