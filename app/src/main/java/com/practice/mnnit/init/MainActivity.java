package com.practice.mnnit.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Parameters p = new Parameters();
                if(p.getLoggedIn() == 0)
                {
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    if(p.getLoginPage()==1)
                    {
                        Intent i = new Intent(MainActivity.this, SelectPortal.class);
                        i.putExtra("reg",p.getLoginReg());
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(MainActivity.this, StudentPortal.class);
                        i.putExtra("reg",p.getLoginReg());
                        startActivity(i);
                        finish();
                    }
                }


            }
        }, SPLASH_TIME_OUT);


    }
}
