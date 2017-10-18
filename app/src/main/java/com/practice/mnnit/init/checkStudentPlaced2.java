package com.practice.mnnit.init;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class checkStudentPlaced2 extends AppCompatActivity {

    private RelativeLayout r1;
    private EditText[] student_name = new EditText[50];
    private EditText[] reg_no = new EditText[50];
    private EditText[] branch = new EditText[50];
    private EditText[] ctc = new EditText[50];
    private Integer m = 0;
    private String compName;
    private Integer number;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_student_placed2);

        Toolbar tool = (Toolbar)findViewById(R.id.studPlaced2Toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Add Placed Student");

        Bundle extras = getIntent().getExtras();
        compName = extras.getString("compName");
        number = Integer.parseInt(extras.getString("number"));

        r1 = (RelativeLayout)findViewById(R.id.rl);

        for(int i = 0; i<number; i++)
        {
            addTextView(m,i+1);
            student_name[i] = addEditText(m, "Enter Student Name",0);
            reg_no[i] = addEditText(m, "Enter Registration Number",0);
            branch[i] = addEditText(m, "Enter Branch",0);
            ctc[i] = addEditText(m, "Enter Package (in lpa)",1);
            m+=10;
        }

        b1 = addButton(m+20);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number == 0)
                {
                    Intent i = new Intent(checkStudentPlaced2.this, checkStudentPlaced1.class);
                    startActivity(i);
                }
                else
                    sendDataToServer();
            }
        });
    }

    EditText addEditText(int margin, String hint,int type)
    {
        EditText et1 = new EditText(checkStudentPlaced2.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = margin;
        et1.setLayoutParams(params);

        et1.setHint(hint);
        et1.setEms(10);

        if(type==1)
        {
            Log.d("CHECKING","1");
            et1.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        r1.addView(et1);
        m+=80;
        return et1;
    }

    void addTextView(int margin, int num)
    {
        TextView t = new TextView(checkStudentPlaced2.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.MATCH_PARENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = margin;

        t.setLayoutParams(params);
        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        t.setText("Student " + String.valueOf(num));
        r1.addView(t);
        m+=70;
    }

    Button addButton(int margin)
    {
        Button b = new Button(checkStudentPlaced2.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

        params.topMargin = margin;
        b.setLayoutParams(params);

        if(number == 0)
            b.setText("Back");
        else
            b.setText("Submit");
        r1.addView(b);

        return b;
    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("company", compName);

            JSONArray jarr = new JSONArray();
            for(int i=0; i<number; i++)
            {
                JSONObject jobj = new JSONObject();
                jobj.put("name",student_name[i].getText().toString());
                jobj.put("reg_no",reg_no[i].getText().toString());
                jobj.put("branch",branch[i].getText().toString());
                jobj.put("package",ctc[i].getText().toString());
                jarr.put(i,jobj);
            }

            j.put("results",jarr);

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendDataToServer() {
        final String json = getJson();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                Log.d("PARAM",json);
                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.compareTo("1") == 0) {
                    Toast.makeText(checkStudentPlaced2.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(checkStudentPlaced2.this, checkUpdateStudentData.class);
                    startActivity(i);
                } else if (s.compareTo("0") == 0) {
                    Toast.makeText(checkStudentPlaced2.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            URL url = new URL("http://192.168.43.25/init/placed_data.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("placed_data=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();

            Log.d("HI", data);
            JSONObject jobj = new JSONObject(data);
            return jobj.getString("status");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
