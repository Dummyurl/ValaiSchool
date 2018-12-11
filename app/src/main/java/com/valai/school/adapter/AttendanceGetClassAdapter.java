package com.valai.school.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetClassAttendancePOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceGetClassAdapter extends RecyclerView.Adapter<AttendanceGetClassAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetClassAttendancePOJO.Datum> getClassAttendanceList;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image1)
        ImageView image1;

        @BindView(R.id.text_name)
        TextView text_name;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public AttendanceGetClassAdapter(Context ctx, List<GetClassAttendancePOJO.Datum> getClassAttendanceList) {
        this.ctx = ctx;
        this.getClassAttendanceList = getClassAttendanceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignmentsection_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.image1.setTag(position);
        holder.image1.setVisibility(View.GONE);
        holder.text_name.setTag(position);
        holder.text_name.setText(getClassAttendanceList.get(position).getSectionName());
    }

    @Override
    public int getItemCount() {
        return getClassAttendanceList.size();
    }
}