/*
 * Filename: CoordinatorService.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coordinator.CoordinatorDTO;
import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for interacting with coordinators
 *
 * @author Andrew walker
 */
@Service
public class CoordinatorService {

    // the jpa repository for coordinator
    @Autowired
    private CoordinatorRepository coordinatorRepository;

    /**
     * Finds a coordinator by name
     * @param name the name of the coordinator
     * @return the coordinator
     */
    public CoordinatorDTO getCoordinatorByName(String name){
        Optional<Coordinator> coordinatorOpt = coordinatorRepository.findCoordinatorByNameEquals(name);
        return coordinatorOpt.map(CoordinatorDTO::new).orElse(null);
    }

    /**
     * Gets all coordinators for a team
     * @param teamId the team id
     * @return the coordinators
     */
    public List<CoordinatorDTO> getCoordinatorsByTeamId(long teamId){
        return coordinatorRepository.findCoordinatorsByTeam_Id(teamId).stream().map(CoordinatorDTO::new).collect(Collectors.toList());
    }
}
