package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.DTO.coordinator.CoordinatorDTO;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @GetMapping("/byTeamId/{teamId}")
    public List<CoordinatorDTO> getPlayerById(@PathVariable("teamId") int teamId) {
        return coordinatorService.getCoordinatorsByTeamId(teamId);
    }
}
