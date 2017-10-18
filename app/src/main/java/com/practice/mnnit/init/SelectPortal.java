package com.practice.mnnit.init;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SelectPortal extends AppCompatActivity {

    private Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_portal);

        Toolbar tool = (Toolbar)findViewById(R.id.selectActivityToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Select Portal");


        b1 = (Button)findViewById(R.id.button18);
        b2 = (Button)findViewById(R.id.button19);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectPortal.this, TPOportal.class);
                i.putExtra("reg", getIntent().getStringExtra("reg"));
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectPortal.this, StudentPortal.class);
                i.putExtra("reg", getIntent().getStringExtra("reg"));
                startActivity(i);
            }
        });

    }
}
