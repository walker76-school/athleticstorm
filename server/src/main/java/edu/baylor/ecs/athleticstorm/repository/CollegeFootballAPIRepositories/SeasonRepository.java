/*
 * Filename: SeasonRepository.java
 * Author: Ian Laird
 * Date Last Modified: 3/24/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Season data
 *
 * @author Ian Laird
 */
public interface SeasonRepository extends JpaRepository<Season, Season.SeasonID> {

}
