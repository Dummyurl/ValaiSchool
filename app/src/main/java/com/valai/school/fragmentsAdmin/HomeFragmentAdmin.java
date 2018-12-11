package com.valai.school.fragmentsAdmin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valai.school.R;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.fragmentsStaff.AttendanceFragmentStaff;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.modal.SignInPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragmentAdmin extends BaseFragment {
    public static final String TAG = HomeFragmentAdmin.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private FragmentListnerAdmin fragmentListner;
    private List<SignInPOJO.Datum> listSignInRes;

    public HomeFragmentAdmin() {
        // Required empty public constructor
    }

    public static HomeFragmentAdmin newInstance(String param1, String param2) {
        HomeFragmentAdmin fragment = new HomeFragmentAdmin();
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
        View rootView = inflater.inflate(R.layout.fragment_home_admin, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listSignInRes = new ArrayList<>();
        listSignInRes = fragmentListner.getSignInResultList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListnerAdmin) context;
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
        fragmentListner.setOnLoadHome(1, MessageFragmentAdmin.TAG);
    }

    @OnClick(R.id.card_nav_attendance)
    public void onCardAttendanceClick() {
        fragmentListner.setOnLoadHome(2, AttendanceFragmentAdmin.TAG);
    }
/*
    @OnClick(R.id.card_nav_diary)
    public void onCardDiaryClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        fragmentListner.setOnLoadHome(1, DiaryFragment.TAG);
    }



    @OnClick(R.id.card_nav_fee)
    public void onCardFeeClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        fragmentListner.setOnLoadHome(3, FeeFragment.TAG);
    }

    @OnClick(R.id.card_nav_attendance)
    public void onCardAttendanceClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        fragmentListner.setOnLoadHome(4, AttendanceFragment.TAG);
    }

    @OnClick(R.id.card_exam_schedule)
    public void onCardExamClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        fragmentListner.setOnLoadHome(5, ExamFragment.TAG);
    }

    @OnClick(R.id.card_nav_transport)
    public void onCardTransportClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        fragmentListner.setOnLoadHome(6, TransportFragment.TAG);
    }

    @OnClick(R.id.card_nav_holiday)
    public void onCardHolidayListClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        fragmentListner.setOnLoadHome(7, HolidayFragment.TAG);
    }*/
}