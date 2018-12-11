package com.valai.school.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetAttendancePOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetAttendancePOJO.Datum> listAttendance;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_month)
        TextView text_month;

        @BindView(R.id.text_present)
        TextView text_present;

        @BindView(R.id.text_absent)
        TextView text_absent;

        @BindView(R.id.text_total)
        TextView text_total;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public YearAdapter(Context ctx, List<GetAttendancePOJO.Datum> listAttendance) {
        this.ctx = ctx;
        this.listAttendance = listAttendance;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.year_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_month.setText(listAttendance.get(position).getMonthName());

        if (String.valueOf(listAttendance.get(position).getPresent()).lastIndexOf(".0") == 1) {
            holder.text_present.setText(String.valueOf(listAttendance.get(position).getPresent()).replace(".0", ""));
        } else {
            holder.text_present.setText(String.valueOf(listAttendance.get(position).getPresent()));
        }

        if (String.valueOf(listAttendance.get(position).getAbsent()).lastIndexOf(".0") == 1) {
            holder.text_absent.setText(String.valueOf(listAttendance.get(position).getAbsent()).replace(".0", ""));
        } else {
            holder.text_absent.setText(String.valueOf(listAttendance.get(position).getAbsent()));
        }

        if (String.valueOf(listAttendance.get(position).getTotalDay()).lastIndexOf(".0") == 1) {
            holder.text_total.setText(String.valueOf(listAttendance.get(position).getTotalDay()).replace(".0", ""));
        } else {
            holder.text_total.setText(String.valueOf(listAttendance.get(position).getTotalDay()));
        }
    }

    @Override
    public int getItemCount() {
        return listAttendance.size();
    }
}