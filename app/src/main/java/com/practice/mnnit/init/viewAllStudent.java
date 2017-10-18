package com.practice.mnnit.init;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class viewAllStudent extends AppCompatActivity implements SearchView.OnQueryTextListener {

    String data;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterTPOStudent adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Student> arrayList=new ArrayList<>();
    Bitmap[] check = new Bitmap[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_student);

        data = "";
        getStudentData();

        for(;;)
            if(data.compareTo("") != 0)
                break;

        toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);
        recyclerView =(RecyclerView)findViewById(R.id.recycleTpoStudent);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

                Intent i = new Intent(viewAllStudent.this, viewProfile.class);
                i.putExtra("reg",arrayList.get(position).getReg_no());
                Log.d("FINAL",arrayList.get(position).getReg_no());
                i.putExtra("status", "0");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(viewAllStudent.this);
        return  true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Student> newList=new ArrayList<>();
        for(Student country1 : arrayList)
        {
            String name=country1.getReg_no().toLowerCase();
            if(name.contains(newText))
            {
                newList.add(country1);
            }

        }
        adapter.setFilter(newList);
        return true;
    }

    void getStudentData()
    {
        new AsyncTask<Void, Void, Student[]>() {

            @Override
            protected Student[] doInBackground(Void... voids) {
                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/getAllStudent.php");
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

                    JSONArray j = new JSONArray(data);
                    Student[] stu = new Student[j.length()];

                    for (int i = 0; i<j.length(); i++)
                    {
                        Bitmap b;
                        JSONObject jobj = j.getJSONObject(i);
                        URL url1 = new URL(jobj.getString("photo"));
                        b = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
                        Student tmp = new Student(jobj.getString("name"),
                                jobj.getString("reg"),
                                jobj.getString("course"),
                                jobj.getString("branch"),
                                jobj.getString("email"),
                                jobj.getString("phone"),
                                jobj.getString("credits"),
                                jobj.getString("placed"),
                                jobj.getString("company"),
                                jobj.getString("ctc"),
                                b);
                        stu[i] = tmp;
                        Log.d("CHJJJJJ", String.valueOf(i));
                    }
                    return stu;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Student[] s) {
                super.onPostExecute(s);

                for(int i = 0; i< s.length; i++)
                {
                    Log.d("CHECKINGGGGGG", s[i].getName());
                    arrayList.add(s[i]);
                }

                adapter=new RecycleAdapterTPOStudent(arrayList);
                recyclerView.setAdapter(adapter);

            }
        }.execute();
    }
}
