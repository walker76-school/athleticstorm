/*
 * Filename: Player Repository
 * Author: Ian Laird
 * Date Last Modified: 4/18/2020
 */

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository for Player data
 *
 * @author Ian Laird
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Get player by name
     * @param firstName first name
     * @param lastName last name
     * @param team team name
     * @return player by name
     */
    @Query("SELECT p FROM Player p left join p.usage where p.firstName = :firstName and p.lastName = :lastName and p.team = :team")
    Optional<Player> findPlayerByFirstAndLastNameAndTeam(String firstName, String lastName, String team);
}
