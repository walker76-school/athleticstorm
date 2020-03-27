/******************************************************************************
 *
 * CoachService.java
 *
 * author: Ian laird
 *
 * Created 3/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.CoachRepository;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public List<Coach> getCoachesByTeamId(Long teamId) {
        Team team = teamRepository.getOne(teamId);
        return team.getCoaches();
    }

    public Coach getCoachByName(String name) {
        return coachRepository.findCoachByNameEquals(name).get();
    }

    //TODO this
    /*
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
    */
}
