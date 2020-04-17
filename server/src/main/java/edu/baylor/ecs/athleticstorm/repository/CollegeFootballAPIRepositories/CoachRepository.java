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
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    public Optional<Coach> findCoachByNameEquals(String name);

    @Query("SELECT DISTINCT c FROM Coach c join c.seasons s WHERE s.school = :team")
    public List<Coach> findHistoricalCoachesByTeam(String team);
}
