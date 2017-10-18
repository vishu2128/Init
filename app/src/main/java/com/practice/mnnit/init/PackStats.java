package com.practice.mnnit.init;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PackStats extends Fragment {

    private String s = null;
    private String[] b = {"CSE", "ECE", "IT", "EE", "ME", "Civil"};
    private float[] avg =   {0, 0, 0, 0, 0, 0};
    private float[] mx = {0, 0, 0, 0, 0, 0};
    private Integer[] numStu = {0, 0, 0, 0, 0, 0};
    private View view;
    private BarChart barChart1;

    public PackStats() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pack_stats, container, false);

        Log.d("1","!");
        if (getArguments() != null) {
            Log.d("PERSONAL","5");
            s = getArguments().getString("data");
        }

        Log.d("HEYYYYYYYYYYYYYYY", s);

        try {
            JSONArray j = new JSONArray(s);
            for (int i = 0; i<j.length(); i++)
            {
                JSONObject jobj = j.getJSONObject(i);
                for(int k=0;k<6;k++)
                {
                    if((jobj.getString("branch").compareTo(b[k]) == 0) && (jobj.getString("placed").compareTo("1") == 0))
                    {
                        Log.d("CHECK", jobj.getString("name"));
                        numStu[k]++;
                        String str = "";
                        Float f = 0f;
                        String tmp = jobj.getString("ctc");
                        for (int l = 0; l < tmp.length();l++) {
                            if (tmp.charAt(l) == 'l' || tmp.charAt(l) == 'L') {
                                Log.d("CHCRK", str);
                                f = Float.valueOf(str);
                                break;
                            } else
                                str += tmp.charAt(l);

                        }
                        avg[k] += f;
                        mx[k] = Math.max(mx[k], f);
                    }
                }

            }

            for (int i=0; i<6; i++)
            {
                if(numStu[i] == 0)
                    avg[i] = 0f;
                else
                    avg[i] /= numStu[i];

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0; i<6; i++)
            Log.d("CHECK", String.valueOf(avg[i]) + " " + String.valueOf(mx[i]));

        barChart1 = view.findViewById(R.id.barChart);
        ArrayList<BarEntry> e1= new ArrayList<>();
        for(int i=0;i<6;i++){
            e1.add(new BarEntry(avg[i], i));}

        ArrayList<BarEntry> e2= new ArrayList<>();
        for(int i=0;i<6;i++) {
            e2.add(new BarEntry(mx[i], i));}

        ArrayList<String> label = new ArrayList<String>();
        label.add("CSE");
        label.add("ECE");
        label.add("IT");
        label.add("EE");
        label.add("ME");
        label.add("Civil");

        BarDataSet dataset1 = new BarDataSet(e1, "highest package");
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarDataSet dataset2 = new BarDataSet(e2, "average package");
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);

        BarData bar = new BarData(label, dataSets);
        barChart1.setData(bar);

        barChart1.setDescription("Placement Stats");
        barChart1.animateY(5000);


        return view;
    }

}
