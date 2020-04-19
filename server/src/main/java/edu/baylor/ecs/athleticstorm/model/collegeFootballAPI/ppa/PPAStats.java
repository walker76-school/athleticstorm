package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.ppa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPAStats {

    private double overall;
    private double passing;
    private double rushing;
    private double firstDown;
    private double secondDown;
    private double thirdDown;
}
