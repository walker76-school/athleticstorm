/******************************************************************************
 *
 * AdvancedPlayerRepository.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.AdvancedPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvancedPlayerRepository extends JpaRepository<AdvancedPlayer, Long > {
}
