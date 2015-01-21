package com.aquamorph.frcregional.connection;

import android.app.Activity;

public class PostActivity extends Activity {

    /*
    private List<EventLists> eventListses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PostFetcher fetcher = new PostFetcher();
        fetcher.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void handlePostsList(List<EventLists> eventListses) {
        this.eventListses = eventListses;





    }

    private void failedLoad(final String error, final Exception ex) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PostActivity.this, error + ex, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class PostFetcher extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                //Checks for valid connection
                HttpResponse response = Http.getRequest(Constants.getEventURL("2059"));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    try {
                        //Parse it as JSON
                        Reader reader = new InputStreamReader(content);

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        List<EventLists> eventListses = new ArrayList<EventLists>();
                        eventListses = Arrays.asList(gson.fromJson(reader, EventLists[].class));
                        content.close();

                                                       for (EventLists eventLists : PostActivity.this.eventListses) {
                                    Toast.makeText(PostActivity.this, eventLists.event_code, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(PostActivity.this, eventLists.name, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception ex) {
                        failedLoad("Failed to parse JSON due to: ",ex);
                    }
                } else {
                    failedLoad("Server responded with status code: ", null);
                }
            } catch (Exception ex) {
                failedLoad("Failed to send HTTP POST request due to: ",ex);
            }
            return null;
        }
    }*/
}
