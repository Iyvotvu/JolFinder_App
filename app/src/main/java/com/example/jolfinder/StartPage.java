package com.example.jolfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartPage extends AppCompatActivity {

    ImageView login,register, guest;
    String noAccess,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        noAccess = "YOU DO NOT HAVE ACCESS TO THE FAVOURITES PAGE,\n SINCE YOU ARE ONLY A GUEST USER!";
        user = "guest";

        login = (ImageView) findViewById(R.id.sign_in_but);
        register = (ImageView) findViewById(R.id.register_but);
        guest = (ImageView)findViewById(R.id.guest) ;

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartPage.this, MainActivity.class);
                intent.putExtra("Access", noAccess);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(StartPage.this, Login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartPage.this, Registration.class);
                startActivity(intent);
            }
        });

    }
}