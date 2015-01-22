package com.aquamorph.frcregional;

public class Constants {

    public static final String LOG_TAG = "FRC Regional";
    public static final String URL = "http://www.thebluealliance.com/api/v2/";
    public static final String HEADER = "christian_colglazier:frc_regional:dev";
    public static final String YEAR = "2015";
    public static final String TEAM = "2059";

    public static String getEventURL(String team) {
        return URL + "team/frc" + team + "/"+YEAR+"/events";
    }
}
