package edu.baylor.ecs.athleticstorm.service;

import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.ppa.PPA;
import edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.spRatings.SPRating;
import edu.baylor.ecs.athleticstorm.model.rating.RatingComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static edu.baylor.ecs.athleticstorm.startUp.Constants.*;

@Service
public class RatingService {

    @Autowired
    private RestTemplate restTemplate;

    private final double SWEIGHT = 0.5; // Weight of SP+ Ratings
    private final double PWEIGHT = 1.0; // Weight of PPA Ratings

    private final double OW = 50; // Weight of Offensive Score
    private final double DW = 50; // Weight of Defensive Score
    private final double HWW = 1.5; // Weight of Home Wins
    private final double AWW = 1.5; // Weight of Away Wins

    static final Map<Integer, Set<SPRating>> SP_RATINGS = new HashMap<>();
    static final Map<Integer, Set<PPA>> PPA_RATINGS = new HashMap<>();

    static final Map<Integer, SPRating> LOWEST_SP_RATINGS = new HashMap<>();
    static final Map<Integer, SPRating> HIGHEST_SP_RATINGS = new HashMap<>();

    static boolean isInit = false;

    @Transactional
    public void init(){
        if(SP_RATINGS.size() == 0){
            SPRating[] spRatings = restTemplate.getForObject(spRatings(), SPRating[].class);
            assert spRatings != null;
            for(SPRating rating : spRatings){
                Set<SPRating> ratingPerYear = SP_RATINGS.getOrDefault(rating.getYear(), new HashSet<>());
                ratingPerYear.add(rating);
                SP_RATINGS.put(rating.getYear(), ratingPerYear);
            }
        }

        // Lowest / Highest SP Ratings For Each Year
        for(Map.Entry<Integer, Set<SPRating>> entry : SP_RATINGS.entrySet()){
            SPRating min = entry.getValue().stream().min(Comparator.comparing(SPRating::getRating)).get();
            SPRating max = entry.getValue().stream().max(Comparator.comparing(SPRating::getRating)).get();
            LOWEST_SP_RATINGS.put(entry.getKey(), min);
            HIGHEST_SP_RATINGS.put(entry.getKey(), max);
        }

        if(PPA_RATINGS.size() == 0){
            for(int year = 2017; year <= 2019; year++) {
                PPA[] ppas = restTemplate.getForObject(ppaRatings(year), PPA[].class);
                assert ppas != null;
                PPA_RATINGS.put(year, new HashSet<>(Arrays.asList(ppas)));
            }
        }

        isInit = true;
    }

    public RatingComposite getRatings(String team, int year, int week, double hwp, double awp){

        if(!isInit){
            init();
        }

        String opponent = PPA_RATINGS.get(year).stream().filter(x -> x.getWeek() == week && x.getTeam().equalsIgnoreCase(team))
                .findFirst()
                .get()
                .getOpponent();

        double sRating = SP_RATINGS.get(year).stream().filter(x -> x.getTeam().equalsIgnoreCase(opponent)).findFirst().get().getRating(); // SP+ Rating of week's opponent
        double sRatingLow = Math.abs(LOWEST_SP_RATINGS.get(year).getRating()); // Lowest SP+ of the week
        double sRatingHigh = Math.abs(HIGHEST_SP_RATINGS.get(year).getRating()); // Highest SP+ of the week

        PPA ppa = PPA_RATINGS.get(year).stream().filter(x -> x.getWeek() == week && x.getTeam().equalsIgnoreCase(team))
                .findFirst()
                .get(); // Avg PPA of Offensive

        PPA ppaHigh = PPA_RATINGS.get(year).stream().filter(x -> x.getWeek() == week && x.getTeam().equalsIgnoreCase(team))
                .max(Comparator.comparing(a -> a.getOffense().getOverall()))
                .get(); // Highest PPA in League

        PPA ppaLow = PPA_RATINGS.get(year).stream().filter(x -> x.getWeek() == week && x.getTeam().equalsIgnoreCase(team))
                .max(Comparator.comparing(a -> a.getOffense().getOverall()))
                .get(); // Lowest PPA in League

        double numeratorOC = ((sRating + Math.abs(sRatingLow)) * SWEIGHT) * ((ppa.getOffense().getOverall() + Math.abs(ppaLow.getOffense().getOverall())) * PWEIGHT);
        double denominatorOC = ((sRatingHigh + Math.abs(sRatingLow)) * SWEIGHT) * ((ppaHigh.getOffense().getOverall() + Math.abs(ppaLow.getOffense().getOverall())) * PWEIGHT);

        double numeratorDC = ((sRating + Math.abs(sRatingLow)) * SWEIGHT) * ((ppa.getDefense().getOverall() + Math.abs(ppaLow.getDefense().getOverall())) * PWEIGHT);
        double denominatorDC = ((sRatingHigh + Math.abs(sRatingLow)) * SWEIGHT) * ((ppaHigh.getDefense().getOverall() + Math.abs(ppaLow.getDefense().getOverall())) * PWEIGHT);

        double ocr = numeratorOC / denominatorOC * 100;
        double dcr = numeratorDC / denominatorDC * 100;

        double acr = ((ocr * (OW / 100)) + (dcr * (DW / 100))) / (OW + DW);

        double coachScore = (acr + (hwp * HWW) + (awp * AWW)) / 4;

        return new RatingComposite(coachScore, ocr, dcr);
    }
}
