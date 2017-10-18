package com.practice.mnnit.init;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shivani gupta on 10/10/2017.
 */
public class RecycleAdapterStudentPlaced extends RecyclerView.Adapter<RecycleAdapterStudentPlaced.MyViewHolder> {
    ArrayList<Company> arrayList=new ArrayList<>();
    RecycleAdapterStudentPlaced(ArrayList<Company> arrayList)
    {
        this.arrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_student_placed,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.t1.setText(arrayList.get(position).getName());
        holder.t2.setText(arrayList.get(position).getPlaced());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2;
        public MyViewHolder(View itemView) {

            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.cardStudentPlacedName);
            t2=(TextView) itemView.findViewById(R.id.cardStudentPlacedList);
        }
    }
    public void setFilter(ArrayList<Company> newList)
    {
        arrayList=new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }
}