package com.valai.school.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.utils.ImageConverter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private Context ctx;
    ArrayList<String> Names;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView text_name;

        @BindView(R.id.text_pending)
        TextView text_pending;

        @BindView(R.id.image1)
        ImageView image1;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
    public ChatAdapter(ArrayList<String> Names1) {
        this.Names = Names1;
    }


    public ChatAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_name.setText(Names.get(position));

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}