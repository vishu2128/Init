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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class viewRegisteredCompanies extends AppCompatActivity {

    String data;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterStudentUpcoming adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Company> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_companies);

        data = "";
        getCompanyData();

        for(;;)
            if(data.compareTo("") != 0)
                break;

        Log.d("Register", data);

        try {
            JSONArray j = new JSONArray(data);
            Company[] c = new Company[j.length()];
            for (int i = 0; i<j.length(); i++)
            {
                Company tmp = new Company(j.getString(i));
                c[i] = tmp;
            }


            for(int i = 0; i<j.length(); i++)
            {
                arrayList.add(c[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);

        recyclerView =(RecyclerView)findViewById(R.id.recycleStudentRegistered);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(viewRegisteredCompanies.this, showCompany.class);
                Company cmp = arrayList.get(position);
                i.putExtra("name", cmp.getName());
                i.putExtra("reg", getIntent().getStringExtra("reg"));
                i.putExtra("branch", getIntent().getStringExtra("branch"));
                i.putExtra("status", "0");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        adapter=new RecycleAdapterStudentUpcoming(arrayList);
        recyclerView.setAdapter(adapter);
    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", getIntent().getStringExtra("reg"));
            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getCompanyData() {
        final String json = getJson();
        //Log.d("PARAM", json);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Log.d("JSONPORTAL", json);
                getServerResponse(json);
                Log.d("Register", "1");
                return null;
            }
        }.execute();
    }

    private Void getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/registeredCompanies.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("register_data=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();
            Log.d("PARAM2",data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
