package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class AdvancedPlayer {

    @Data
    public static class UsageDTO{
        private float overall;
        private float pass;
        private float rush;
        private float firstDown;
        private float secondDown;
        private float thirdDown;
        private float standardDowns;
        private float passingDowns;
    }

    private Long id;

    private Player player;

    private int season;

    private String name;

    private String position;

    private String team;

    private String conference;

    private UsageDTO usage;
}
