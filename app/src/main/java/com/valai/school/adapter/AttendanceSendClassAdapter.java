package com.valai.school.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetStudentNameDetailsPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttendanceSendClassAdapter extends RecyclerView.Adapter<AttendanceSendClassAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetStudentNameDetailsPOJO.Datum> studentDetailList;

    public AttendanceSendClassAdapter(Context ctx, List<GetStudentNameDetailsPOJO.Datum> studentDetailList) {
        this.ctx = ctx;
        this.studentDetailList = studentDetailList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btnPresent)
        Button btnPresent;

        @BindView(R.id.btnAbsent)
        Button btnAbsent;

        @BindView(R.id.btnHalfDay)
        Button btnHalfDay;

        @BindView(R.id.text_name)
        TextView text_name;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.btnPresent)
        void btnPresentClick(View view) {
            studentDetailList.get(Integer.parseInt(view.getTag().toString())).setIsPresent(1);
            notifyDataSetChanged();
        }

        @OnClick(R.id.btnAbsent)
        void btnAbsentClick(View view) {
            studentDetailList.get(Integer.parseInt(view.getTag().toString())).setIsPresent(2);
            notifyDataSetChanged();
        }

        @OnClick(R.id.btnHalfDay)
        void btnHalfDayClick(View view) {
            studentDetailList.get(Integer.parseInt(view.getTag().toString())).setIsPresent(3);
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_send_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.btnPresent.setTag(position);
        holder.btnAbsent.setTag(position);
        holder.btnHalfDay.setTag(position);
        holder.text_name.setText(studentDetailList.get(position).getStudName());

        if (studentDetailList.get(position).getIsPresent() == 1) {
            holder.btnPresent.setBackgroundResource(R.drawable.green_rect_fill);
            holder.btnAbsent.setBackgroundResource(R.drawable.red_rect);
            holder.btnHalfDay.setBackgroundResource(R.drawable.yellow_rect);

            holder.btnPresent.setTextColor(Color.WHITE);
            holder.btnAbsent.setTextColor(Color.parseColor("#DB5955"));
            holder.btnHalfDay.setTextColor(Color.parseColor("#F4B947"));

        } else if (studentDetailList.get(position).getIsPresent() == 2) {
            holder.btnPresent.setBackgroundResource(R.drawable.green_rect);
            holder.btnAbsent.setBackgroundResource(R.drawable.red_rect_fill);
            holder.btnHalfDay.setBackgroundResource(R.drawable.yellow_rect);

            holder.btnPresent.setTextColor(Color.parseColor("#4A994A"));
            holder.btnAbsent.setTextColor(Color.WHITE);
            holder.btnHalfDay.setTextColor(Color.parseColor("#F4B947"));

        } else if (studentDetailList.get(position).getIsPresent() == 3) {
            holder.btnPresent.setBackgroundResource(R.drawable.green_rect);
            holder.btnAbsent.setBackgroundResource(R.drawable.red_rect);
            holder.btnHalfDay.setBackgroundResource(R.drawable.yellow_rect_fill);

            holder.btnPresent.setTextColor(Color.parseColor("#4A994A"));
            holder.btnAbsent.setTextColor(Color.parseColor("#DB5955"));
            holder.btnHalfDay.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return studentDetailList.size();
    }
}