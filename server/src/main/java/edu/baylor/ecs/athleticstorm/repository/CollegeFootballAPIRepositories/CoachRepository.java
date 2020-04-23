/*
 * Filename: CoachRepository.java
 * Author: Ian Laird
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Coach data
 *
 * @author Ian Laird
 */
public interface CoachRepository extends JpaRepository<Coach, Long> {

    /**
     * Get a coach by name
     * @param name a coach name
     * @return a coach by name
     */
    Optional<Coach> findCoachByNameEquals(String name);

    /**
     * Get a coach by team
     * @param team a team name
     * @return a coach by team
     */
    @Query("SELECT DISTINCT c FROM Coach c join c.seasons s WHERE s.school = :team")
    List<Coach> findHistoricalCoachesByTeam(String team);
}
