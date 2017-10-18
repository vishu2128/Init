package com.practice.mnnit.init;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class viewStats extends AppCompatActivity
{

    static String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        getStudentData();
        for(;;)
            if(data.compareTo("") != 0)
                break;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view_stats);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager_view_stats);
        viewPager.setAdapter(new TabsExamplePagerAdapter(getSupportFragmentManager()));


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_view_stats);
        tabLayout.setupWithViewPager(viewPager);

    }


    public static class TabsExamplePagerAdapter extends FragmentPagerAdapter
    {


        private static final int NUM_ITEMS = 2;

        public TabsExamplePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }


        @Override

        // For each tab different fragment is returned

        public Fragment getItem(int position)
        {

            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            switch (position)
            {

                case 0:
                {
                    PlacedStats fragobj = new PlacedStats();
                    fragobj.setArguments(bundle);
                    return fragobj;
                }


                case 1:
                {
                    PackStats fragobj1 = new PackStats();
                    fragobj1.setArguments(bundle);
                    return fragobj1;
                }

                default:
                    return null;

            }

        }


        @Override

        public int getCount()
        {

            return NUM_ITEMS;


        }


        @Override

        public CharSequence getPageTitle(int position)
        {
            if(position == 0)
                return "Placement Data";
            else
                return  "Package Data";
        }

    }

    void getStudentData()
    {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
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

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

}