package com.example.jolfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    ImageView goto_home, goto_profile, goto_host, goto_fav ;
    TextView uName;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText editTextUsername,editTextPassword;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uName = (TextView) findViewById(R.id.textView16);

        Intent intent = getIntent();
        String str = intent.getStringExtra("name");
        String access = intent.getStringExtra("Access");
        String user = intent.getStringExtra("User");

        uName.setText(str);


        goto_home = (ImageView) findViewById(R.id.Home_Page_From_Profile);
        goto_profile = (ImageView) findViewById(R.id.Refresh_Profile);
        goto_host = (ImageView) findViewById(R.id.Host_Page_From_Profile);
        goto_fav = (ImageView) findViewById(R.id.Fav_Page_From_Profile);


        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Profile.this, MainActivity.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);


            }
        });
        goto_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Profile.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        goto_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Host.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        goto_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Favourites.class);
                intent.putExtra("name", str);
                intent.putExtra("Access", access);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

    }




    /*public void createLoginPopup(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View loginPopupView = getLayoutInflater().inflate(R.layout.activity_login, null);
        editTextUsername = (EditText) loginPopupView.findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) loginPopupView.findViewById(R.id.editTextPassword);
        loginbutton = (Button) loginPopupView.findViewById(R.id.loginbutton);

        dialogBuilder.setView(loginPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

    }*/
}