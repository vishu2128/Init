package com.practice.mnnit.init;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class viewRequest extends AppCompatActivity {

    String reg,data;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecycleAdapterStudentRequest adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Request> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        data = "";
        reg = getIntent().getStringExtra("reg");
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
                Request tmp = new Request(jobj.getString("uid"), jobj.getString("message"),jobj.getString("response"));
                r[i] = tmp;
            }


            for(int i = 0; i<j.length(); i++)
            {
                arrayList.add(r[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        toolbar=(Toolbar)findViewById(R.id.recycleToolbar);
        setSupportActionBar(toolbar);
        recyclerView =(RecyclerView)findViewById(R.id.recycleStudentRequest);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerRecyclerView(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //country country2 = arrayList.get(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(viewRequest.this);
                alert.setTitle("Reply From TPO"); //Set Alert dialog title here
                String s = arrayList.get(position).getResponse();
                if(s.length() == 0)
                alert.setMessage("Your Message Has Not been Replied Yet!!"); //Message here
                else
                alert.setMessage(s);
                // Set an EditText view to get user input
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();


            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        adapter=new RecycleAdapterStudentRequest(arrayList);
        recyclerView.setAdapter(adapter);
    }

    void getRequestData()
    {
        final JSONObject j = new JSONObject();
        String json = null;
        try {
            j.put("reg_no", reg);
            json = j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJson = json;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Parameters p = new Parameters();
                    URL url = new URL("http://"+p.ip+"/init/getStudentRequest.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream os = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(("sendReg=" + finalJson));

                    bw.flush();
                    bw.close();
                    os.close();


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
