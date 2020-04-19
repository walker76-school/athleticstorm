/******************************************************************************
 *
 * RosterPlayerRepository.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RosterPlayerRepository extends JpaRepository<RosterPlayer, RosterPlayer.RosterPlayerId> {

    @Query("SELECT rp from RosterPlayer rp join rp.team t where rp.year = :year and t.id = :teamId")
    public List<RosterPlayer> findAllByYearAndTeam(Integer year, Long teamId);
}
