package com.practice.mnnit.init;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class StudentPortal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String reg_no, br, credits,cpi;
    private TextView name,reg,branch;
    private NavigationView navigationView;
    MyReceiver myReceiver;
    int prev;
    private Bitmap bitmap;
    private AdapterViewFlipper simpleAdapterViewFlipper;
    int[] companyImages = {R.drawable.google, R.drawable.directi, R.drawable.samsung, R.drawable.morgan, R.drawable.visa};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_portal);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Intent i = getIntent();
        reg_no = i.getStringExtra("reg");

        simpleAdapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapterViewFlipper); // get the reference of AdapterViewFlipper
// Custom Adapter for setting the data in Views
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), companyImages);
        simpleAdapterViewFlipper.setAdapter(customAdapter); // set adapter for AdapterViewFlipper
// set interval time for flipping between views
        simpleAdapterViewFlipper.setFlipInterval(3000);
// set auto start for flipping between views
        simpleAdapterViewFlipper.setAutoStart(true);

        getAllData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(StudentPortal.this);
                alert.setTitle("Message to TPO"); //Set Alert dialog title here
                alert.setMessage("Enter Your Message Here"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(StudentPortal.this);
                alert.setView(input);

                alert.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String srt = input.getEditableText().toString();
                        Log.d("CHECKKKK",srt);
                        sendMessage(srt);
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CompanyNotification.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        Intent service = new Intent(this, CompanyNotification.class);
        service.putExtra("reg",reg_no);
        startService(service);

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_portal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.studentLogOut) {
            storePrevious();
            Intent i = new Intent(StudentPortal.this, Login.class);
            startActivity(i);
            finish();
            return true;
        }

        if (id == R.id.studentChangePass) {
            Intent i = new Intent(StudentPortal.this, changePassword.class);
            i.putExtra("reg",reg_no);
            i.putExtra("ret","0");
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.viewProfile) {
            Intent i = new Intent(StudentPortal.this, viewProfile.class);
            i.putExtra("reg",reg_no);
            i.putExtra("status", "1");
            startActivity(i);

        } else if (id == R.id.viewRequest) {
            Intent i = new Intent(StudentPortal.this, viewRequest.class);
            i.putExtra("reg",reg_no);
            i.putExtra("branch", br);
            startActivity(i);
        } else if (id == R.id.viewStats) {
            Intent i = new Intent(StudentPortal.this, viewStats.class);
            startActivity(i);
        } else if (id == R.id.regComp) {
            Intent i = new Intent(StudentPortal.this, viewRegisteredCompanies.class);
            i.putExtra("reg",reg_no);
            i.putExtra("branch", br);
            startActivity(i);

        } else if (id == R.id.upComp) {
            Intent i = new Intent(StudentPortal.this, upcoming.class);
            i.putExtra("reg",reg_no);
            i.putExtra("branch", br);
            i.putExtra("credits", credits);
            i.putExtra("cpi",cpi);
            startActivity(i);
        } else if (id == R.id.viewPlaced) {
            Intent i = new Intent(StudentPortal.this, viewAllPlaced.class);
            startActivity(i);

        } else if (id == R.id.contact) {
            Intent i = new Intent(StudentPortal.this, contactUs.class);
            startActivity(i);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", reg_no);
            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getAllData() {
        final String json = getJson();
        //Log.d("PARAM", json);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                Log.d("JSONPORTAL", json);
                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);

                try {
                    View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_student_portal);

                    name = (TextView)navHeaderView.findViewById(R.id.textVw);
                    reg = (TextView)navHeaderView.findViewById(R.id.textView);
                    branch = (TextView)navHeaderView.findViewById(R.id.textView9);
                    ImageView iv = (ImageView)navHeaderView.findViewById(R.id.imageView);
                    iv.setImageBitmap(bitmap);

                    JSONObject jobj = new JSONObject(data);
                    name.setText(jobj.getString("name"));
                    reg.setText(jobj.getString("reg_no"));
                    branch.setText(jobj.getString("course") + " " + jobj.getString("branch"));
                    br = jobj.getString("branch");
                    credits = jobj.getString("tpo_credits");
                    cpi = jobj.getString("cpi");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/display_mainStudent.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("display_student=" + json));

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
            JSONObject j = new JSONObject(data);
            URL url1 = new URL(j.getString("photo"));
            bitmap = BitmapFactory.decodeStream(url1.openConnection().getInputStream());

            return data;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    String getJson1(String s) {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", reg_no);
            j.put("msg", s);

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessage(String s) {
        final String json = getJson1(s);
        //Log.d("PARAM", json);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse1(json);
                return st;
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);

                try {
                    JSONObject jobj = new JSONObject(data);
                    if((jobj.getString("status")).compareTo("1") == 0)
                    {
                        Toast.makeText(StudentPortal.this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(StudentPortal.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private String getServerResponse1(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/send_message.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("message_details=" + json));

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
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    void storePrevious()
    {
        String json = "";
        final JSONObject j = new JSONObject();
        try {
            j.put("prv", String.valueOf(prev));
            Log.d("Previous", String.valueOf(prev));
            j.put("reg",reg_no);
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
                    URL url = new URL("http://"+p.ip+"/init/storePrevious.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream os = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(("store_previous=" + finalJson));

                    bw.flush();
                    bw.close();
                    os.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String data = "", line;

                    while ((line = in.readLine()) != null) {
                        data+=line;
                    }
                    in.close();
                    Log.d("PREVIOUS","A" + data);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                stopService(new Intent(StudentPortal.this, CompanyNotification.class));
                Parameters p = new Parameters();
                p.setLoggedIn(0);
                Intent i = new Intent(StudentPortal.this, Login.class);
                startActivity(i);
            }
        }.execute();
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            int datapassed = arg1.getIntExtra("DATAPASSED", 0);
            prev = datapassed;
        }

    }
}
