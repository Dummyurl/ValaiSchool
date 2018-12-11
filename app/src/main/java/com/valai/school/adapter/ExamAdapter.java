package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.MyViewHolder> {


    private Context ctx;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_exam_name)
        TextView text_exam_name;

        @BindView(R.id.text_sub_name)
        TextView text_sub_name;

        @BindView(R.id.text_exam_date)
        TextView text_exam_date;

        @BindView(R.id.text_start_time)
        TextView text_start_time;

        @BindView(R.id.text_end_time)
        TextView text_end_time;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ExamAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_exam_name.setText("Exam Name: Ist term");
        holder.text_sub_name.setText("Subject Name: Maths");
        holder.text_exam_date.setText("Exam Date: 2/02/2018");
        holder.text_start_time.setText("Start Time: 9 am");
        holder.text_end_time.setText("End Time: 12 pm");
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

