package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.DTO.TeamResponse;
import edu.baylor.ecs.athleticstorm.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private ColorRepository colorRepository;

    public TeamResponse getTeamColor(String team){
        return new TeamResponse(colorRepository.findByTeamName(team));
    }
}
