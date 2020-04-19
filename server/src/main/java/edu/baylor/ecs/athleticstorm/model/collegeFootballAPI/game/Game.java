package edu.baylor.ecs.athleticstorm.model.collegeFootballAPI.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private int id;
    private int season;
    private int week;
    private String season_type;
    private String start_date;
    private boolean start_time_tbd;
    private boolean neutral_site;
    private boolean conference_game;
    private int attendance;
    private int venue_id;
    private String venue;
    private int home_id;
    private String home_team;
    private String home_conference;
    private int home_points;
    private int[] home_line_scores;
    private double home_post_win_prob;
    private int away_id;
    private String away_team;
    private String away_conference;
    private int away_points;
    private int[] away_line_scores;
    private double away_post_win_prob;
    private double excitement_index;
}
