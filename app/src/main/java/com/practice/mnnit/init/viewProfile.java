package com.practice.mnnit.init;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
import java.util.List;

public class viewProfile extends AppCompatActivity {

    private String reg_no = null;
    private String showStatus = null;
    static private String data = "";
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent i = getIntent();
        reg_no = i.getStringExtra("reg");
        Log.d("FINAL1",reg_no);
        showStatus = i.getStringExtra("status");
        data = "";
        getDataFromServer();

        for(;;)
            if(data.compareTo("")!=0)
                break;

        RequestRunTimePermission();
        // Setup Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Log.d("PARAM","2");
        viewPager = (ViewPager) findViewById(R.id.pager_view);
        setupViewPager(viewPager);

        Log.d("PARAM","3");
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_view);
        tabLayout.setupWithViewPager(viewPager);
        Log.d("PARAM","4");
    }

    public void RequestRunTimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(viewProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions(viewProfile.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {
        switch (RC) {
            case 1:
                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Log.d("HEYYYYY",data);
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putSerializable("status", showStatus);

        Personal fragobj = new Personal();
        fragobj.setArguments(bundle);
        adapter.addFragment(fragobj, "PERSONAL");

        Academic fragobj1 = new Academic();
        fragobj1.setArguments(bundle);
        adapter.addFragment(fragobj1, "ACADEMIC");

        PhotoAndResume fragobj2 = new PhotoAndResume();
        fragobj2.setArguments(bundle);
        adapter.addFragment(fragobj2, "PHOTO/RESUME");
        viewPager.setAdapter(adapter);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        // As we are implementing two tabs
        private static final int NUM_ITEMS = 3;
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        // For each tab different fragment is returned
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void getDataFromServer() {
        String json = null;
        final JSONObject j = new JSONObject();
        try {
            Log.d("FINAL4",reg_no);
            j.put("reg_no", reg_no);
            json =  j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJson = json;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                Log.d("FINAL2",finalJson);
                String st = getServerResponse(finalJson);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/display_student.php");
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
            String line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            Log.d("PAMBAviewProfile",data);
            in.close();

            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
