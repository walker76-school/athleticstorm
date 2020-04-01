/******************************************************************************
 *
 * RatingKey.java
 *
 * author: Ian laird
 *
 * Created 2/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.model.rating;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class RatingKey implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "YEAR")
    private Integer year;
}
