package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompositePlayer {

    private Player player;
    private AdvancedPlayer advancedPlayer;
    private boolean hasAdvancedPlayer;

    public CompositePlayer(){
        this.player = null;
        this.advancedPlayer = null;
        this.hasAdvancedPlayer = false;
    }

    public void setAdvancedPlayer(AdvancedPlayer advancedPlayer){
        this.advancedPlayer = advancedPlayer;
        this.hasAdvancedPlayer = advancedPlayer != null;
    }
}
