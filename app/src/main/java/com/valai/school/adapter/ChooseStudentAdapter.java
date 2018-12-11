package com.valai.school.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.valai.school.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseStudentAdapter extends RecyclerView.Adapter<ChooseStudentAdapter.MyViewHolder> {

    private Context ctx;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.student_name)
        TextView student_name;

        @BindView(R.id.student_class)
        TextView student_class;

        @BindView(R.id.student_image)
        ImageView student_image;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ChooseStudentAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_student, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.student_name.setText("Amit");
        holder.student_class.setText("Class 8");
        Log.e("pos", "" + position);
        if (position == -1) {
            Log.e("pos1", "" + position);
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}