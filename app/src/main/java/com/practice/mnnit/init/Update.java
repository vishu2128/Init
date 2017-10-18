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

public class Update extends AppCompatActivity {

    private EditText[] e = new EditText[20];
    private Button b;
    private String[] s = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar tool = (Toolbar)findViewById(R.id.updateToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Fill in the Details");

        e[0] = (EditText)findViewById(R.id.editText3);
        e[1] = (EditText)findViewById(R.id.editText4);
        e[2] = (EditText)findViewById(R.id.editText5);
        e[3] = (EditText)findViewById(R.id.editText6);
        e[4] = (EditText)findViewById(R.id.editText7);
        e[5] = (EditText)findViewById(R.id.editText8);
        e[6] = (EditText)findViewById(R.id.editText9);
        e[7] = (EditText)findViewById(R.id.editText10);
        e[8] = (EditText)findViewById(R.id.editText11);
        e[9] = (EditText)findViewById(R.id.editText12);
        e[10] = (EditText)findViewById(R.id.editText13);
        e[11] = (EditText)findViewById(R.id.editText14);
        e[12] = (EditText)findViewById(R.id.editText15);
        e[13] = (EditText)findViewById(R.id.editText16);
        e[14] = (EditText)findViewById(R.id.editText17);
        e[15] = (EditText)findViewById(R.id.editText18);
        b = (Button)findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0; i<=15; i++)
                s[i] = e[i].getText().toString();
                if(s[14].compareTo(s[15]) != 0)
                {
                    Toast.makeText(Update.this,"Password dont match",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendDataToServer();
                }

            }
        });
    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("name",s[0]);
            j.put("gender",s[1]);
            j.put("dob",s[2]);
            j.put("course",s[3]);
            j.put("branch",s[4]);
            j.put("cpi",s[5]);
            j.put("twe",s[6]);
            j.put("ten",s[7]);
            j.put("email",s[8]);
            j.put("contact",s[9]);
            j.put("address",s[10]);
            j.put("city",s[11]);
            j.put("state",s[12]);
            j.put("hostel",s[13]);
            j.put("pass",s[14]);

            String reg = getIntent().getStringExtra("reg");

            j.put("reg_no",reg);


            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendDataToServer() {
        final String json = getJson();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                if (s.compareTo("1") == 0) {
                    Intent i = new Intent(Update.this, checkFileSend.class);
                    startActivity(i);
                } else if (s.compareTo("0") == 0) {
                    Toast.makeText(Update.this, "No Such Registartion Number", Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(Update.this, checkFileSend.class);
                i.putExtra("reg", getIntent().getStringExtra("reg"));
                startActivity(i);
            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            URL url = new URL("http://192.168.43.25/init/register.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            //con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            //con.getOutputStream().write( ("name=" + name).getBytes());

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("details=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();

            JSONObject jobj = new JSONObject(data);
            return jobj.getString("status");

        } catch (IOException e) {
            Log.d("PP","EX");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


}
