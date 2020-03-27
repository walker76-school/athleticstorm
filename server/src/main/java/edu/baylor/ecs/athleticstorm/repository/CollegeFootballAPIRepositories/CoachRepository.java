/******************************************************************************
 *
 * CoachRepository.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    public Optional<Coach> findCoachByFirst_nameEqualsAndLast_nameEquals(String firstName, String lastName);
}
