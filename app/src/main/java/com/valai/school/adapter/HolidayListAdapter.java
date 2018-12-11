package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetHolidaysPOJO;
import com.valai.school.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayListAdapter extends RecyclerView.Adapter<HolidayListAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetHolidaysPOJO.Datum> holidaysList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_Holy_Title)
        TextView tv_Holy_Title;

        @BindView(R.id.tv_Holy_Date)
        TextView tv_Holy_Date;

        @BindView(R.id.tv_Holy_Des)
        TextView tv_Holy_Des;

        @BindView(R.id.viewActive)
        View viewActive;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public HolidayListAdapter(Context ctx, List<GetHolidaysPOJO.Datum> holidaysList) {
        this.ctx = ctx;
        this.holidaysList = holidaysList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_holidays_list_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_Holy_Title.setText(holidaysList.get(position).getHolidayName());
        holder.tv_Holy_Des.setText(holidaysList.get(position).getHolidayDescription());

        String startDatesHolidays = CommonUtils.convertDateStringFormat3(holidaysList.get(position).getHolidayStart());
        String endDatesHolidays = CommonUtils.convertDateStringFormat3(holidaysList.get(position).getHolidayEnd());

        if (startDatesHolidays.equals(endDatesHolidays)) {
            holder.tv_Holy_Date.setText(startDatesHolidays);
        } else {
            holder.tv_Holy_Date.setText(startDatesHolidays + " to " + endDatesHolidays);
        }

        if (holidaysList.get(position).getIsActive() == 1) {
            holder.viewActive.setBackgroundColor(ctx.getResources().getColor(R.color.colorLime));
        } else {
            holder.viewActive.setBackgroundColor(ctx.getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public int getItemCount() {
        return holidaysList.size();
    }
}