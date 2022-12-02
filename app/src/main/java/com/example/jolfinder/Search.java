package com.example.jolfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;

public class Search extends AppCompatActivity {

    private static final int REQUEST_NEW_RANGE = 1;
    private static final String TAG = "Search";

    int Range = 10;
    String NewRange;

    ImageView f_search, refresh,goto_home,goto_profile,goto_host,goto_guest,goto_fav;

    double lat, lon;

    private GpsTracker gpsTracker;


    String JSON_STRING;
    String json_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        String access = intent.getStringExtra("Access");
        String user = intent.getStringExtra("User");


        gpsTracker = new GpsTracker(Search.this);
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lon = gpsTracker.getLongitude();


        } else {
            gpsTracker.showSettingsAlert();
        }


        goto_home = (ImageView) findViewById(R.id.Home_Page_From_Search);
        goto_profile = (ImageView) findViewById(R.id.Profile_Page_From_Search);
        goto_host = (ImageView) findViewById(R.id.Host_Page_From_Search);
        goto_fav = (ImageView) findViewById(R.id.Fav_Page_From_Search);
        f_search = (ImageView) findViewById(R.id.f_search);
        refresh = (ImageView) findViewById(R.id.refresh_but);


        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        goto_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.equals("user")){
                    Intent intent = new Intent(Search.this, Profile.class);
                    intent.putExtra("name", str);
                    intent.putExtra("User", user);
                    startActivity(intent);

                }else {
                    Toast.makeText(Search.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goto_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, Host.class);
                intent.putExtra("name", str);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        goto_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.equals("user")){
                    Intent intent = new Intent(Search.this, Favourites.class);
                    intent.putExtra("name", str);
                    intent.putExtra("User", user);
                    startActivity(intent);

                }else {
                    Toast.makeText(Search.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        f_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, filter_search.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivityForResult(intent, REQUEST_NEW_RANGE);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute();
            }
        });


        //FETCHING DATA USING JSON//
        new BackgroundTask().execute();
        ////////////////////////////


    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;

        JSONObject jsonObject;
        JSONArray jsonArray;
        EventAdapter eventAdapter;
        ListView listView;



        @Override
        protected void onPreExecute() {
            json_url = "http://10.0.2.2:80/jolfinder/get_details.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            listView = (ListView) findViewById(R.id.event_listview);
            eventAdapter = new EventAdapter(Search.this, R.layout.row_layout);
            listView.setAdapter(eventAdapter);
            json_string = result;

            if (json_string == null) {
                Toast.makeText(getApplicationContext(), "There are no events near you :(", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Looks like were having fun tonight :)", Toast.LENGTH_SHORT).show();
            }

            /**
             * FETCHES EVENT DATA FROM DATABASE TO PASS TO DISTANCE METHOD
             */
            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");

                int count = 0;
                String eventN, eventLat, eventLong, image;

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    final RelativeLayout relativeLayout = null;
                    eventN = JO.getString("event_name");
                    eventLat = JO.getString("event_lat");
                    eventLong = JO.getString("event_long");
                    image = JO.getString("img");

                    Double eventLat_val = Double.parseDouble(eventLat);
                    Double eventLon_val = Double.parseDouble(eventLong);


                    Double event_distance = distance(eventLat_val, lat, eventLon_val, lon);
                    Long e_dist = Math.round(event_distance);

                    if (e_dist > Range) {
                        count++;
                    }else{
                        Events events = new Events(eventN, e_dist.toString() + " Km", image);
                        eventAdapter.add(events);
                        count++;

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    /**
    * TAKES USERS LOCATION AND EVENTS LOCATION
     * OUTPUTS A DISTANCE IN KM
     * TAKES IN ACCOUNT CURVATURE OF EARTH
     */
    public static double distance(double eventLat, double userLat, double eventLon, double userLon) {

        eventLon = Math.toRadians(eventLon);
        userLon = Math.toRadians(userLon);
        eventLat = Math.toRadians(eventLat);
        userLat = Math.toRadians(userLat);

        double dlon = userLon - eventLon;
        double dlat = userLat - eventLat;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(eventLat) * Math.cos(userLat)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.sin(Math.sqrt(a));

        double r = 6371;

        return (c * r);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_NEW_RANGE:
                if (resultCode == Activity.RESULT_OK) {
                    NewRange = data.getStringExtra("Range");
                    Range = Integer.parseInt(NewRange);


                    Log.d(TAG, "onActivityResult: " + Range);
                } else {
                    Log.d(TAG, "onActivityResult: canceled");
                }
        }
    }
}