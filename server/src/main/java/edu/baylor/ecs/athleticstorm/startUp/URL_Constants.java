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

/**
 * Contains URLs that are used to access the online API
 *
 * @author Ian Laird
 */
public final class URL_Constants {
    private URL_Constants(){}

    // the base url of the API
    public static final String BASE_URL = "https://api.collegefootballdata.com";

    // team url
    public static final String TEAM_URL =   BASE_URL + "/teams";

    // fbs url
    public static final String FBS_URL =    BASE_URL + "/teams/fbs";

    // coach url
    public static final String COACH_URL =  BASE_URL + "/coaches";

    // player url
    public static final String PLAYER_URL = BASE_URL + "/player";

    /**
     * gets the coaches for specified team
     *
     * @param teamName the name of the team
     */
    public static String coachByTeamId(String teamName){
        return COACH_URL + "?team=" + teamName + "&minYear=2000";
    }

    /**
     * creates roster url for team and year
     * @param team the team
     * @param year the year
     * @return the url
     */
    public static String rosterByTeamAndYear(String team, String year){
        return BASE_URL + "/roster?team=" + team + (Objects.isNull(year) ? "" : "&year=" + year);
    }

    /**
     * url for coach by first and last name
     * @param firstName the first name
     * @param lastName the last name
     * @return the url
     */
    public static String coachByName(String firstName, String lastName){
        return COACH_URL + "?firstName=" + firstName + "?lastName=" + lastName;
    }

    /**
     * player url by full name and team
     * @param name the name of the player
     * @param team the team name
     * @return the url
     */
    public static String playerByFullNameAndTeam(String name, String team){
        return PLAYER_URL + "/search?searchTerm=" + name + "&team=" + team;
    }

    /**
     * url for player usage
     * @param year the year
     * @param playerId the id of the player
     * @return the url
     */
    public static String playerUsage(String year, String playerId){
        return PLAYER_URL + "/usage?year=" + year + (Objects.nonNull(playerId) ? ("&playerId=" + playerId) : "");
    }

    /**
     * url for team games
     * @param year the year
     * @param team the team name
     * @return url
     */
    public static String gamesPerTeamAndYear(int year, String team){
        return BASE_URL + "/games?year=" + year + "&seasonType=regular" + (!team.isEmpty() ? ("&team=" + team) : "");
    }

    /**
     * sp ratings
     * @param year the year
     * @return the url
     */
    public static String spRatings(int year){
        return BASE_URL + "/ratings/sp?year=" + year;
    }

    /**
     * ppa ratings
     * @param year the year
     * @return the url
     */
    public static String ppaRatings(int year){
        return BASE_URL + "/ppa/games?year=" + year + "&excludeGarbageTime=true";
    }
}
