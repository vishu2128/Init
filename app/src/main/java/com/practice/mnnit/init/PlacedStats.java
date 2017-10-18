package com.practice.mnnit.init;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlacedStats extends Fragment implements OnChartValueSelectedListener, View.OnClickListener{

    private String s = null;
    private String[] b = {"CSE", "ECE", "IT", "EE", "ME", "Civil"};
    private Integer[] placed =   {0, 0, 0, 0, 0, 0};
    private Integer total = 0;
    private double show[] = new double[6];
    private View view;

    public PlacedStats() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_placed_stats, container, false);

        Log.d("1","!");
        if (getArguments() != null) {
            Log.d("PERSONAL","5");
            s = getArguments().getString("data");
        }

        try {
            JSONArray j = new JSONArray(s);
            for (int i = 0; i<j.length(); i++)
            {
                JSONObject jobj = j.getJSONObject(i);
                for(int k=0;k<6;k++)
                {
                    if((jobj.getString("branch").compareTo(b[k]) == 0) && (jobj.getString("placed").compareTo("1") == 0))
                        placed[k]++;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i<6; i++)
            total+=placed[i];

        for(int i=0; i<6; i++)
            show[i] = (placed[i] * 1.0)/total;

        PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        for(int i=0; i<6; i++)
            yvalues.add(new Entry((float) show[i], i));

        PieDataSet dataSet = new PieDataSet(yvalues, "Branches");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("CSE");
        xVals.add("ECE");
        xVals.add("IT");
        xVals.add("EE");
        xVals.add("ME");
        xVals.add("Civil");

        PieData chart = new PieData(xVals, dataSet);
        chart.setValueFormatter(new PercentFormatter());
        pieChart.setData(chart);
        pieChart.setDescription("Branch-Wise Percentage Placement");
        pieChart.setDrawHoleEnabled(false);
        //pieChart.setTransparentCircleRadius(58f);

        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        chart.setValueTextSize(13f);
        chart.setValueTextColor(Color.DKGRAY);

        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateXY(1400, 1400);

        return view;
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View view) {

    }
}
