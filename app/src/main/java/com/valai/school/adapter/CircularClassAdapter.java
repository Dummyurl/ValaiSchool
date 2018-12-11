package com.valai.school.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.interfaces.FragmentListnerCircularAdmin;
import com.valai.school.modal.GetCircularClassPOJO;
import com.valai.school.modal.ParentCircularModal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircularClassAdapter extends RecyclerView.Adapter<CircularClassAdapter.MyViewHolder> {

    private Context ctx;
    private List<GetCircularClassPOJO.Datum> datumArrayList;
    private ArrayList<ParentCircularModal> selectedStrings;
    private FragmentListnerAdmin fragmentListner;
    private FragmentListnerCircularAdmin fragmentListnerCircularAdmin;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image1)
        ImageView image1;

        @BindView(R.id.text_name)
        TextView text_name;

        @BindView(R.id.checkbox)
        CheckBox checkbox;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CircularClassAdapter(Context ctx, List<GetCircularClassPOJO.Datum> datumArrayList,
                                FragmentListnerCircularAdmin fragmentListnerCircularAdmin, FragmentListnerAdmin fragmentListner) {
        this.ctx = ctx;
        this.datumArrayList = datumArrayList;
        this.fragmentListnerCircularAdmin = fragmentListnerCircularAdmin;
        this.fragmentListner = fragmentListner;
        selectedStrings = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.circularclass_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.image1.setTag(position);
        holder.text_name.setTag(position);
        holder.checkbox.setChecked(datumArrayList.get(position).isSelected());
        holder.checkbox.setEnabled(datumArrayList.get(position).isChecked());
        holder.checkbox.setTag(position);
        holder.text_name.setText(datumArrayList.get(position).getClassName());
        if (fragmentListner.getAppPreferenceHelper().getStartIntentResponse()) {
            selectedStrings = new ArrayList<>();
        }

        if(position == 0){
            holder.image1.setVisibility(View.GONE);
            holder.text_name.setTextSize(18);
        }else{
            holder.image1.setVisibility(View.VISIBLE);
        }

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    ParentCircularModal modal = new ParentCircularModal();
                    modal.setTitle(holder.text_name.getText().toString());
                    modal.setUserId(datumArrayList.get(position).getClassId());
                    selectedStrings.add(modal);
                } else {
                    for (int i = 0; i < selectedStrings.size(); i++) {
                        if (selectedStrings.get(i).getTitle().equals(holder.text_name.getText().toString())) {
                            selectedStrings.remove(i);
                        }
                    }
                }
                fragmentListnerCircularAdmin.setList(selectedStrings);

                if (position == 0) {
                    if (datumArrayList.get(position).isSelected()) {
                        datumArrayList.get(position).setSelected(false);
                        for (int i = 1; i < datumArrayList.size(); i++) {
                            datumArrayList.get(i).setSelected(false);
                            datumArrayList.get(i).setChecked(true);
                        }
                    } else {
                        datumArrayList.get(position).setSelected(isChecked);
                        for (int i = 1; i < datumArrayList.size(); i++) {
                            datumArrayList.get(i).setSelected(isChecked);
                            datumArrayList.get(i).setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                } else {
                    datumArrayList.get(position).setSelected(isChecked);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }
}