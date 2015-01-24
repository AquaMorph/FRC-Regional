package com.aquamorph.frcregional.models;

import java.util.List;

public class EventMatchLists {
    public String comp_level;
    public String match_number;
    public String time_string;
    public String set_number;
    public String key;
    public String score_breakdown;

    public class alliances {

        public class blue {
            public double score;
            public List<String> teams;
        }
        public class red {
            public double score;
            public List<String> teams;
        }
    }

    public String event_key;
}
