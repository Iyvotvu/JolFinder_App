package com.example.jolfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.ParseException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        EditText email= (EditText) findViewById(R.id.editTextEmailAddress);
//        EditText username = (EditText) findViewById(R.id.editTextUsername);
//        EditText password = (EditText) findViewById(R.id.editTextPassword);
//
//
//        login = (Button) findViewById(R.id.button2);
//        registration = (Button) findViewById(R.id.button);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(i);
//            }
//        });
//ABOVE GOES IN MAIN if both are on same page^

public class Login extends AppCompatActivity {

    String userN, userT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.enableDefaults();

        EditText username = (EditText) findViewById(R.id.editTextUsername);
        EditText password = (EditText) findViewById(R.id.editTextPassword);

        Button loginbutton = (Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = username.getText().toString().trim();
                userN = username.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if (userName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide a Username", Toast.LENGTH_SHORT).show();
                }

                if (userPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please provide a Password", Toast.LENGTH_SHORT).show();
                }

                String result = null;
                InputStream is = null;
                StringBuilder sb = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://10.0.2.2:80/jolfinder/login.php"); //replace with ours
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("log_tag", "Connection Error" + e.toString());
                }
                //convert response to string with format
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line = "0";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                } catch (Exception e) {
                    Log.e("log_tag", "Error converting result " + e.toString());
                }
                String u_name;
                String u_pass;

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////ERROR HANDELING NEEDS WORK///////////////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                try {
                    JSONArray jArray = new JSONArray(result);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        u_name = json_data.getString("username").toString().trim();
                        u_pass = json_data.getString("password").toString().trim();
                        // comparing table record with user id and password

                        if (userName.toString().equals(u_name.toString()) && userPassword.toString().equals(u_pass.toString())) {
                            Intent i1 = new Intent(Login.this, MainActivity.class);
                            userT = "user";
                            i1.putExtra("name", userN);
                            i1.putExtra("User", userT);
                            startActivity(i1);
                            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e1) {
                    Toast.makeText(getBaseContext(), "No User Found", Toast.LENGTH_LONG).show();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

        });
    }
}