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

import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<TeamDTO> getAllTeams() {
        return convertToDTO(teamRepository.findAll());
    }

    public TeamDTO getTeamByName(String name){
        Optional<Team> response = teamRepository.findTeamBySchool(name);
        return response.isPresent() ? new TeamDTO(response.get()) : null;
    }

    public List<TeamDTO> getAllFBSTeams() {
        return convertToDTO(teamRepository.findAllFBS());
    }

    public TeamDTO getTeamById(Long id){
        return new TeamDTO(teamRepository.getOne(id));
    }

    public List<TeamDTO> convertToDTO(Collection<Team> teams){
        return teams.stream().map(TeamDTO::new).collect(Collectors.toList());
    }

}
