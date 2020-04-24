/*
 * Filename: CompositePlayer.java
 * Author: Andrew Walker
 * Date Last Modified: 4/14/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

/**
 * DTO for a player's full stats
 *
 * @author Andrew Walker
 */
@AllArgsConstructor
@Data
public class CompositePlayer {

    private PlayerDTO player;
    private AdvancedPlayerDTO advancedPlayer;
    private boolean hasAdvancedPlayer;

    /**
     * Creates a CompositePlayer from a Player and a specified year for Usage
     * @param p a Player
     * @param year a specified year for Usage
     */
    public CompositePlayer(Player p, Long year){
        this.player = new PlayerDTO(p);
        Optional<Usage> usage = p.getUsage().stream().filter(x -> x.getYear().equals(year)).findAny();
        this.advancedPlayer = usage.isPresent() ? new AdvancedPlayerDTO(p, usage.get()) : null;
        this.hasAdvancedPlayer = this.advancedPlayer != null;
    }
}
