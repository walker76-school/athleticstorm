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

    public CompositePlayer(Player p){
        this.player = new PlayerDTO(p);
        this.advancedPlayer = new AdvancedPlayerDTO(p);
        this.hasAdvancedPlayer = true;
    }
}
