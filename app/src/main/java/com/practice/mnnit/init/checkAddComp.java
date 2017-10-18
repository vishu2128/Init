package com.practice.mnnit.init;

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

public class checkAddComp extends AppCompatActivity {

    private String name, ctc, courses, CPI,designation,location,link,dateTest,dateArrival,dateReg;
    private EditText e1,e2,e4,e5,e6,e7,e8,e9,e10,e11;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_add_comp);

        Toolbar tool = (Toolbar)findViewById(R.id.addCompanyToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Add Company");

        e1 = (EditText)findViewById(R.id.editText19);
        e2 = (EditText)findViewById(R.id.editText27);
        e4 = (EditText)findViewById(R.id.editText21);
        e5 = (EditText)findViewById(R.id.editText22);
        e6 = (EditText)findViewById(R.id.editText23);
        e7 = (EditText)findViewById(R.id.editText24);
        e8 = (EditText)findViewById(R.id.editText25);
        e9 = (EditText)findViewById(R.id.editText26);
        e10 = (EditText)findViewById(R.id.editText28);
        e11 = (EditText)findViewById(R.id.editText36);

        b1 = (Button)findViewById(R.id.button3);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = e1.getText().toString();
                ctc = e2.getText().toString();
                courses = e4.getText().toString();
                CPI= e5.getText().toString();
                designation = e6.getText().toString();
                location = e7.getText().toString();
                link = e8.getText().toString();
                dateTest = e9.getText().toString();
                dateArrival = e10.getText().toString();
                dateReg = e11.getText().toString();

                sendDataToServer();
            }
        });

    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("name", name);
            j.put("ctc", ctc);
            j.put("courses",courses);
            j.put("cpi",CPI);
            j.put("designation",designation);
            j.put("location",location);
            j.put("lnk",link);
            j.put("dateTest",dateTest);
            j.put("dateArrival",dateArrival);
            j.put("dateReg",dateReg);

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendDataToServer() {
        final String json = getJson();
        Log.d("PARAM", json);

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
                    Toast.makeText(checkAddComp.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                    e1.setText("");
                    e2.setText("");
                    e4.setText("");
                    e5.setText("");
                    e6.setText("");
                    e7.setText("");
                    e8.setText("");
                    e9.setText("");
                    e10.setText("");
                    e11.setText("");
                } else if (s.compareTo("0") == 0) {
                    Toast.makeText(checkAddComp.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                else if(s.compareTo("2") == 0) {
                    Toast.makeText(checkAddComp.this,"Company Already Exists",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/addCompany.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("companydata=" + json));

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
