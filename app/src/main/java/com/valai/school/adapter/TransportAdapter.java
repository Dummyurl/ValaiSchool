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
import com.valai.school.modal.TransportModal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.MyViewHolder> {

    private Context ctx;
    private List<TransportModal> listTrans;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_Trans_Title)
        TextView tv_Trans_Title;

        @BindView(R.id.tv_Trans_Des)
        TextView tv_Trans_Des;

        @BindView(R.id.imgTransIcon)
        ImageView imgTransIcon;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TransportAdapter(Context ctx, List<TransportModal> listTrans) {
        this.ctx = ctx;
        this.listTrans = listTrans;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transport_list_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransportModal model = listTrans.get(position);
        holder.tv_Trans_Title.setText(model.title);
        holder.tv_Trans_Des.setText(model.message);
        holder.imgTransIcon.setImageResource(model.image);
    }

    @Override
    public int getItemCount() {
        return listTrans.size();
    }
}