/*
 * Filename: RatingRepository.java
 * Author: Ian Laird
 * Date Last Modified: 4/19/2020
 */

package edu.baylor.ecs.athleticstorm.repository;

import edu.baylor.ecs.athleticstorm.model.rating.Rating;
import edu.baylor.ecs.athleticstorm.model.rating.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Rating data
 *
 * @author Ian Laird
 */
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    /**
     * Find Rating by name
     * @param name name
     * @return a Rating by name
     */
     List<Rating> findAllByKey_Name(String name);
}
