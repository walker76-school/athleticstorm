package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "USAGE")
public class Usage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;

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
}
