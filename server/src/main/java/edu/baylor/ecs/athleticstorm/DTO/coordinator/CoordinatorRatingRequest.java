package edu.baylor.ecs.athleticstorm.DTO.coordinator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorRatingRequest {
    private String name;
    private long teamId;
}
