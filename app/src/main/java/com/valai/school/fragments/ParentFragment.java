package com.valai.school.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetStudentDetailsPOJO;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentFragment extends BaseFragment {
    public static final String TAG = ParentFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<GetStudentDetailsPOJO.Datum> list;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    @BindView(R.id.text_f_name_Val)
    TextView text_f_name_Val;

    @BindView(R.id.text_f_mobile_Val)
    TextView text_f_mobile_Val;

    @BindView(R.id.text_f_email_value)
    TextView text_f_email_value;

    @BindView(R.id.text_m_name_Val)
    TextView text_m_name_Val;

    @BindView(R.id.text_m_mobile_Val)
    TextView text_m_mobile_Val;

    @BindView(R.id.text_address_Val)
    TextView text_address_Val;

    @BindView(R.id.text_guardian_name_Val)
    TextView text_guardian_name_Val;

    @BindView(R.id.text_guardian_mobile_Val)
    TextView text_guardian_mobile_Val;

    @BindView(R.id.text_guardian_email_Val)
    TextView text_guardian_email_Val;

    public ParentFragment() {
        // Required empty public constructor
    }

    public static ParentFragment newInstance(List<GetStudentDetailsPOJO.Datum> param1, String param2) {
        ParentFragment fragment = new ParentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = (List<GetStudentDetailsPOJO.Datum>) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (list != null && list.size() > 0) {
            text_f_name_Val.setText(list.get(0).getFatherName());
            text_f_mobile_Val.setText(list.get(0).getFatherMobNo());
            text_f_email_value.setText(list.get(0).getFatherEmailId());

            text_m_name_Val.setText(list.get(0).getMotherName());
            text_m_mobile_Val.setText(list.get(0).getMotherMobile());
            text_address_Val.setText(list.get(0).getMotherEmailId());

            text_guardian_name_Val.setText(list.get(0).getGuardianName());
            text_guardian_mobile_Val.setText(list.get(0).getGuardianMobile());
            text_guardian_email_Val.setText(list.get(0).getGuardianEmailId());
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}