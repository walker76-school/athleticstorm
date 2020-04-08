/******************************************************************************
 *
 * Usage.java
 *
 * author: Ian laird
 *
 * Created 3/24/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.player;

import edu.baylor.ecs.athleticstorm.DTO.player.AdvancedPlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "PLAYER_USAGE")
@EqualsAndHashCode
public class Usage implements Comparable<Usage>{

    @Id
    @Column(name = "PLAYER_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    @MapsId
    @EqualsAndHashCode.Exclude
    private Player player;

    @Column(name = "OVERALL")
    @EqualsAndHashCode.Exclude
    private float overall;

    @Column(name = "PASS")
    @EqualsAndHashCode.Exclude
    private float pass;

    @Column(name = "RUSH")
    @EqualsAndHashCode.Exclude
    private float rush;

    @Column(name = "FIRST_DOWN")
    @EqualsAndHashCode.Exclude
    private float firstDown;

    @Column(name = "SECOND_DOWN")
    @EqualsAndHashCode.Exclude
    private float secondDown;

    @Column(name = "THIRD_DOWN")
    @EqualsAndHashCode.Exclude
    private float thirdDown;

    @Column(name = "STANDARD_DOWNS")
    @EqualsAndHashCode.Exclude
    private float standardDowns;

    @Column(name = "PASSING_DOWNS")
    @EqualsAndHashCode.Exclude
    private float passingDowns;

    public Usage(AdvancedPlayerDTO.UsageDTO usageDTO, Player p){
        this.id = p.getId();
        this.player = p;
        p.setUsage(this);
        this.overall = usageDTO.getOverall();
        this.pass = usageDTO.getPass();
        this.rush = usageDTO.getRush();
        this.firstDown = usageDTO.getFirstDown();
        this.secondDown = usageDTO.getSecondDown();
        this.thirdDown = usageDTO.getThirdDown();
        this.standardDowns = usageDTO.getStandardDowns();
        this.passingDowns = usageDTO.getPassingDowns();
    }

    @Override
    public int compareTo(Usage usage) {
        return this.id.compareTo(usage.getId());
    }
}
