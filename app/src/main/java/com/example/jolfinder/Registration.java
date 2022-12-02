package com.example.jolfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

public class Registration extends AppCompatActivity {

    EditText username, password, email;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        StrictMode.enableDefaults();

        username = (EditText) findViewById(R.id.tv_username);
        password = (EditText) findViewById(R.id.tv_password);
        email = (EditText) findViewById(R.id.tv_email);

        submit = (Button) findViewById(R.id.btn_submit);

        submit.setOnClickListener((v) -> {
            try {
                String u = username.getText().toString();
                String p = password.getText().toString();
                String e = email.getText().toString();

                if (u.isEmpty() || p.isEmpty() || e.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                } else {


                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://10.0.2.2:80/jolFinder/registration.php?username=" + u + "&password=" + p + "&email=" + e);
                    HttpResponse response = httpclient.execute(httppost);
                    Intent i1 = new Intent(getApplicationContext(), StartPage.class);
                    startActivity(i1);
                    Toast.makeText(getApplicationContext(), "Registration Success!!", Toast.LENGTH_SHORT).show();
                    Log.e("pass 1", "connection success");

                }


            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

            }

        });


    }


}
