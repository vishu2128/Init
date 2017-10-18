package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class checkStudentPlaced1 extends AppCompatActivity {

    private TextView e2;
    private AutoCompleteTextView e1;
    private Button b1;
    private ImageButton ib,ib2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_student_placed1);

        Toolbar tool = (Toolbar)findViewById(R.id.studPlaced1Toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Add Placed Student");


        b1 = (Button)findViewById(R.id.button8);

        e1 = (AutoCompleteTextView)findViewById(R.id.editText34);
        e2 = (TextView)findViewById(R.id.editText35);
        ib = (ImageButton)findViewById(R.id.imageButton2);
        ib2 = (ImageButton)findViewById(R.id.imageButton3);

        getCompanies();

        e1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                e1.showDropDown();
                return false;
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((e2.getText().toString()).compareTo("Number of Students Placed")==0)
                {
                    Log.d("CHECK",e2.getText().toString());
                    e2.setText("1");
                }
                else
                {
                    Log.d("CHECK",e2.getText().toString());
                    int num = Integer.parseInt(e2.getText().toString()) + 1;
                    Log.d("CHECK",String.valueOf(num));
                    e2.setText(String.valueOf(num));
                }


            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((e2.getText().toString()).compareTo("Number of Students Placed")!=0)
                {
                    Log.d("CHECK",e2.getText().toString());
                    int num = Integer.parseInt(e2.getText().toString()) - 1;
                    if(num < 0)
                        num  = 0;
                    Log.d("CHECK",String.valueOf(num));
                    e2.setText(String.valueOf(num));
                }


            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comp, number;

                comp = e1.getText().toString();
                number = e2.getText().toString();

                Intent intent = new Intent(checkStudentPlaced1.this, checkStudentPlaced2.class);
                Bundle extras = new Bundle();
                extras.putString("compName",comp);
                extras.putString("number",number);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }

    private void getCompanies() {

        new AsyncTask<Void, Void, String[]>() {
            @Override
            protected String[] doInBackground(Void... voids) {

                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/company_names.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String data = "", line;

                    while ((line = in.readLine()) != null) {
                        data += line;
                    }
                    in.close();

                    JSONArray comp = new JSONArray(data);
                    String [] allComp = new String[comp.length()];
                    for (int i = 0; i < comp.length(); i++) {
                        allComp[i] = comp.getString(i);
                        Log.d("PAMBA",allComp[i]);
                    }

                    return allComp;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String[] allComp) {
                super.onPostExecute(allComp);

                if(allComp.length == 0)
                {
                    Toast.makeText(checkStudentPlaced1.this, "Sorry! No Companies", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(checkStudentPlaced1.this, checkUpdateStudentData.class);
                    startActivity(i);
                }

                ArrayAdapter<String> adapter= new ArrayAdapter<String>(checkStudentPlaced1.this,android.R.layout.select_dialog_item, allComp);
                e1.setThreshold(0);
                e1.setAdapter(adapter);
            }
        }.execute();
    }

}
