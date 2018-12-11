package com.valai.school.fragmentsStaff;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valai.school.R;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.interfaces.FragmentListnerStaff;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragmentStaff extends BaseFragment {
    public static final String TAG = HomeFragmentStaff.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private FragmentListnerStaff fragmentListner;

    public HomeFragmentStaff() {
        // Required empty public constructor
    }

    public static HomeFragmentStaff newInstance(String param1, String param2) {
        HomeFragmentStaff fragment = new HomeFragmentStaff();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_staff, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListnerStaff) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @OnClick(R.id.card_nav_message)
    public void onCardMessageClick() {
        fragmentListner.setOnLoadHome(1, MessageFragmentStaff.TAG);
    }

    @OnClick(R.id.card_nav_attendance)
    public void onCardAttendanceClick() {
        fragmentListner.setOnLoadHome(2, AttendanceFragmentStaff.TAG);
    }
}