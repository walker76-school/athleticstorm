package edu.baylor.ecs.athleticstorm.DTO.coordinator;

import edu.baylor.ecs.athleticstorm.model.coordinator.Coordinator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorDTO {
    private String name;
    private String position;
    private int startYear;
    private int endYear;
    private long schoolId;

    public CoordinatorDTO(Coordinator coordinator){
        this.name = coordinator.getName();
        this.position = coordinator.getPosition();
        this.startYear = coordinator.getStartYear();
        this.endYear = coordinator.getEndYear();
        this.schoolId = coordinator.getTeam().getId();
    }
}
