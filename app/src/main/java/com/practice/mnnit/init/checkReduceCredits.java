package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class checkReduceCredits extends AppCompatActivity {

    private Button b1;
    private EditText e1;
    private Spinner e2;
    private String reg_no, creditsReduced;
    private String[] credits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reduce_credits);

        Toolbar tool = (Toolbar)findViewById(R.id.reducedCreditsToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Reduce Credits");

        b1 = (Button)findViewById(R.id.button7);
        e1 = (EditText)findViewById(R.id.editText32);
        e2 = (Spinner) findViewById(R.id.editText33);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(checkReduceCredits.this,android.R.layout.simple_spinner_item, credits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e2.setAdapter(adapter);
        e2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                e2.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg_no = e1.getText().toString();
                creditsReduced = e2.getSelectedItem().toString();

                sendDataToServer();
            }
        });
    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", reg_no);
            j.put("credits", creditsReduced);

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendDataToServer() {
        final String json = getJson();
        //Log.d("PARAM", json);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.d("PARAM1", s);
                if (s.compareTo("1") == 0) {
                    Toast.makeText(checkReduceCredits.this, "Credits Deducted Succesfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(checkReduceCredits.this, checkUpdateStudentData.class);
                    startActivity(i);
                } else if (s.compareTo("0") == 0) {
                    Toast.makeText(checkReduceCredits.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            URL url = new URL("http://192.168.43.25/init/tpo_credit_reduction.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("tpo_reduce=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();
            Log.d("PARAM2",data);
            JSONObject jobj = new JSONObject(data);
            return jobj.getString("status");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
