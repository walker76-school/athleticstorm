/******************************************************************************
 *
 * PlayerService.java
 *
 * author: Ian laird
 *
 * Created 3/25/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.service.CollegeFootballAPi;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.RosterPlayer;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.Team;
import edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PlayerService {

    @Autowired
    private TeamRepository teamRepository;

    public List<RosterPlayer> getTeamRoster(Long teamId) {
        return roster(teamId, new Long(2020));
    }

    public List<RosterPlayer> roster(Long teamId, Long year){
        Team team = teamRepository.getOne(teamId);
        return team.getRosterPlayers();
    }

    public List<RosterPlayer> getSeasonRoster(Long teamId, Long year) {
        return roster(teamId, year);
    }

}
