package com.aquamorph.frcregional.connection;

import com.aquamorph.frcregional.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Http {

    public static HttpResponse getRequest(String url) {
        try {
            //Connects to the Blue Alliance
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("X-TBA-App-Id", Constants.HEADER);
            return client.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
