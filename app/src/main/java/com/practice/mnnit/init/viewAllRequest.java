package com.practice.mnnit.init;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class viewAllRequest extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //String[] c_name={"INDIA","AMERICA","PAKISTAN","CHINA","AUSTRALIA","All","fall",};
    String data;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterTPORequest adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Request> arrayList=new ArrayList<>();
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_request);

        data = "";
        getRequestData();

        for(;;)
            if(data.compareTo("") != 0)
                break;

        try {
            JSONArray j = new JSONArray(data);
            Request[] r = new Request[j.length()];
            for (int i = 0; i<j.length(); i++)
            {
                JSONObject jobj = j.getJSONObject(i);
                //Log.d("Performance" , jobj.getString("uid"));
                //Log.d("Performance" , jobj.getString("message"));
                //String s = jobj.getString("uid");
                Request tmp = new Request(jobj.getString("uid"), jobj.getString("message"), jobj.getString("reg"), jobj.getString("response"));
                r[i] = tmp;
            }


            for(int i = 0; i<j.length(); i++)
            {
                arrayList.add(r[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        b = (Button)findViewById(R.id.cardTpoRequestReply);
        toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);
        recyclerView =(RecyclerView)findViewById(R.id.recycleTpoRequest);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        adapter=new RecycleAdapterTPORequest(arrayList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(viewAllRequest.this);
        return  true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Request> newList=new ArrayList<>();
        for(Request country1 : arrayList)
        {
            String name=country1.getReg().toLowerCase();
            if(name.contains(newText))
            {
                newList.add(country1);
            }

        }
        adapter.setFilter(newList);
        return true;
    }

    void getRequestData()
    {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/getAllRequest.php");
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
}
