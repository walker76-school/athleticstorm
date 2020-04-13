/******************************************************************************
 *
 * PlayerRepository.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.repository.CollegeFootballAPIRepositories;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p join p.usage where p.firstName = :firstName and p.lastName = :lastName")
    public Optional<Player> findPlayerByFirstAndLastName(String firstName, String lastName);

    public Optional<Player> findPlayerByNameEquals(String name);
}
