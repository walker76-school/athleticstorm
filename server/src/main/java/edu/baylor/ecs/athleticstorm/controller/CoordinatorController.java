/*
 * Filename: CoordinatorController.java
 * Author: Andrew Walker
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.coordinator.CoordinatorDTO;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for Coordinator data
 *
 * @author Andrew Walker
 */
@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    /**
     * Returns a list of coordinators for a given team
     * @param teamId a team id
     * @return a list of coordinators for a given team
     */
    @GetMapping("/byTeamId/{teamId}")
    public List<CoordinatorDTO> getPlayerById(@PathVariable("teamId") int teamId) {
        return coordinatorService.getCoordinatorsByTeamId(teamId);
    }
}
