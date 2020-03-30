package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompositePlayer {

    private PlayerDTO player;
    private AdvancedPlayerDTO advancedPlayer;
    private boolean hasAdvancedPlayer;

    public CompositePlayer(Player p, AdvancedPlayerDTO advancedPlayerDTO){
        this.player = new PlayerDTO(p);
        this.advancedPlayer = advancedPlayerDTO;
        this.hasAdvancedPlayer = advancedPlayer != null;
    }
}
