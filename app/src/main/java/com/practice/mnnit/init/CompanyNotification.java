package com.practice.mnnit.init;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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

public class CompanyNotification extends Service {

    private String data,reg;
    private Integer prev;
    public final static String MY_ACTION = "MY_ACTION";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        reg = intent.getStringExtra("reg");
        getPrevious();

        return START_STICKY;
    }

    void createNotification()
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    Log.d("Thread RUNNING", "Yippee");
                    displayNotification();
                    Log.d("Previous", String.valueOf(prev));
                    try {
                        Intent intent = new Intent();
                        intent.setAction(MY_ACTION);

                        intent.putExtra("DATAPASSED", prev);
                        sendBroadcast(intent);
                        Thread.sleep(10000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    void displayNotification()
    {
        data = "";
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
        }.execute();

        for(;;)
        {
            Log.d("displayNotification",data);
            if(data.compareTo("")!=0)
                break;
        }

        Log.d("CHECKINGGGG",data);
        try {
            Log.d("NOTIFICATION","1");
            JSONArray comp = new JSONArray(data);
            Log.d("NOTIFICATION","2");

            if(comp.length() != prev)
            {
                Log.d("NOTIFICATION","3");
                Log.d("Lengths" , Integer.toString(comp.length()) + " " + Integer.toString(prev));

                for(int i = 0; i < (comp.length() - prev); i++)
                {

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_menu_manage)
                            .setContentTitle("Company Alert")
                            .setContentText(comp.getString(comp.length()-i-1) + " will be visiting our campus. Click for further details");

                    int mNotificationId = (int) System.currentTimeMillis();
                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                }
            }
            prev = comp.length();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getPrevious()
    {
        final String[] info = new String[1];
        info[0] = "";
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
                    URL url = new URL("http://"+p.ip+"/init/sendPrevious.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    OutputStream os = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(("sendPrevious=" + finalJson));

                    bw.flush();
                    bw.close();
                    os.close();


                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        info[0] += line;
                    }
                    in.close();
                    Log.d("HEY", info[0]);
                    JSONObject j = new JSONObject(info[0]);
                    prev = Integer.parseInt(j.getString("previous"));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                createNotification();
            }

        }.execute();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
