package com.aquamorph.frcregional.network;

import android.content.Context;
import android.os.AsyncTask;

import com.aquamorph.frcregional.Constants;
import com.aquamorph.frcregional.database.EventMatchAdapter;
import com.aquamorph.frcregional.models.EventMatchLists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventMatchFetcher extends AsyncTask<Void, Void, String> {

    EventMatchAdapter myDB;
    private List<EventMatchLists> eventMatchListses;
    private Context mContext;

    public EventMatchFetcher(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        //Checks for valid connection
        HttpResponse response = Http.getRequest(Constants.getEventURL(Constants.TEAM));
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = null;
            try {
                content = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Parse it as JSON
            Reader reader = new InputStreamReader(content);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            eventMatchListses = new ArrayList<EventMatchLists>();
            eventMatchListses = Arrays.asList(gson.fromJson(reader, EventMatchLists[].class));
            try {
                content.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            myDB = new EventMatchAdapter(mContext);
            myDB.open();

            myDB.deleteAll();

            for (EventMatchLists eventLists : EventMatchFetcher.this.eventMatchListses) {
                myDB.insertRow(
                        /*eventLists.comp_level,
                        eventLists.match_number,
                        eventLists.time_string,
                        eventLists.set_number,
                        eventLists.key,
                        eventLists.score_breakdown,*/
                        "42",
                        "2059",
                        "2059",
                        "2059",
                        "42",
                        "2059",
                        "42",
                        "2059",
                        "2059",
                        "2059",
                        "42",
                        "2059",
                        "2059",
                        "2059"
                        );
            }
            myDB.close();
        }
        return null;
    }
}