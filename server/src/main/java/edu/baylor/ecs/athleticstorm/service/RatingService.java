package edu.baylor.ecs.athleticstorm.service;

import org.springframework.stereotype.Service;

@Service
public class RatingService {

    /**
     * ACS = ((OC * OW/1) + (DC * DW/1) + (SC * SW)) / (OW + DW + SC)
     *
     * HC = (ACS + (HWP * 1.5) + (AWP * 1.5))/4
     *
     * ACS = assistant coaches score
     * OC = offensive coordinator score
     * OW = offense weight
     * DC = defensive coordinator score
     * DW = defense weight
     * SC = special teams coordinator score
     * SW = special teams weight
     * HC = head coach score
     * HWP = home win percentage
     * AWP = away win percentage
     */

    private final int OW = 1;
    private final int DW = 1;

    public double getRatingsForCoach(){
        double OC = getRatingsForOC();
        double DC = getRatingsForDC();
        double ACS = ((OC * OW) + (DC * DW)) / (OW + DW);
        double HWP = 1;
        double AWP = 1;

        return (ACS + (HWP * 1.5) + (AWP * 1.5))/4;
    }

    public double getRatingsForOC(){
        double scale = 1.5;
        double rweight = 1;
        double pweight = 1.5;

        double ppa = 1.0;

        double existingRating = 1.0;

        // scale be 1.5, the rweight be 1 and pweight be 1.5
        //double rpw = (((existingRating + Math.abs(ratingLow)) * rweight) * ((ppa + Math.abs(ppaLow)) * pweight) / ((ratingHigh + Math.abs(ratingLow)) * (ppaHigh + Math.abs(ppaLow)))) * 100;
        double rpw = 1.0;
        return (existingRating + rpw * scale) / (1 + scale);
    }

    public double getRatingsForDC(){
        return -1;
    }

}
