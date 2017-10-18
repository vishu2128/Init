package com.practice.mnnit.init;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shivani gupta on 10/5/2017.
 */
public class RecycleAdapterStudentUpcoming extends RecyclerView.Adapter<RecycleAdapterStudentUpcoming.MyViewHolder> {
    ArrayList<Company> arrayList=new ArrayList<>();
    RecycleAdapterStudentUpcoming(ArrayList<Company> arrayList)
    {
        this.arrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_student_upcoming,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.t1.setText(arrayList.get(position).getName());
        holder.t2.setText(arrayList.get(position).getDateRegistration());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2;
        public MyViewHolder(View itemView) {

            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.cardStudentNameCompany);
            t2=(TextView) itemView.findViewById(R.id.cardStudentRegisterCompany);
        }
    }
}