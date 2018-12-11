package com.valai.school.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.valai.school.R;
import com.valai.school.interfaces.CardAdapter;
import com.valai.school.utils.CardItem;
import com.valai.school.utils.CircleTransform;

import java.util.ArrayList;
import java.util.List;

import static com.valai.school.utils.AppConstants.ROOT_DOWNLOAD;
import static com.valai.school.utils.AppConstants.ROOT_GROUP_FOLDER;
import static com.valai.school.utils.AppConstants.ROOT_SLASH;
import static com.valai.school.utils.AppConstants.ROOT_STUDENT_PHOTO_FOLDER;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private Context ctx;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item, Context ctx) {
        mViews.add(null);
        mData.add(item);
        this.ctx = ctx;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public List<CardItem> getCardList() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = view.findViewById(R.id.student_name);
        TextView contentTextView = view.findViewById(R.id.student_class);
        final ImageView imageView = view.findViewById(R.id.student_image);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());

        if (!item.getImage().equals("")) {

            Glide.with(ctx).load(ROOT_DOWNLOAD + ROOT_GROUP_FOLDER + item.getOrgId() + ROOT_STUDENT_PHOTO_FOLDER + item.getAcademicId() + ROOT_SLASH + item.getImage())
                    .error(R.mipmap.student_icon)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .placeholder(R.mipmap.student_icon)
                    .transform(new CircleTransform(ctx))
                    .into(imageView);
        }
    }
}