/*
 * Filename: CoordinatorRepository.java
 * Author: Andrew Walker
 * Date Last Modified: 4/18/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Coordinator data
 *
 * @author Andrew Walker
 */
public interface CoordinatorRepository extends JpaRepository<Coordinator, Coordinator.CoordinatorID> {

    /**
     * Get a coordinator by name
     * @param name a coordinator name
     * @return a coordinator by name
     */
    Optional<Coordinator> findCoordinatorByNameEquals(String name);

    /**
     * Get a coordinator by team id
     * @param teamId a team id
     * @return a coordinator by team id
     */
    List<Coordinator> findCoordinatorsByTeam_Id(long teamId);
}
