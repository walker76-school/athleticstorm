package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coordinator.CoordinatorDTO;
import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoordinatorService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    public CoordinatorDTO getCoordinatorByName(String name){
        Optional<Coordinator> coordinatorOpt = coordinatorRepository.findCoordinatorByNameEquals(name);
        return coordinatorOpt.map(CoordinatorDTO::new).orElse(null);
    }

    public List<CoordinatorDTO> getCoordinatorsByTeamId(long teamId){
        return coordinatorRepository.findCoordinatorsByTeam_Id(teamId).stream().map(CoordinatorDTO::new).collect(Collectors.toList());
    }
}
