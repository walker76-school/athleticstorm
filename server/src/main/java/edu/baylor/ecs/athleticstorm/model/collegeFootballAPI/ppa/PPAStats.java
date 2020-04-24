/*
 * Filename: PPAStats.java
 * Author: Andrew Walker
 * Date Last Modified: 4/22/2020
 */

package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.ppa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents specific PPA stats from CollegeFootballAPI
 *
 * @author Andrew Walker
 */
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
