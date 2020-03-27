/******************************************************************************
 *
 * Constants.java
 *
 * author: Ian laird
 *
 * Created 3/27/20
 *
 * © 2020
 *
 ******************************************************************************/

package edu.baylor.ecs.athleticstorm.startUp;

import java.util.Objects;

public final class Constants {
    private Constants(){}

    public static final String BASE_URL = "https://api.collegefootballdata.com";
    public static final String TEAM_URL =   BASE_URL + "/teams";
    public static final String FBS_URL =    BASE_URL + "/teams/fbs";
    public static final String COACH_URL =  BASE_URL + "/coaches";
    public static final String PLAYER_URL = BASE_URL + "/players";

    public static String coachByTeamId(String teamName){
        return COACH_URL + "?team=" + teamName + "&minYear=2000";
    }
    public static String rosterByTeamAndYear(String team, String year){
        return BASE_URL + "/roster?team=" + team + (Objects.isNull(year) ? "" : "&year=" + year);
    }
    public static String coachByName(String firstName, String lastName){
        return COACH_URL + "?firstName=" + firstName + "?lastName=" + lastName;
    }
    public static String playerByFullName(String name){
        return PLAYER_URL + "/search?searchTerm=" + name;
    }
    public static String playerUsage(String year, String playerId){
        return PLAYER_URL + "usage?year=" + year + "&playerId=" + playerId;
    }
}
