package edu.baylor.ecs.athleticstorm.model.coordinator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinator {
    private String name;
    private String position;
    private int startYear;
    private int endYear;
}
