package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CollegeFootballAPIService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Team> getAllTeams() {
        ResponseEntity<Team[]> response =  restTemplate.getForEntity(
                "https://api.collegefootballdata.com/teams",
                Team[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<Team> getAllFBSTeams() {
        ResponseEntity<Team[]> response =  restTemplate.getForEntity(
                        "https://api.collegefootballdata.com/teams/fbs",
                        Team[].class);
       return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
