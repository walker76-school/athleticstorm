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

import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Coordinator.CoordinatorID> {
    public Optional<Coordinator> findCoordinatorByNameEquals(String name);

    List<Coordinator> findCoordinatorsByTeam_Id(long teamId);
}
