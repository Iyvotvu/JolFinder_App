package com.example.jolfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {


    ImageView Home_Page_But,Pro_Page_But,Host_Page_But,Favourites_Page_But, logout;
    Button Search_But;
    private GpsTracker gpsTracker;

    TextView tvLatitude,tvLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        String access = intent.getStringExtra("Access");
        String user = intent.getStringExtra("User");

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Pro_Page_But = (ImageView) findViewById(R.id.Profile_Page);
        Host_Page_But = (ImageView) findViewById(R.id.Host_Page);
        Favourites_Page_But = (ImageView) findViewById(R.id.Fav_Page);
        Home_Page_But = (ImageView) findViewById(R.id.Refresh_Home);
        Search_But = (Button) findViewById(R.id.Search_Places);
        logout = (ImageView)findViewById(R.id.logout) ;

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,StartPage.class);
                startActivity(intent);
            }
        });

        Pro_Page_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user.equals("user")){
                    Intent intent = new Intent(MainActivity.this, Profile.class);
                    intent.putExtra("User", user);
                    intent.putExtra("name", str);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Host_Page_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Host.class);
                intent.putExtra("name", str);
                intent.putExtra("User", user);
                intent.putExtra("Access", access);
                startActivity(intent);
            }
        });
        Favourites_Page_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.equals("user")){
                    Intent intent = new Intent(MainActivity.this, Favourites.class);
                    intent.putExtra("name", str);
                    intent.putExtra("User", user);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Home_Page_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        Search_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, Search.class);
                intent.putExtra("name", str);
                intent.putExtra("User", user);
                startActivity(intent);


            }
        });

    }


}



