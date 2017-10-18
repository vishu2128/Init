package com.practice.mnnit.init;

/**
 * Created by Shivani gupta on 10/12/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hp on 3/17/2016.
 */
public class RecycleAdapterStudentContact extends RecyclerView.Adapter<RecycleAdapterStudentContact.MyViewHolder> {

    ArrayList<Student> arrayList=new ArrayList<>();
    Context context;

    RecycleAdapterStudentContact(ArrayList<Student> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_student_contact,parent,false);
        return new MyViewHolder((view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.c_name.setText(arrayList.get(position).getName());
        holder.c_registration.setText(arrayList.get(position).getReg_no());
        holder.c_contact.setText(arrayList.get(position).getPhone());
        holder.c_course.setText(arrayList.get(position).getCourse());
        holder.c_branch.setText(arrayList.get(position).getBranch());
        holder.c_email.setText(arrayList.get(position).getEmail());

        holder.c_flag.setImageResource(arrayList.get(position).getTpoImage());

        holder.c_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("tell:"));
                v.getContext().startActivity(intent);
            }
        });
        holder.c_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_intent=new Intent(Intent.ACTION_DIAL);
                email_intent.setType("plain/text");
                v.getContext().startActivity(email_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView c_name;
        TextView c_registration,c_contact,c_course,c_branch,c_email;
        ImageView c_flag;
        public MyViewHolder(View itemView) {

            super(itemView);
            c_name=(TextView) itemView.findViewById(R.id.tpo_name);
            c_registration=(TextView) itemView.findViewById(R.id.tpo_registration);
            c_contact=(TextView) itemView.findViewById(R.id.tpo_contact);
            c_email=(TextView) itemView.findViewById(R.id.tpo_email);
            c_branch=(TextView) itemView.findViewById(R.id.tpo_branch);
            c_course=(TextView) itemView.findViewById(R.id.tpo_course);
            c_flag=(ImageView)itemView.findViewById(R.id.img_android);
        }
    }

    public void setFilter(ArrayList<Student> newList)
    {
        arrayList=new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }

}