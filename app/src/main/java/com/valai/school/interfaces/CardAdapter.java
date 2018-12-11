package com.valai.school.interfaces;


import android.support.v7.widget.CardView;

import com.valai.school.utils.CardItem;

import java.util.List;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    List<CardItem> getCardList();

    int getCount();
}