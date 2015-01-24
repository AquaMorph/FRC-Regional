package com.aquamorph.frcregional.network;

import android.content.Context;
import android.os.AsyncTask;

import com.aquamorph.frcregional.Constants;
import com.aquamorph.frcregional.database.EventAdapter;
import com.aquamorph.frcregional.models.EventLists;
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

public  class PostFetcher extends AsyncTask<Void, Void, String> {

    EventAdapter myDB;
    private List<EventLists> eventListses;
    private Context mContext;

    public PostFetcher(Context context) {
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
            eventListses = new ArrayList<EventLists>();
            eventListses = Arrays.asList(gson.fromJson(reader, EventLists[].class));
            try {
                content.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //myDB = new EventAdapter(mContext);
            //myDB.open();

            //myDB.deleteAll();

            for (EventLists eventLists : PostFetcher.this.eventListses) {
               //myDB.insertRow(eventLists.name, eventLists.event_code, eventLists.end_date);
            }
            //myDB.close();

        }
        return null;
    }
}