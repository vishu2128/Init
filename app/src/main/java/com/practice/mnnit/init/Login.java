package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    private EditText e1, e2;
    private Button b1;
    private String reg, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        b1 = (Button) findViewById(R.id.button);

        Toolbar tool = (Toolbar)findViewById(R.id.loginToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Login");

       // b1.setClickable(true);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         //       b1.setClickable(false);
                reg = e1.getText().toString();
                pass = e2.getText().toString();

                if (reg.length() == 0 || pass.length() == 0) {
                    Toast.makeText(Login.this, "Complete All The Fields", Toast.LENGTH_SHORT).show();
                } else {
                    //Intent i = new Intent(Login.this, SelectPortal.class);
                    //i.putExtra("reg","20155114");
                    //startActivity(i);
                    sendDataToServer(reg,pass);
                }
            }
        });
    }

    String getJson(String s1, String s2) {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", s1);
            j.put("password", s2);

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendDataToServer(final String reg, String pass) {
        final String json = getJson(reg, pass);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);

                String s = null;
                String isTPO = null;
                try {
                    JSONObject jobj = new JSONObject(response);
                    s = jobj.getString("status");
                    isTPO = jobj.getString("isTpo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (s.compareTo("1") == 0) {
                    Intent i = new Intent(Login.this, Update.class);
                    i.putExtra("reg",reg);
                    startActivity(i);
                    finish();
                } else if (s.compareTo("0") == 0) {
                    Toast.makeText(Login.this, "No Such Registartion Number", Toast.LENGTH_SHORT).show();
                } else if (s.compareTo("-1") == 0) {
                    Toast.makeText(Login.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                } else if (s.compareTo("2") == 0) {
                    if (isTPO.compareTo("1") == 0) {

                        Log.d("CHECK","SELECT");
                        Intent i = new Intent(Login.this, SelectPortal.class);
                        i.putExtra("reg", reg);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(Login.this, StudentPortal.class);
                        i.putExtra("reg",reg);
                        startActivity(i);
                        finish();
                    }

                }

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
                    URL url = new URL("http://192.168.43.25/init/login.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream os = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(("sendData=" + json));

                    bw.flush();
                    bw.close();
                    os.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String data = "", line;

                    while ((line = in.readLine()) != null) {
                        data+=line;
                    }
                    in.close();

                    Log.d("DTA", data);

                    return data;

        } catch (IOException e) {
            Log.d("PP", "EX");
            e.printStackTrace();
        }


        return null;
    }
}