package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import edu.baylor.ecs.athleticstorm.DTO.coach.CoachDTO;
import edu.baylor.ecs.athleticstorm.DTO.season.SeasonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@AllArgsConstructor
@Data

@Entity(name = "Coach")
@Table(name = "COACH")
@EqualsAndHashCode
@NoArgsConstructor
public class Coach implements Comparable<Coach> {

    @Id
    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "COACH_TO_SEASON",
            joinColumns =
            @JoinColumn(name = "COACH_NAME", referencedColumnName = "NAME"),
            inverseJoinColumns = {
                    @JoinColumn(name = "SCHOOL", referencedColumnName = "SCHOOL"),
                    @JoinColumn(name = "YEAR", referencedColumnName = "YEAR")
            }
    )
    @EqualsAndHashCode.Exclude
    private Set<Season> seasons = new TreeSet<>();

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")
    @EqualsAndHashCode.Exclude
    private Team team = null;

    @Override
    public int compareTo(Coach coach) {
        return this.name.compareTo(coach.getName());
    }

    public Coach(CoachDTO coach, Set<Season> seasons){
        this.name = coach.getFirst_name() + " " + coach.getLast_name();

        for(SeasonDTO seasonDTO: coach.getSeasons()){
            Season s = seasons.stream().filter(x -> x.getSchool().equalsIgnoreCase(seasonDTO.getSchool()) && x.getYear() == seasonDTO.getYear()).findAny().orElse(new Season(seasonDTO));
            seasons.add(s);
            s.getCoaches().add(this);
            this.getSeasons().add(s);
        }
    }
}
