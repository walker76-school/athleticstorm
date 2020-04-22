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
import java.io.Serializable;

/**
 * conatains the usage for a player in a specific year
 *
 * @author Ian laird
 */

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "PLAYER_USAGE")
@IdClass(Usage.UsageId.class)
@EqualsAndHashCode
public class Usage implements Comparable<Usage>{

    @Data
    public static class UsageId implements Serializable {
        private Long player;
        private Long year;
    }

    @Id
    @Column(name = "YEAR", insertable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long year;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID")
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

    public Usage(AdvancedPlayerDTO.UsageDTO usageDTO, Player p, Long year){
        this.year = new Long(year);
        this.player = p;
        p.getUsage().add(this);
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
        int val = this.player.compareTo(usage.getPlayer());
        if(val == 0){
            return this.year.compareTo(usage.getYear());
        }
        return val;
    }
}
