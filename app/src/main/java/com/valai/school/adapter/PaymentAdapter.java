package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetFeeDetailPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetFeeDetailPOJO.Datum> listFee;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_FeeType_Val)
        TextView text_FeeType_Val;

        @BindView(R.id.text_FeeAmount_Val)
        TextView text_FeeAmount_Val;

        @BindView(R.id.text_PaidAmount_Val)
        TextView text_PaidAmount_Val;

        @BindView(R.id.text_Balance_Val)
        TextView text_Balance_Val;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PaymentAdapter(Context ctx, List<GetFeeDetailPOJO.Datum> listFee) {
        this.ctx = ctx;
        this.listFee = listFee;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_FeeType_Val.setText(listFee.get(position).getTypeName());
        holder.text_FeeAmount_Val.setText(String.valueOf(listFee.get(position).getReceiptAmount()));
        holder.text_PaidAmount_Val.setText(String.valueOf(listFee.get(position).getStructureAmount()));
        holder.text_Balance_Val.setText(String.valueOf(listFee.get(position).getBalanceAmount()));
    }

    @Override
    public int getItemCount() {
        return listFee.size();
    }
}