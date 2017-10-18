package com.practice.mnnit.init;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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

public class Academic extends Fragment implements View.OnClickListener{

    //private static final String ARG_PARAM1 = "param1";
    private String s = null;
    private String showStatus = null;
    //private OnFragmentInteractionListener mListener;
    private EditText[] e = new EditText[10];
    private AutoCompleteTextView e1;
    private View view;
    private Button b;
    public Academic() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PERSONAL","1");
        view = inflater.inflate(R.layout.fragment_academic, container, false);
        Log.d("PERSONAL","2");

        e[0] = (EditText)view.findViewById(R.id.acadcourse);
        e1 = (AutoCompleteTextView) view.findViewById(R.id.acadbranch);
        e[2] = (EditText)view.findViewById(R.id.acadcpi);
        e[3] = (EditText)view.findViewById(R.id.acadten);
        e[4] = (EditText)view.findViewById(R.id.acad12);

        Log.d("PERSONAL","3");
        b = (Button)view.findViewById(R.id.acadEdit);
        Log.d("PERSONAL","4");

        if (getArguments() != null) {
            Log.d("PERSONAL","5");
            s = getArguments().getString("data");
            showStatus = getArguments().getString("status");
        }

        Log.d("PERSONAL","6" + s);

        try {
            Log.d("Personal", s);
            JSONObject j = new JSONObject(s);
            e[0].setText(j.getString("course"));
            e1.setText(j.getString("branch"));
            e[2].setText(j.getString("cpi"));
            e[3].setText(j.getString("ten"));
            e[4].setText(j.getString("twe"));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        String[] branchList = {"CSE", "ECE", "IT", "EE", "ME", "Civil"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, branchList);
        e1.setThreshold(0);
        e1.setAdapter(adapter);

        e1.setEnabled(false);


        for(int i = 0; i<5; i++)
            if(i!=1)
            e[i].setEnabled(false);

        e1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                e1.showDropDown();
                return false;
            }
        });


            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(b.getText().toString().compareTo("Edit") == 0)
                    {
                        String updatable = null;
                        try {
                            JSONObject j = new JSONObject(s);
                            updatable = j.getString("updatable");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        if(showStatus.compareTo("1") == 0 && updatable.compareTo("0") == 0)
                        {
                            Snackbar snackbar = Snackbar
                                    .make(view, "Last Date for Updating Details is Over", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }

                        else
                        {
                            e1.setEnabled(true);
                            for(int i = 0; i<5; i++)
                                if(i!=1)
                            e[i].setEnabled(true);

                            b.setText("Update");
                        }
                    }

                    else if(b.getText().toString().compareTo("Update") == 0)
                    {
                        e1.setEnabled(false);
                        for(int i = 0; i<5; i++)
                            if(i!=1)
                            e[i].setEnabled(false);
                        sendDataToServer();
                        b.setText("Edit");
                    }
                }
            });


        return view;
    }

    @Override
    public void onClick(View view) {

    }

    String getJson() {
        final JSONObject j = new JSONObject();

        try {
            j.put("course",e[0].getText().toString());
            j.put("branch",e1.getText().toString());
            j.put("cpi",e[2].getText().toString());
            j.put("ten",e[3].getText().toString());
            j.put("twe",e[4].getText().toString());
            JSONObject jobj = new JSONObject(s);
            j.put("reg_no",jobj.getString("reg_no"));
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

                String st = getServerResponse(json);
                return st;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s.compareTo("1")==0)
                {
                    Log.d("Updated Successfully","");
                }
                else
                    Log.d("Sorry","");

            }
        }.execute();
    }

    private String getServerResponse(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/update_student_data_academic.php");
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
}
