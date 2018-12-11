package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetResultSchedulePOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetResultSchedulePOJO.Result> resultList;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_Subject)
        TextView tv_Subject;

        @BindView(R.id.tv_Marks)
        TextView tv_Marks;

        @BindView(R.id.tv_MMarks)
        TextView tv_MMarks;

        @BindView(R.id.tv_Grade)
        TextView tv_Grade;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ResultAdapter(Context ctx, List<GetResultSchedulePOJO.Result> resultList) {
        this.ctx = ctx;
        this.resultList = resultList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_result_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_Subject.setText(resultList.get(position).getSubjectName());
        holder.tv_Marks.setText(String.valueOf(resultList.get(position).getMarkAttain()));
        holder.tv_MMarks.setText(String.valueOf(resultList.get(position).getMaxMark()));
        holder.tv_Grade.setText(resultList.get(position).getGradeName());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}