package com.example.jolfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;


public class Host extends AppCompatActivity {


    private static final String TAG = "Host_Maps";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int REQUEST_CODE_LOCATION = 42;

    private Double event_lat,event_lng;


    ImageView connect_but,goto_fav,goto_guest,goto_host,goto_profile,goto_home;
    TextView tv;
    EditText e_name, e_host;

    Double Event_lat,Event_lng;

    String elat,elng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        String access = intent.getStringExtra("Access");
        String user = intent.getStringExtra("User");


        goto_home = (ImageView)findViewById(R.id.Home_Page_From_Host);
        goto_profile = (ImageView)findViewById(R.id.Profile_Page_From_Host);
        goto_host = (ImageView)findViewById(R.id.Refresh_Host);
        goto_fav = (ImageView)findViewById(R.id.Fav_Page_From_Host);
        connect_but = (ImageView)findViewById(R.id.button2);

        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host.this, MainActivity.class);
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
                    Intent intent = new Intent(Host.this, Profile.class);
                    intent.putExtra("name", str);
                    intent.putExtra("User", user);
                    startActivity(intent);

                }else {
                    Toast.makeText(Host.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goto_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host.this, Host.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        goto_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.equals("user")){
                    Intent intent = new Intent(Host.this, Favourites.class);
                    intent.putExtra("name", str);
                    intent.putExtra("User", user);
                    startActivity(intent);

                }else {
                    Toast.makeText(Host.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        connect_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e_name = (EditText)findViewById(R.id.event_name);
                e_host = (EditText)findViewById(R.id.event_host);


                String event_name = e_name.getText().toString();
                String event_host = e_host.getText().toString();

                Double con_elat = Double.parseDouble(elat);
                Double con_elng = Double.parseDouble(elng);

                StrictMode.enableDefaults();

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost insertEvent = new HttpPost("http://10.0.2.2:80/jolfinder/insert_event.php?eve_name="+ event_name+"&eve_host="+ event_host+"&eve_lat="+ con_elat+"&eve_lng="+ con_elng);
                    HttpResponse reponseToQuery = httpClient.execute(insertEvent);

                    Toast toast = Toast.makeText(getApplicationContext(), "Event Successfully added", Toast.LENGTH_SHORT);
                    toast.show();
                    e_name.setText("");
                    e_host.setText("");



                }catch (Exception e){

                    Toast toast = Toast.makeText(getApplicationContext(), "Event Could not be added", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

        if(isServicesOK()){
            init();
        }


    }

    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host.this,Host_Map.class);
                startActivityForResult(intent, REQUEST_CODE_LOCATION );
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (resultCode == Activity.RESULT_OK) {
                    elat = data.getStringExtra("Map_Lat");
                    elng = data.getStringExtra("Map_Lng");

                    Log.d(TAG, "onActivityResult: " + elat +" "+ elng);
                } else {
                    Log.d(TAG, "onActivityResult: canceled");
                }
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Host.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and user can make map requests
            Log.d(TAG,"isServicesOK: Google Play Services is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error ocured but fixable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Host.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}