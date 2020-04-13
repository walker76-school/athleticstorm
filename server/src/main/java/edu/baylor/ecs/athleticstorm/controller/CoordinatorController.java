package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.exception.ResourceNotFoundException;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import edu.baylor.ecs.athleticstorm.service.CollegeFootballAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    @Autowired
    private CollegeFootballAPIService collegeFootballAPIService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/byTeamId/{teamId}")
    public List<Coordinator> getPlayerById(@PathVariable("teamId") int teamId) throws IOException {
        Team team = collegeFootballAPIService.getAllTeams().stream()
                .filter(x -> x.getId() == teamId)
                .findFirst()
                .orElse(null);

        if(team == null){
            throw new ResourceNotFoundException("Team", teamId + "", null);
        }

        String schoolName = team.getSchool();
        List<Coordinator> coordinatorsDC = loadCoordinators(schoolName, "DC");
        List<Coordinator> coordinatorsOC = loadCoordinators(schoolName, "OC");

        return Stream.concat(
                coordinatorsDC.stream(),
                coordinatorsOC.stream()).collect(Collectors.toList());
    }

    private List<Coordinator> loadCoordinators(String schoolName, String key) throws IOException {
        List<Coordinator> coordinators = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:data/" + key + ".csv");
        InputStream inputStream = resource.getInputStream();
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(",");
            if(tokens[0].equalsIgnoreCase(schoolName)) {

                Map<String, List<Integer>> nameToYears = new HashMap<>();
                getCoordinatorsByYear(nameToYears, tokens, 1, 2017);
                getCoordinatorsByYear(nameToYears, tokens, 2, 2018);
                getCoordinatorsByYear(nameToYears, tokens, 3, 2019);

                for(Map.Entry<String, List<Integer>> entry : nameToYears.entrySet()){
                    List<Integer> years = entry.getValue();
                    Integer startYear = years.stream().min(Integer::compareTo).get();
                    Integer endYear = years.stream().max(Integer::compareTo).get();
                    coordinators.add(new Coordinator(entry.getKey(), key, startYear, endYear));
                }
            }
        }
        sc.close();
        return coordinators;
    }

    private void getCoordinatorsByYear(Map<String, List<Integer>> nameToYears, String[] tokens, int index, int year){
        String token = tokens[index];
        String[] coordinators = token.split("/");
        for(String name : coordinators){
            List<Integer> years = nameToYears.getOrDefault(name, new ArrayList<>());
            years.add(year);
            nameToYears.put(name, years);
        }
    }
}
