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

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ResourceLoader resourceLoader;

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

    public List<String> getVideosByName(String teamName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/Videos.csv");
            InputStream inputStream = resource.getInputStream();
            Scanner sc = new Scanner(inputStream);
            List<String> videos = new ArrayList<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split(",");
                if(tokens[0].equalsIgnoreCase(teamName)){
                    videos.add(tokens[1].substring(tokens[1].indexOf('=') + 1));
                    videos.add(tokens[2].substring(tokens[2].indexOf('=') + 1));
                    videos.add(tokens[3].substring(tokens[3].indexOf('=') + 1));
                }
            }
            sc.close();
            return videos;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
