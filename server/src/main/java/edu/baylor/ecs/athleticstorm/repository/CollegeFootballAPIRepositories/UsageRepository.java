/*
 * Filename: UsageRepository.java
 * Author: Ian Laird
 * Date Last Modified: 4/6/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Usage data
 *
 * @author Ian Laird
 */
public interface UsageRepository extends JpaRepository<Usage, Long> {

}
