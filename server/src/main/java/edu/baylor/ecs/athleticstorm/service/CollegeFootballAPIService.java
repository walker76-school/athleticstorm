package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.model.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.model.coach.Term;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.RosterPlayer;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Season;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    public Team getTeamByName(String name){
        List<Team> allTeams = getAllTeams();
        return allTeams.stream().filter(x -> x.getSchool().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Team> getAllFBSTeams() {
        ResponseEntity<Team[]> response =  restTemplate.getForEntity(
                        "https://api.collegefootballdata.com/teams/fbs",
                        Team[].class);
       return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<Coach> getAllCoaches() {
        ResponseEntity<Coach[]> response =  restTemplate.getForEntity(
                "https://api.collegefootballdata.com/coaches",
                Coach[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<Coach> getCoachesByTeamId(int teamId) {
        List<Team> allTeams = getAllTeams();
        Optional<Team> teamOpt = allTeams.stream().filter(x -> x.getId() == teamId).findFirst();
        if(teamOpt.isPresent()){
            Team team = teamOpt.get();
            ResponseEntity<Coach[]> response =  restTemplate.getForEntity(
                    "https://api.collegefootballdata.com/coaches?team=" + team.getSchool() + "&minYear=2000",
                    Coach[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        }
        return new ArrayList<>();
    }

    public List<RosterPlayer> getSeasonRoster(int teamId, int year) {
        List<Team> allTeams = getAllTeams();
        Optional<Team> teamOpt = allTeams.stream().filter(x -> x.getId() == teamId).findFirst();
        if(teamOpt.isPresent()){
            Team team = teamOpt.get();
            ResponseEntity<RosterPlayer[]> response =  restTemplate.getForEntity(
                    "https://api.collegefootballdata.com/roster?team=" + team.getSchool() + "&year=" + year,
                    RosterPlayer[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        }
        return new ArrayList<>();
    }

    public CoachRecord buildRecordFromCoach(Coach coach){
        CoachRecord record = new CoachRecord(coach);

        List<Season> allSeasons = coach.getSeasons();
        Map<String, Set<Season>> schoolToSeasonsMap = new HashMap<>();
        for(Season season : allSeasons){
            Set<Season> seasons = schoolToSeasonsMap.getOrDefault(season.getSchool(), new HashSet<>());
            seasons.add(season);
            schoolToSeasonsMap.put(season.getSchool(), seasons);
        }

        for(Map.Entry<String, Set<Season>> entry : schoolToSeasonsMap.entrySet()){
            Term schoolTerm = new Term();
            Team team = getTeamByName(entry.getKey());
            schoolTerm.setTeam(team);
            schoolTerm.setSeasons(entry.getValue());
            record.addTerm(schoolTerm);
        }

        return record;
    }

    public Coach getCoachByName(String firstName, String lastName) {
        ResponseEntity<Coach[]> response =  restTemplate.getForEntity(
                "https://api.collegefootballdata.com/coaches?firstName=" + firstName.toLowerCase() + "&lastName=" + lastName.toLowerCase(),
                Coach[].class);
        return Arrays.stream(Objects.requireNonNull(response.getBody())).findFirst().orElse(null);
    }
}
