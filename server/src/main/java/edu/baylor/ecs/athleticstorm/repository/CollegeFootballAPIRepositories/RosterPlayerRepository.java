/*
 * Filename: RosterPlayerRepository.java
 * Author: Ian Laird
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.RosterPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for Player Roster data
 *
 * @author Ian Laird
 */
public interface RosterPlayerRepository extends JpaRepository<RosterPlayer, RosterPlayer.RosterPlayerId> {

    /**
     * Get a player roster by year and team
     * @param year year
     * @param teamName team name
     * @return a player roster by year and team
     */
    @Query("SELECT rp from RosterPlayer rp join fetch rp.season where rp.season.year = :year and rp.season.school = :teamName")
    List<RosterPlayer> findAllByYearAndTeam(Integer year, String teamName);
}
