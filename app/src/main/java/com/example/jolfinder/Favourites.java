package com.example.jolfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Favourites extends AppCompatActivity {

    ImageView goto_home,goto_profile,goto_host,goto_fav;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        String access = intent.getStringExtra("Access");
        String user = intent.getStringExtra("User");

        message = (TextView)findViewById(R.id.textView4);

        message.setText(access);

        goto_home = (ImageView)findViewById(R.id.Home_Page_From_Fav);
        goto_profile = (ImageView)findViewById(R.id.Profile_Page_From_Fav);
        goto_host = (ImageView)findViewById(R.id.Host_Page_From_Fav);
        goto_fav = (ImageView)findViewById(R.id.Refresh_Fav);

        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Favourites.this, MainActivity.class);
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
                    Intent intent = new Intent(Favourites.this, Profile.class);
                    intent.putExtra("name", str);
                    intent.putExtra("User", user);
                    startActivity(intent);

                }else {
                    Toast.makeText(Favourites.this, "You do not have access to this page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goto_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Favourites.this, Host.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        goto_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Favourites.this, Favourites.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });



    }
}