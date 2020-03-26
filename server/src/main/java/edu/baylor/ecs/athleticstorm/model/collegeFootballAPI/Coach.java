package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor

@Entity
@Table(name = "COACH")
public class Coach {

    @Column(name = "FIRST_NAME")
    private String first_name;

    @Column(name = "LAST_NAME")
    private String last_name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "SCHOOL_NAME", referencedColumnName = "SCHOOL"),
            @JoinColumn(name = "SCHOOL_YEAR", referencedColumnName = "YEAR")
    })
    private List<Season> seasons;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "ID")
    private Team team;
}
