package edu.baylor.ecs.athleticstorm.DTO.coordinator;

import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Rated Coordinator
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedCoordinatorDTO {
    private String name;
    private String position;
    private int startYear;
    private int endYear;
    private long schoolId;
    private double rating;

    /**
     * Creates a DTO from a Coordinator
     * @param coordinator a Coordinator
     */
    public RatedCoordinatorDTO(CoordinatorDTO coordinator){
        this.name = coordinator.getName();
        this.position = coordinator.getPosition();
        this.startYear = coordinator.getStartYear();
        this.endYear = coordinator.getEndYear();
        this.schoolId = coordinator.getSchoolId();
        this.rating = -1;
    }
}
