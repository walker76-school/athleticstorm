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

/**
 * CoachService
 *
 * Service for interacting with coaches
 *
 * @author Ian Laird
 */
@Service
public class CoachService {

    // coach jpa repository
    @Autowired
    private CoachRepository coachRepository;

    // team jpa repository
    @Autowired
    private TeamRepository teamRepository;

    /**
     * gets all coaches in the DB
     *
     * @return all coaches
     */
    public List<CoachDTO> getAllCoaches() {
        return convertToDTO(coachRepository.findAll());
    }

    /**
     * get all current coaches for a team
     *
     * @param teamId the id of the team
     * @return all current coaches for a team
     */
    public List<CoachDTO> getCoachesByTeamId(Long teamId) {
        Team team = teamRepository.getOne(teamId);
        return convertToDTO(team.getCoaches());
    }

    /**
     * get every coach that has ever coached a team
     *
     * @param teamId the id of the team
     * @return the id of the team
     */
    public List<CoachDTO> getHistoricalCoachesByTeamId(Long teamId) {
        String teamName = teamRepository.getOne(teamId).getSchool();
        return convertToDTO(coachRepository.findHistoricalCoachesByTeam(teamName));
    }

    /**
     * get coach of a specific name
     *
     * @param name the name of the coach
     * @return coach
     */
    public CoachDTO getCoachByName(String name) {
        return new CoachDTO(coachRepository.findCoachByNameEquals(name).get());
    }

    /**
     * converts a collection of {@link Coach} to {@link CoachDTO}
     * @param coaches the coach collection
     * @return the coach dto collection
     */
    private List<CoachDTO> convertToDTO(Collection<Coach> coaches){
        return coaches
                .stream()
                .map(CoachDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * builds a complete coach record
     *
     * @param coach the coach dto
     * @return the coach record
     */
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
