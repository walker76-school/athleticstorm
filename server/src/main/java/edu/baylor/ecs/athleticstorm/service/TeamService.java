package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private ColorRepository colorRepository;

    public String getTeamColor(String team){
        return colorRepository.findByTeamName(team).getColor();
    }
}
