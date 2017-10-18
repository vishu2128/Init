package com.practice.mnnit.init;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class checkUpdateComp extends AppCompatActivity {

    private String company = null;
    private static EditText[] e = new EditText[15];
    private EditText e1;
    private Button b, b1, d1,d2,d3;
    private TextView t1;
    private static final int DATE_DIALOG_ID = 0;
    private int year, month, day;
    private DatePickerDialogFragment mDatePickerDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update_comp);

        Toolbar tool = (Toolbar)findViewById(R.id.checkUpdateCompToolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Update Company");

        company = getIntent().getStringExtra("company");
        e1 = (EditText) findViewById(R.id.editTxt29);
        e[0] = (EditText)findViewById(R.id.editTxt21);
        e[1] = (EditText)findViewById(R.id.editTxt22);
        e[2] = (EditText)findViewById(R.id.editTxt23);
        e[3] = (EditText)findViewById(R.id.editTxt24);
        e[4] = (EditText)findViewById(R.id.editTxt25);
        e[5] = (EditText)findViewById(R.id.editTxt26);
        e[6] = (EditText)findViewById(R.id.editTxt27);
        e[7] = (EditText)findViewById(R.id.editTxt28);
        e[8] = (EditText)findViewById(R.id.editTxt36);

        t1 = (TextView)findViewById(R.id.textView10);
        b = (Button)findViewById(R.id.buton3);
        b1 = (Button)findViewById(R.id.buton14);
        d1 = (Button)findViewById(R.id.button14);
        d2 = (Button)findViewById(R.id.button27);
        d3 = (Button)findViewById(R.id.button28);

        mDatePickerDialogFragment = new DatePickerDialogFragment();

        t1.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.VISIBLE);

        for(int i = 0; i<9; i++)
            e[i].setEnabled(false);
        e1.setEnabled(false);

        e1.setText(company);
        getDataFromServer();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(b.getText().toString().compareTo("Edit") == 0)
                {
                    e1.setEnabled(true);
                    for(int i = 0; i<9; i++)
                        e[i].setEnabled(true);
                    b.setText("Update");

                }

                else if(b.getText().toString().compareTo("Update") == 0) {
                    e1.setEnabled(false);
                    for (int i = 0; i < 9; i++)
                        e[i].setEnabled(false);
                    sendDataToServer();
                    b.setText("Edit");
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPlacedData();
            }
        });

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_Register);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_Arrival);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_Test);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

    }


    private void getDataFromServer() {
        String json = null;
        final JSONObject j = new JSONObject();
        try {
            j.put("name", company);

            json =  j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJson = json;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse(finalJson);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jobj = new JSONObject(s);
                    e[0].setText(jobj.getString("courses"));
                    e[1].setText(jobj.getString("eligibility"));
                    e[2].setText(jobj.getString("designation"));
                    e[3].setText(jobj.getString("location"));
                    e[4].setText(jobj.getString("lnk"));
                    e[5].setText(jobj.getString("date_test"));
                    e[7].setText(jobj.getString("date_arrival"));
                    e[8].setText(jobj.getString("date_registration"));
                    e[6].setText(jobj.getString("ctc"));

                } catch (JSONException e2) {
                    e2.printStackTrace();
                }

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/display_company.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("display_company=" + json));
            Log.d("PAMBA",json);

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            Log.d("PAMBA",data);
            in.close();


            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("name", e1.getText().toString());
            j.put("courses", e[0].getText().toString());
            j.put("eligibility", e[1].getText().toString());
            j.put("designation", e[2].getText().toString());
            j.put("location", e[3].getText().toString());
            j.put("lnk", e[4].getText().toString());
            j.put("date_test", e[5].getText().toString());
            j.put("ctc", e[6].getText().toString());
            j.put("date_arrival", e[7].getText().toString());
            j.put("date_registration", e[8].getText().toString());

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

                String st = getServerResponse1(json);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s.compareTo("1")==0)
                {
                    Toast.makeText(checkUpdateComp.this, "Updated Succesfully",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(checkUpdateComp.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }

    private String getServerResponse1(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/update_comp_data.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("updatedata=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();

            Log.d("CHECK",data);
            JSONObject jobj = new JSONObject(data);
            return jobj.getString("status");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    private void getPlacedData() {
        String json = null;
        final JSONObject j = new JSONObject();
        try {
            j.put("name", company);

            json =  j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalJson = json;
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse2(finalJson);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                String placed = "";
                JSONArray j = null;
                try {
                    j = new JSONArray(s);

                    if(j.length() == 0)
                    {
                        b1.setVisibility(View.INVISIBLE);
                        t1.setText("No one Placed Yet");
                        t1.setVisibility(View.VISIBLE);
                        return;
                    }
                    for(int i = 0; i<j.length(); i++)
                    {
                        JSONObject jobj = j.getJSONObject(i);
                        placed += jobj.getString("name") + " (" + jobj.getString("reg") + ")";

                        if(i!=(j.length()-1))
                            placed += "\n ";
                        else
                            placed += ".";
                    }

                    b1.setVisibility(View.INVISIBLE);
                    t1.setText(placed);
                    t1.setVisibility(View.VISIBLE);

                } catch (JSONException e2) {
                    e2.printStackTrace();
                }

            }
        }.execute();
    }

    private String getServerResponse2(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/getPlacedCompanyWise.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("sendData=" + json));
            Log.d("PAMBA",json);

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            Log.d("PAMBA",data);
            in.close();


            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        public static final int FLAG_Register = 0;
        public static final int FLAG_Arrival = 1;
        public static final int FLAG_Test = 2;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (flag == FLAG_Register) {
                e[8].setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_Arrival) {
                e[7].setText(format.format(calendar.getTime()));
            }
            else if (flag == FLAG_Test) {
                e[6].setText(format.format(calendar.getTime()));
            }
        }

    }
}
