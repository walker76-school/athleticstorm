package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.DTO.player.AdvancedPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "PLAYER_USAGE")
public class Usage {

    @Id
    @Column(name = "PLAYER_ID")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "PLAYER_ID", referencedColumnName = "PLAYER_ID")
    private Player player;

    @Column(name = "OVERALL")
    private float overall;

    @Column(name = "PASS")
    private float pass;

    @Column(name = "RUSH")
    private float rush;

    @Column(name = "FIRST_DOWN")
    private float firstDown;

    @Column(name = "SECOND_DOWN")
    private float secondDown;

    @Column(name = "THIRD_DOWN")
    private float thirdDown;

    @Column(name = "STANDARD_DOWNS")
    private float standardDowns;

    @Column(name = "PASSING_DOWNS")
    private float passingDowns;

    public Usage(AdvancedPlayer.UsageDTO usageDTO, Player p){
        this.id = p.getId();
        this.player = p;
        this.overall = usageDTO.getOverall();
        this.pass = usageDTO.getPass();
        this.rush = usageDTO.getRush();
        this.firstDown = usageDTO.getFirstDown();
        this.secondDown = usageDTO.getSecondDown();
        this.thirdDown = usageDTO.getThirdDown();
        this.standardDowns = usageDTO.getStandardDowns();
        this.passingDowns = usageDTO.getPassingDowns();
    }
}
