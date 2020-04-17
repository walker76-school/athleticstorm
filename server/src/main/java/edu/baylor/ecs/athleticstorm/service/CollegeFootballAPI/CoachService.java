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

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.coach.CoachRecord;
import edu.baylor.ecs.athleticstorm.DTO.coach.Term;
import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import edu.baylor.ecs.athleticstorm.DTO.team.TeamDTO;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Coach;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.CoachRepository;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<CoachDTO> getAllCoaches() {
        return convertToDTO(coachRepository.findAll());
    }

    public List<CoachDTO> getCoachesByTeamId(Long teamId) {
        Team team = teamRepository.getOne(teamId);
        return convertToDTO(team.getCoaches());
    }

    public List<CoachDTO> getHistoricalCoachesByTeamId(Long teamId) {
        String teamName = teamRepository.getOne(teamId).getSchool();
        return convertToDTO(coachRepository.findHistoricalCoachesByTeam(teamName));
    }

    public CoachDTO getCoachByName(String name) {
        return new CoachDTO(coachRepository.findCoachByNameEquals(name).get());
    }

    private List<CoachDTO> convertToDTO(Collection<Coach> coaches){
        return coaches
                .stream()
                .map(CoachDTO::new)
                .collect(Collectors.toList());
    }

    public CoachRecord buildRecordFromCoach(CoachDTO coach){
        CoachRecord record = new CoachRecord(coach);

        List<SeasonDTO> allSeasons = coach.getSeasons();
        Map<String, Set<SeasonDTO>> schoolToSeasonsMap = new HashMap<>();
        for(SeasonDTO season : allSeasons){
            Set<SeasonDTO> seasons = schoolToSeasonsMap.getOrDefault(season.getSchool(), new HashSet<>());
            seasons.add(season);
            schoolToSeasonsMap.put(season.getSchool(), seasons);
        }

        for(Map.Entry<String, Set<SeasonDTO>> entry : schoolToSeasonsMap.entrySet()){
            Term schoolTerm = new Term();
            Optional<Team> teamOpt = teamRepository.findTeamBySchool(entry.getKey());
            if(teamOpt.isPresent()){
                Team team = teamOpt.get();
                schoolTerm.setTeam(new TeamDTO(team));
                schoolTerm.setSeasons(entry.getValue());
                record.addTerm(schoolTerm);
            }
        }

        return record;
    }
}
