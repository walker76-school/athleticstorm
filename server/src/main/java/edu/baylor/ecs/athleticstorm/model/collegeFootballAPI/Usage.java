package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Usage {
    private float overall;
    private float pass;
    private float rush;
    private float firstDown;
    private float secondDown;
    private float thirdDown;
    private float standardDowns;
    private float passingDowns;
}
