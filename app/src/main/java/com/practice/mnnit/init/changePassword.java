package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class changePassword extends AppCompatActivity {

    private EditText e1,e2;
    private TextView t;
    private Button b;
    private String reg = null;
    private String ret = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar tool = (Toolbar)findViewById(R.id.changePasswordToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Change Password");

        reg = getIntent().getStringExtra("reg");
        ret = getIntent().getStringExtra("ret");

        e1 = (EditText)findViewById(R.id.editText29);
        e2 = (EditText)findViewById(R.id.editText30);
        b = (Button)findViewById(R.id.button29);
        t = (TextView)findViewById(R.id.textView12);
        e1.setError("Enter Your Password!");

        e1.addTextChangedListener(mTextEditorWatcher);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().length() == 0 || e2.getText().toString().length() == 0)
                    Toast.makeText(changePassword.this, "Enter Both Fields", Toast.LENGTH_SHORT).show();
                else if(e1.getText().toString().compareTo(e2.getText().toString()) != 0)
                {
                    Toast.makeText(changePassword.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    e1.setText("");
                    e2.setText("");
                }
                else
                    sendPassword();
            }
        });

    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            t.setText("Password Strength: Not Entered");
            e1.setError("Enter Your Password!");
            t.setTextColor(0xffff0000);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        public void afterTextChanged(Editable s)
        {
            if(s.length()==0)
            {
                t.setText("Password Strength: Not Entered");
                t.setTextColor(0xffff0000);
                e1.setError("Enter Your Password!");
            }
            else if(s.length()<4)
            {
                t.setText("Password Strength: WEAK");
                t.setTextColor(0xffff0000);
            }
            else if(s.length()<6)
            {
                t.setText("Password Strength: MEDIUM");
                t.setTextColor(0xffffff00);
            }
            else
            {
                t.setText("Password Strength: STRONG");
                t.setTextColor(0xff00ff00);
            }
        }
    };

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("reg_no", reg);
            j.put("password", e1.getText().toString());

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPassword() {
        final String json = getJson();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);

                String s = null;
                try {
                    JSONObject jobj = new JSONObject(response);
                    s = jobj.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (s.compareTo("1") == 0) {
                    Toast.makeText(changePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();

                    Intent i;
                    if(ret.compareTo("1") == 0)
                        i = new Intent(changePassword.this, TPOportal.class);
                    else
                        i = new Intent(changePassword.this, StudentPortal.class);

                    startActivity(i);
                    finish();
                } else if (s.compareTo("0") == 0) {
                    Toast.makeText(changePassword.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            URL url = new URL("http://192.168.43.25/init/changePassword.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("sendData=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();

            Log.d("DTA", data);

            return data;

        } catch (IOException e) {
            Log.d("PP", "EX");
            e.printStackTrace();
        }


        return null;
    }
}
