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

public class Personal extends Fragment implements View.OnClickListener {

    //private static final String ARG_PARAM1 = "param1";
    private String s = null;
    //private OnFragmentInteractionListener mListener;
    private EditText[] e = new EditText[10];
    private String showStatus = null;
    private View view;
    private Button b;
    private AutoCompleteTextView e3;


    public Personal() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PERSONAL","1");
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        Log.d("PERSONAL","2");

        e[0] = (EditText)view.findViewById(R.id.name);
        e[1] = (EditText)view.findViewById(R.id.reg);
        e[2] = (EditText)view.findViewById(R.id.dob);
        e3 = (AutoCompleteTextView) view.findViewById(R.id.gen);
        e[4] = (EditText)view.findViewById(R.id.hostel);
        e[5] = (EditText)view.findViewById(R.id.address);
        e[6] = (EditText)view.findViewById(R.id.city);
        e[7] = (EditText)view.findViewById(R.id.state);
        e[8] = (EditText)view.findViewById(R.id.phone);
        e[9] = (EditText)view.findViewById(R.id.email);

        Log.d("PERSONAL","3");
        b = (Button)view.findViewById(R.id.Edit);
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
            e[0].setText(j.getString("name"));
            e[1].setText(j.getString("reg_no"));
            e[2].setText(j.getString("dob"));
            e3.setText(j.getString("gender"));
            e[4].setText(j.getString("hostel"));
            e[5].setText(j.getString("address"));
            e[6].setText(j.getString("city"));
            e[7].setText(j.getString("state"));
            e[8].setText(j.getString("contact_no"));
            e[9].setText(j.getString("email"));

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        String[] gender = {"Male", "Female", "Other"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, gender);
        e3.setThreshold(0);
        e3.setAdapter(adapter);

        e3.setEnabled(false);


        for(int i = 0; i<10; i++)
            if(i!=3)
            e[i].setEnabled(false);

        e3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                e3.showDropDown();
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
                            //Log.d("UPDATABLE", updatable);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        //Log.d("UPDATABLE", updatable);
                        if(showStatus.compareTo("1") == 0 && updatable.compareTo("0") == 0)
                        {
                            Snackbar snackbar = Snackbar
                                    .make(view, "Last Date for Updating Details is Over", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }

                        else
                        {
                            for(int i = 0; i<10; i++)
                            {
                                if(i==1 || i==3)
                                    continue;
                                e[i].setEnabled(true);
                            }
                            e3.setEnabled(true);
                            b.setText("Update");
                        }
                    }

                    else if(b.getText().toString().compareTo("Update") == 0)
                    {
                        e3.setEnabled(false);
                        for(int i = 0; i<10; i++)
                            if(i!=3)
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
            j.put("name",e[0].getText().toString());
            j.put("reg_no",e[1].getText().toString());
            j.put("dob",e[2].getText().toString());
            j.put("gender",e3.getText().toString());
            j.put("hostel",e[4].getText().toString());
            j.put("address",e[5].getText().toString());
            j.put("city",e[6].getText().toString());
            j.put("state",e[7].getText().toString());
            j.put("phone",e[8].getText().toString());
            j.put("email",e[9].getText().toString());

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
            URL url = new URL("http://"+p.ip+"/init/update_student_data.php");
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
