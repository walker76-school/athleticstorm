/*
 * Filename: TeamController.java
 * Author: Ian Laird
 * Date Last Modified: 4/19/2020
 */

package edu.baylor.ecs.athleticstorm.controller.data;

import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Team data
 *
 * @author Ian Laird
 */
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * Returns all teams
     * @return all teams
     */
    @GetMapping("/all")
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams();
    }

    /**
     * Returns all FBS teams
     * @return all FBS teams
     */
    @GetMapping("/fbs")
    public List<TeamDTO> getAllFBSTeams(){
        return teamService.getAllFBSTeams().stream().sorted(Comparator.comparing(TeamDTO::getSchool)).collect(Collectors.toList());
    }

    /**
     * Returns team data for a given team
     * @param teamId a team id
     * @return team data for a given team
     */
    @GetMapping("/{teamId}")
    public TeamDTO getTeamByTeamId(@PathVariable("teamId") Long teamId){
        return teamService.getTeamById(teamId);
    }

    /**
     * Returns team data for a given team
     * @param teamName a team name
     * @return team data for a given team
     */
    @GetMapping("/byName/{teamName}")
    public TeamDTO getTeamByTeamName(@PathVariable("teamName") String teamName){
        return teamService.getTeamByName(teamName);
    }

    /**
     * Returns highlight videos for a team given their name
     * @param teamName a team name
     * @return highlight videos for a team given their name
     */
    @GetMapping("/videos/byName/{teamName}")
    public List<String> getVideosByTeamName(@PathVariable("teamName") String teamName){
        return teamService.getVideosByName(teamName);
    }

}
