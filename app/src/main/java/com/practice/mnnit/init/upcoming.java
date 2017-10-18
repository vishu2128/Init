package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class upcoming extends AppCompatActivity {

    String data;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterStudentUpcoming adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Company> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        data = "";
        getCompanyData();

        for(;;)
            if(data.compareTo("") != 0)
                break;

        try {
            JSONArray j = new JSONArray(data);
            int num = 0;
            for(int i=0; i<j.length(); i++)
            {
                JSONObject jobj = j.getJSONObject(i);
                if(dateCheck(jobj.getString("date")) == 1)
                {
                    num++;
                }
            }
            Company[] c = new Company[num];
            int k = 0;
            for (int i = 0; i<j.length(); i++)
            {
                JSONObject jobj = j.getJSONObject(i);
                if(dateCheck(jobj.getString("date")) == 1)
                {
                    Company tmp = new Company(jobj.getString("name"), jobj.getString("date"));
                    Log.d("CHECKKKK",String.valueOf(k));
                    c[k] = tmp; k++;
                }
            }


            for(int i = 0; i<num; i++)
            {
                arrayList.add(c[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


            toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);

        recyclerView =(RecyclerView)findViewById(R.id.recycleStudentUpcoming);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(upcoming.this, showCompany.class);
                Company cmp = arrayList.get(position);
                i.putExtra("name", cmp.getName());
                i.putExtra("reg", getIntent().getStringExtra("reg"));
                i.putExtra("branch", getIntent().getStringExtra("branch"));
                i.putExtra("status", "1");
                i.putExtra("credits", getIntent().getStringExtra("credits"));
                i.putExtra("cpi",getIntent().getStringExtra("cpi"));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        adapter=new RecycleAdapterStudentUpcoming(arrayList);
        recyclerView.setAdapter(adapter);
    }

    void getCompanyData()
    {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/upcoming.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        data += line;
                    }
                    in.close();
                    Log.d("HEY", data);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    int dateCheck(String s)
    {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = mdformat.format(calendar.getTime());

            Log.d("DATECHECK", strDate);
            Log.d("DATECHECK1", s);
            DateFormat formatter = mdformat;
            Date d1 = (Date)formatter.parse(s);
            Date today = (Date)formatter.parse(strDate);

            if(today.after(d1) == false)
            {
                Log.d("DATECHECK", "1");
                return 1;
            }
            else
                return 0;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
