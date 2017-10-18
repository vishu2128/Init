package com.practice.mnnit.init;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * Created by Shivani gupta on 10/10/2017.
 */
public class RecycleAdapterTPORequest extends RecyclerView.Adapter<RecycleAdapterTPORequest.MyViewHolder> {
    ArrayList<Request> arrayList=new ArrayList<>();
    RecycleAdapterTPORequest(ArrayList<Request> arrayList)
    {
        this.arrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_tpo_request,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.t1.setText(arrayList.get(position).getUID());
        holder.t2.setText(arrayList.get(position).getReg());
        holder.t3.setText(arrayList.get(position).getMessage());

        holder.t2.setClickable(true);

        holder.t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), viewProfile.class);
                i.putExtra("reg",arrayList.get(position).getReg());
                i.putExtra("status","0");
                view.getContext().startActivity(i);
            }
        });

        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Respond To Request");
                alert.setMessage(arrayList.get(position).getMessage());

                // Set an EditText view to get user input
                final EditText input = new EditText(view.getContext());
                alert.setView(input);

                alert.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String srt = input.getEditableText().toString();
                        Log.d("CHECKKKK",srt);
                        sendMessage(srt, arrayList.get(position).getUID());
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        Button b;
        public MyViewHolder(View itemView) {

            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.cardTpoRequestUID);
            t2=(TextView) itemView.findViewById(R.id.cardTpoRequestReg);
            t3=(TextView) itemView.findViewById(R.id.cardTpoRequestMessage);
            b = (Button)itemView.findViewById(R.id.cardTpoRequestReply);
        }
    }
    public void setFilter(ArrayList<Request> newList)
    {
        arrayList=new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }

    String getJson1(String s, String uid) {
        final JSONObject j = new JSONObject();

        try {
            j.put("uid", uid);
            j.put("msg", s);

            return j.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessage(String s, String uid) {
        final String json = getJson1(s,uid);
        //Log.d("PARAM", json);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                String st = getServerResponse1(json);
                return st;
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);

                try {
                    JSONObject jobj = new JSONObject(data);
                    if((jobj.getString("status")).compareTo("1") == 0)
                    {}
                    else
                    {}

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private String getServerResponse1(String json) {
        try {
            Parameters p = new Parameters();
            URL url = new URL("http://"+p.ip+"/init/send_response.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(("message_details=" + json));

            bw.flush();
            bw.close();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String data = "", line;

            while ((line = in.readLine()) != null) {
                data+=line;
            }
            in.close();
            Log.d("PARAM2",data);
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}