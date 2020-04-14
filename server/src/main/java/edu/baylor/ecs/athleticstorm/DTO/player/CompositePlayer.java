package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class CompositePlayer {

    private PlayerDTO player;
    private AdvancedPlayerDTO advancedPlayer;
    private boolean hasAdvancedPlayer;

    public CompositePlayer(Player p, Long year){
        this.player = new PlayerDTO(p);
        Optional<Usage> usage = p.getUsage().stream().filter(x -> x.getYear().equals(year)).findAny();
        this.advancedPlayer = usage.isPresent() ? new AdvancedPlayerDTO(p, usage.get()) : null;
        this.hasAdvancedPlayer = this.advancedPlayer != null;
    }
}
