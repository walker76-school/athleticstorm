/******************************************************************************
 *
 * TeamService.java
 *
 * author: Ian laird
 *
 * Created 3/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import edu.baylor.ecs.athleticstorm.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamByName(String name){
        Optional<Team> response = teamRepository.findTeamBySchool(name);
        return response.isPresent() ? response.get() : null;
    }

    public List<Team> getAllFBSTeams() {
        return teamRepository.findAllFBS();
    }

    public Team getTeamById(Long id){
        return teamRepository.getOne(id);
    }

    @Autowired
    private ColorRepository colorRepository;

    public TeamResponse getTeamColor(String team){
        return new TeamResponse(colorRepository.findByTeamName(team));
    }

}
