package edu.baylor.ecs.athleticstorm.controller.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    public List<TeamDTO> getAllTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/fbs")
    public List<TeamDTO> getAllFBSTeams(){
        return teamService.getAllFBSTeams().stream().sorted(Comparator.comparing(TeamDTO::getSchool)).collect(Collectors.toList());
    }

    @GetMapping("/{teamId}")
    public TeamDTO getTeamByTeamId(@PathVariable("teamId") Long teamId){
        return teamService.getTeamById(teamId);
    }


    @GetMapping("/byName/{teamName}")
    public TeamDTO getTeamByTeamName(@PathVariable("teamName") String teamName){
        return teamService.getTeamByName(teamName);
    }

    @GetMapping("/videos/byName/{teamName}")
    public List<String> getVideosByTeamName(@PathVariable("teamName") String teamName){
        return teamService.getVideosByName(teamName);
    }

}
