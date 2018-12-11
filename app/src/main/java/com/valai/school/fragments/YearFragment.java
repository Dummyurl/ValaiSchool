package com.valai.school.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.YearAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetAttendancePOJO;
import com.valai.school.utils.AnimationItem;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;


public class YearFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private OnFragmentInteractionListener mListener;
    private AnimationItem mSelectedItem;
    private FragmentListner fragmentListner;
    private List<GetAttendancePOJO.Datum> listAttendance;
    private YearAdapter yearAdapter;

    public YearFragment() {
        // Required empty public constructor
    }

    public static YearFragment newInstance(List<GetAttendancePOJO.Datum> listAttendance, String param2) {
        YearFragment fragment = new YearFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) listAttendance);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listAttendance = (List<GetAttendancePOJO.Datum>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_year, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[2];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        setAdapter();
    }

    private void setAdapter() {
        if (listAttendance != null && listAttendance.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (yearAdapter == null) {
                yearAdapter = new YearAdapter(getContext(), listAttendance);
                recycler_view.setAdapter(yearAdapter);
                runLayoutAnimation(recycler_view, mSelectedItem);
            } else {
                yearAdapter.notifyDataSetChanged();
                runLayoutAnimation(recycler_view, mSelectedItem);
            }
        } else {
            recycler_view.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        runLayoutAnimation(recycler_view, mSelectedItem);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }
}