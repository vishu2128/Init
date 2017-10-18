package com.practice.mnnit.init;


//import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class checkUpdateStudentData extends AppCompatActivity {

    private Button b1,b2,b3;
    String Updatable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update_student_data);

        Toolbar tool = (Toolbar)findViewById(R.id.checkUpdateStudToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Update Student");

        b1 = (Button)findViewById(R.id.button4);
        b2 = (Button)findViewById(R.id.button12);
        b3 = (Button)findViewById(R.id.button13);

        getUpdatable();

        for(;;)
            if(Updatable.compareTo("") != 0)
                break;

        if(Updatable.compareTo("0") == 0)
            b3.setText("Open Student Update Portal");
        else
            b3.setText("Close Student Update Portal");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(checkUpdateStudentData.this, checkStudentPlaced1.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(checkUpdateStudentData.this, checkReduceCredits.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((b3.getText().toString()).compareTo("Close Student Update Portal")==0)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(checkUpdateStudentData.this);

                    alertDialog.setTitle("Confirm Status Change");
                    alertDialog.setMessage("Are you sure you want to Close Student Update Portal?");
                    alertDialog.setIcon(R.drawable.ic_menu_manage);

                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            // Write your code here to invoke YES event
                            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            b3.setText("Open Student Update Portal");
                            changeUpdatable(0);
                        }
                    });

                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }

                if((b3.getText().toString()).compareTo("Open Student Update Portal")==0)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(checkUpdateStudentData.this);

                    alertDialog.setTitle("Confirm Status Change");
                    alertDialog.setMessage("Are you sure you want to Open Student Update Portal?");
                    alertDialog.setIcon(R.drawable.ic_menu_manage);

                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            // Write your code here to invoke YES event
                            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            b3.setText("Close Student Update Portal");
                            changeUpdatable(1);
                        }
                    });

                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }

            }
        });
    }

    private void getUpdatable() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/check_updatable.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line;
                    String data = "";

                    while ((line = in.readLine()) != null) {
                        data += line;
                    }
                    in.close();
                    Log.d("HEYY",data);
                    JSONObject j = new JSONObject(data);
                    Updatable = j.getString("updatable");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
            }
        }.execute();
    }

    private void changeUpdatable(int i) {
        final Integer val = i;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                getServerResponse1(String.valueOf(val));
                return null;
            }

            @Override
            protected void onPostExecute(Void response) {
                super.onPostExecute(response);
            }
        }.execute();
    }

    private String getServerResponse1(String json) {
        try {
            URL url = new URL("http://192.168.43.25/init/changeUpdatable.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("updatable=" + json));
            Log.d("HEYY","updatable=" + json);

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            String data = "";

            while ((line = in.readLine()) != null) {
                data += line;
            }
            in.close();
            Log.d("HEYY",data);

        } catch (IOException e) {
            Log.d("PP", "EX");
            e.printStackTrace();
        }
        return null;
    }
}







