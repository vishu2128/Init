package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class viewAllCompany extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String data;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterTPOCompany adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Company> arrayList=new ArrayList<>();
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_company);

        data = "";
        getCompanies();

        for(;;)
            if(data.compareTo("") != 0)
                break;

        try {
            JSONArray j = new JSONArray(data);

            if(j.length() == 0)
            {
                Company[] r = new Company[1];
                Company tmp  = new Company("No Companies Yet");
                r[0] = tmp;

            }
            else
            {
                Company[] r = new Company[j.length()];
                for (int i = 0; i<j.length(); i++)
                {
                    Company tmp = new Company(j.getString(i));
                    r[i] = tmp;
                }

                for(int i = 0; i<j.length(); i++)
                {
                    arrayList.add(r[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);
        recyclerView =(RecyclerView)findViewById(R.id.recycleTpoCompany);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(viewAllCompany.this, checkUpdateComp.class);
                i.putExtra("company",arrayList.get(position).getName());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        adapter=new RecycleAdapterTPOCompany(arrayList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(viewAllCompany.this);
        return  true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Company> newList=new ArrayList<>();
        for(Company country1 : arrayList)
        {
            String name=country1.getName().toLowerCase();
            if(name.contains(newText))
            {
                newList.add(country1);
            }

        }
        adapter.setFilter(newList);
        return true;
    }

    private void getCompanies() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/company_names.php");
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
                } catch (IOException e) {
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


}
