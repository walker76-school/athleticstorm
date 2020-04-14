package edu.baylor.ecs.athleticstorm.DTO.player;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Player;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class AdvancedPlayerDTO {

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class UsageDTO{
        private float overall;
        private float pass;
        private float rush;
        private float firstDown;
        private float secondDown;
        private float thirdDown;
        private float standardDowns;
        private float passingDowns;

        public UsageDTO(Usage u){
            this.overall = u.getOverall();
            this.rush = u.getRush();
            this.pass = u.getPass();
            this.firstDown = u.getFirstDown();
            this.secondDown = u.getSecondDown();
            this.thirdDown = u.getThirdDown();
            this.standardDowns = u.getStandardDowns();
            this.passingDowns = u.getPassingDowns();
        }
    }

    private Long id;
    private int season;
    private String name;
    private String position;
    private String team;
    private String conference;
    private UsageDTO usage;

    public AdvancedPlayerDTO(Player p, Usage u){
        this.id = p.getId();
        this.name = p.getName();
        this.position = p.getPosition();
        this.team = p.getTeam();
        //this.conference
        //this.season
        this.usage = new UsageDTO(u);
    }
}
