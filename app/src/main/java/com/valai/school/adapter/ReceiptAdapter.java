package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.interfaces.ReceiptGenerate;
import com.valai.school.modal.GetReceiptPOJO;
import com.valai.school.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetReceiptPOJO.Datum> listReceipt;
    private ReceiptGenerate receiptGenerate;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_receiptNo)
        TextView tv_receiptNo;

        @BindView(R.id.tv_Date)
        TextView tv_Date;

        @BindView(R.id.tv_Amount)
        TextView tv_Amount;

        @BindView(R.id.img_Download)
        ImageButton img_Download;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.img_Download)
        void onDownloadClick(View view) {
            receiptGenerate.getPrintReceiptUrlRequestCall(Integer.parseInt(view.getTag().toString()));
        }
    }

    public ReceiptAdapter(Context ctx, List<GetReceiptPOJO.Datum> listReceipt, ReceiptGenerate receiptGenerate) {
        this.ctx = ctx;
        this.listReceipt = listReceipt;
        this.receiptGenerate = receiptGenerate;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_receipt_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.img_Download.setTag(position);
        holder.tv_receiptNo.setText(String.valueOf(listReceipt.get(position).getReceiptCode()));
        holder.tv_Date.setText(CommonUtils.convertDateStringFormat2(listReceipt.get(position).getDdDate()));
        holder.tv_Amount.setText(String.valueOf(listReceipt.get(position).getReceiptAmount()));
    }

    @Override
    public int getItemCount() {
        return listReceipt.size();
    }
}