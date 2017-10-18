package com.practice.mnnit.init;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shivani gupta on 10/10/2017.
 */
public class RecycleAdapterTPOStudent extends RecyclerView.Adapter<RecycleAdapterTPOStudent.MyViewHolder> {
    ArrayList<Student> arrayList=new ArrayList<>();
    RecycleAdapterTPOStudent(ArrayList<Student> arrayList)
    {
        this.arrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_tpo_student,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.t1.setText(arrayList.get(position).getName());
        holder.t2.setText(arrayList.get(position).getReg_no());
        holder.t3.setText(arrayList.get(position).getCourse() + " " + arrayList.get(position).getBranch());
        holder.t4.setText(arrayList.get(position).getPhone());
        holder.t5.setText(arrayList.get(position).getEmail());

        if(arrayList.get(position).getPlaced().compareTo("0") == 0)
            holder.t6.setText("Not Placed");
        else
            holder.t6.setText("Placed At " + arrayList.get(position).getCompany() + " (" + arrayList.get(position).getCtc() + ")");

        holder.t5.setText(arrayList.get(position).getTpoCredits());
        holder.iv123.setImageBitmap(arrayList.get(position).getPic());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4,t5,t6,t7;
        ImageView iv123;
        public MyViewHolder(View itemView) {

            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.cardTpoStudentName);
            t2=(TextView) itemView.findViewById(R.id.cardTpoStudentReg);
            t3=(TextView) itemView.findViewById(R.id.cardTpoStudentCourse);
            t4=(TextView) itemView.findViewById(R.id.cardTpoStudentPhone);
            t5=(TextView) itemView.findViewById(R.id.cardTpoStudentEmail);
            t6=(TextView) itemView.findViewById(R.id.cardTpoStudentPlaced);
            t7=(TextView) itemView.findViewById(R.id.cardTpoStudentCredits);
            iv123 = (ImageView)itemView.findViewById(R.id.cardTpoStudentImage);
        }
    }
    public void setFilter(ArrayList<Student> newList)
    {
        arrayList=new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }

}