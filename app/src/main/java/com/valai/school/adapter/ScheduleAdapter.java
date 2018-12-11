package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetExamSchedulePOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetExamSchedulePOJO.Datum> listExamSchedule;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_Subject)
        TextView tv_Subject;

        @BindView(R.id.tv_Time)
        TextView tv_Time;

        @BindView(R.id.tv_Comments)
        TextView tv_Comments;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ScheduleAdapter(Context ctx, List<GetExamSchedulePOJO.Datum> listExamSchedule) {
        this.ctx = ctx;
        this.listExamSchedule = listExamSchedule;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_schedule_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_Subject.setText(listExamSchedule.get(position).getSubjectName());
        holder.tv_Time.setText(listExamSchedule.get(position).getStartTime());
        holder.tv_Comments.setText(listExamSchedule.get(position).getcomment());
    }

    @Override
    public int getItemCount() {
        return listExamSchedule.size();
    }
}