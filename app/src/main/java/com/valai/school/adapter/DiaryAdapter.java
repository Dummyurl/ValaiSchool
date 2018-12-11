package com.valai.school.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.GetDiaryPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.valai.school.utils.CommonUtils.convertDateStringFormat4;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    private Context ctx;
    private int pos;
    private List<GetDiaryPOJO.Jan> listJan;
    private List<GetDiaryPOJO.Feb> listFeb;
    private List<GetDiaryPOJO.Mar> listMar;
    private List<GetDiaryPOJO.Apr> listApr;
    private List<GetDiaryPOJO.May> listMay;
    private List<GetDiaryPOJO.Jun> listJun;
    private List<GetDiaryPOJO.Jul> listJul;
    private List<GetDiaryPOJO.Aug> listAug;
    private List<GetDiaryPOJO.Sep> listSep;
    private List<GetDiaryPOJO.Oct> listOct;
    private List<GetDiaryPOJO.Nov> listNov;
    private List<GetDiaryPOJO.Dec> listDec;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_DateText)
        TextView tv_DateText;

        @BindView(R.id.diary_title)
        TextView diary_title;

        @BindView(R.id.diary_des)
        TextView diary_des;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public <T> DiaryAdapter(Context ctx, List<T> list, int position) {
        this.ctx = ctx;
        this.pos = position;
        if (position == 0) {
            this.listJan = (List<GetDiaryPOJO.Jan>) list;
        } else if (position == 1) {
            this.listFeb = (List<GetDiaryPOJO.Feb>) list;
        } else if (position == 2) {
            this.listMar = (List<GetDiaryPOJO.Mar>) list;
        } else if (position == 3) {
            this.listApr = (List<GetDiaryPOJO.Apr>) list;
        } else if (position == 4) {
            this.listMay = (List<GetDiaryPOJO.May>) list;
        } else if (position == 5) {
            this.listJun = (List<GetDiaryPOJO.Jun>) list;
        } else if (position == 6) {
            this.listJul = (List<GetDiaryPOJO.Jul>) list;
        } else if (position == 7) {
            this.listAug = (List<GetDiaryPOJO.Aug>) list;
        } else if (position == 8) {
            this.listSep = (List<GetDiaryPOJO.Sep>) list;
        } else if (position == 9) {
            this.listOct = (List<GetDiaryPOJO.Oct>) list;
        } else if (position == 10) {
            this.listNov = (List<GetDiaryPOJO.Nov>) list;
        } else if (position == 11) {
            this.listDec = (List<GetDiaryPOJO.Dec>) list;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_diary_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (pos == 0) {
            holder.diary_title.setText(listJan.get(position).getTitle());
            holder.diary_des.setText(listJan.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listJan.get(position).getStart()));
        } else if (pos == 1) {
            holder.diary_title.setText(listFeb.get(position).getTitle());
            holder.diary_des.setText(listFeb.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listFeb.get(position).getStart()));
        } else if (pos == 2) {
            holder.diary_title.setText(listMar.get(position).getTitle());
            holder.diary_des.setText(listMar.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listMar.get(position).getStart()));
        } else if (pos == 3) {
            holder.diary_title.setText(listApr.get(position).getTitle());
            holder.diary_des.setText(listApr.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listApr.get(position).getStart()));
        } else if (pos == 4) {
            holder.diary_title.setText(listMay.get(position).getTitle());
            holder.diary_des.setText(listMay.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listMay.get(position).getStart()));
        } else if (pos == 5) {
            holder.diary_title.setText(listJun.get(position).getTitle());
            holder.diary_des.setText(listJun.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listJun.get(position).getStart()));
        } else if (pos == 6) {
            holder.diary_title.setText(listJul.get(position).getTitle());
            holder.diary_des.setText(listJul.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listJul.get(position).getStart()));
        } else if (pos == 7) {
            holder.diary_title.setText(listAug.get(position).getTitle());
            holder.diary_des.setText(listAug.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listAug.get(position).getStart()));
        } else if (pos == 8) {
            holder.diary_title.setText(listSep.get(position).getTitle());
            holder.diary_des.setText(listSep.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listSep.get(position).getStart()));
        } else if (pos == 9) {
            holder.diary_title.setText(listOct.get(position).getTitle());
            holder.diary_des.setText(listOct.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listOct.get(position).getStart()));
        } else if (pos == 10) {
            holder.diary_title.setText(listNov.get(position).getTitle());
            holder.diary_des.setText(listNov.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listNov.get(position).getStart()));
        } else if (pos == 11) {
            holder.diary_title.setText(listDec.get(position).getTitle());
            holder.diary_des.setText(listDec.get(position).getTitlePopup());
            holder.tv_DateText.setText(convertDateStringFormat4(listDec.get(position).getStart()));
        }
    }

    @Override
    public int getItemCount() {
        if (pos == 0) {
            return listJan.size();
        } else if (pos == 1) {
            return listFeb.size();
        } else if (pos == 2) {
            return listMar.size();
        } else if (pos == 3) {
            return listApr.size();
        } else if (pos == 4) {
            return listMay.size();
        } else if (pos == 5) {
            return listJun.size();
        } else if (pos == 6) {
            return listJul.size();
        } else if (pos == 7) {
            return listAug.size();
        } else if (pos == 8) {
            return listSep.size();
        } else if (pos == 9) {
            return listOct.size();
        } else if (pos == 10) {
            return listNov.size();
        } else if (pos == 11) {
            return listDec.size();
        }
        return 0;
    }
}