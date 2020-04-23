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
import java.io.Serializable;

/**
 * The id of a rating
 *
 * @author Andrew Walker
 */

@Embeddable
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingKey implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "WEEK")
    private Integer week;
}
