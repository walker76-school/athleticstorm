/*
 * Filename: TeamRepository.java
 * Author: Ian Laird
 * Date Last Modified: 4/12/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Team data
 *
 * @author Ian Laird
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Get a  team by school name
     * @param school school name
     * @return team by school name
     */
    Optional<Team> findTeamBySchool(String school);

    /**
     * Get all FBS teams
     * @return all FBS teams
     */
    @Query("SELECT t from Team t WHERE t.is_fbs = true")
    List<Team> findAllFBS();
}
