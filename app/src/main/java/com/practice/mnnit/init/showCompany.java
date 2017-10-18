package com.practice.mnnit.init;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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

public class showCompany extends AppCompatActivity {

    private String name,reg, branch, course, status, eligibility;
    private Button t[] = new Button[10];
    private Button b;
    private WebView w;
    Integer credits;
    Float cpi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_company);

        Toolbar tool = (Toolbar)findViewById(R.id.showCompanyToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Show Company");


        name = getIntent().getStringExtra("name");
        reg = getIntent().getStringExtra("reg");
        branch = getIntent().getStringExtra("branch");
        status = getIntent().getStringExtra("status");
        if(status.compareTo("1") == 0)
        {
            credits = Integer.parseInt(getIntent().getStringExtra("credits"));
            cpi = Float.valueOf(getIntent().getStringExtra("cpi"));
        }
        t[0] = (Button) findViewById(R.id.showCompanyName1);
        t[1] = (Button) findViewById(R.id.showCompanyName2);
        t[2] = (Button)findViewById(R.id.showCompanyName3);
        t[3] = (Button)findViewById(R.id.showCompanyName4);
        t[4] = (Button)findViewById(R.id.showCompanyName5);
        t[5] = (Button)findViewById(R.id.showCompanyName6);
        t[6] = (Button) findViewById(R.id.showCompanyName7);
        t[7] = (Button)findViewById(R.id.showCompanyName8);
        t[8] = (Button) findViewById(R.id.showCompanyName9);
        b = (Button)findViewById(R.id.button17);
        w = (WebView)findViewById(R.id.webView);

        getDataFromServer();

        if(status.compareTo("0") == 0)
            b.setVisibility(View.INVISIBLE);
        else
            b.setVisibility(View.VISIBLE);

        if(status.compareTo("0") != 0)
        {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBranch() == false)
                        Toast.makeText(showCompany.this, "Sorry! Your Branch is Not Allowed", Toast.LENGTH_SHORT).show();
                    else if(checkCPI() == false)
                        Toast.makeText(showCompany.this, "Sorry! Your CPI is lower than the Required cut-off", Toast.LENGTH_SHORT).show();
                    else if(credits <= 0)
                        Toast.makeText(showCompany.this, "Sorry! You do not have enough TPO Credits", Toast.LENGTH_SHORT).show();
                    else
                        register();

                }
            });
        }


        t[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FSFS",t[1].getText().toString());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + t[1].getText().toString()));
                startActivity(browserIntent);
            }
        });
    }

    private void getDataFromServer() {
        String json = null;
        final JSONObject j = new JSONObject();
        try {
            j.put("name", name);

            json =  j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJson = json;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse(finalJson);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jobj = new JSONObject(s);
                    t[0].setText(jobj.getString("company_name"));
                    t[1].setText(jobj.getString("lnk"));
                    t[2].setText("Courses Allowed: " + jobj.getString("courses"));
                    course = jobj.getString("courses");
                    t[3].setText("Eligibility Criteria: " + jobj.getString("eligibility"));
                    eligibility = jobj.getString("eligibility");
                    t[4].setText("CTC: " + jobj.getString("ctc"));
                    t[5].setText("Designation: " + jobj.getString("designation"));
                    t[6].setText("Location: " + jobj.getString("location"));
                    t[7].setText("Expected Date of Online Test: " + jobj.getString("date_test"));
                    t[8].setText("Last Date of Registration: " + jobj.getString("date_registration"));

                } catch (JSONException e2) {
                    e2.printStackTrace();
                }

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/display_company.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("display_company=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            Log.d("PAMBA",data);
            in.close();


            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean checkBranch()
    {
        int start = 0;
        for(int i=0; i<course.length(); i++)
        {
            String tmp;
            if(course.charAt(i) == ',' || i == (course.length()-1))
            {
                if(i == (course.length()-1))
                    tmp = course.substring(start, i+1);
                else
                    tmp = course.substring(start, i);
                Log.d("tmp",tmp);
                Log.d("branch", branch);
                if(tmp.compareTo(branch) == 0)
                    return true;
                start = i + 2;
            }
        }
        return false;
    }

    boolean checkCPI()
    {
        Float f = 0f;
        String str = "";
        for (int l = 0; l < eligibility.length();l++) {
            if (eligibility.charAt(l) == 'C' || eligibility.charAt(l) == ' ' || eligibility.charAt(l) == 'c') {
                f = Float.valueOf(str);
                break;
            } else
                str += eligibility.charAt(l);

        }

        if(Float.compare(cpi,f) < 0)
            return false;
        return true;
    }

    String getJson1() {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", reg);
            j.put("name", t[0].getText().toString());

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void register() {
        final String json = getJson1();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse1(json);
                return st;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                        if(response.compareTo("1") == 0)
                            Toast.makeText(showCompany.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        else if(response.compareTo("-1") == 0)
                            Toast.makeText(showCompany.this, "Already Registered", Toast.LENGTH_SHORT).show();
                        else if(response.compareTo("0") == 0)
                            Toast.makeText(showCompany.this, "Try Again", Toast.LENGTH_SHORT).show();


            }
        }.execute();
    }

    private String getServerResponse1(String json) {
        try {
            URL url = new URL("http://192.168.43.25/init/registerCompany.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("registerdata=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();

            JSONObject j = new JSONObject(data);

            return j.getString("status");

        } catch (IOException e) {
            Log.d("PP", "EX");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

}
