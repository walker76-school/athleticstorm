/*
 * Filename: CoordinatorDTO.java
 * Author: Andrew Walker
 * Date Last Modified: 4/17/2020
 */

package edu.baylor.ecs.athleticstorm.DTO.coordinator;

import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Coordinator
 *
 * @author Andrew Walker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorDTO {
    private String name;
    private String position;
    private int startYear;
    private int endYear;
    private long schoolId;

    /**
     * Creates a DTO from a Coordinator
     * @param coordinator a Coordinator
     */
    public CoordinatorDTO(Coordinator coordinator){
        this.name = coordinator.getName();
        this.position = coordinator.getPosition();
        this.startYear = coordinator.getStartYear();
        this.endYear = coordinator.getEndYear();
        this.schoolId = coordinator.getTeam().getId();
    }
}
