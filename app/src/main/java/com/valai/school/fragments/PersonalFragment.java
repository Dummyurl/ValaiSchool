package com.valai.school.fragments;

import android.annotation.SuppressLint;
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

import static com.valai.school.utils.CommonUtils.convertDateStringFormat;

public class PersonalFragment extends BaseFragment {
    public static final String TAG = PersonalFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<GetStudentDetailsPOJO.Datum> list;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    @BindView(R.id.text_name_Val)
    TextView text_name_Val;

    @BindView(R.id.text_class_Val)
    TextView text_class_Val;

    @BindView(R.id.text_reg_Val)
    TextView text_reg_Val;

    @BindView(R.id.text_class_teacher_Val)
    TextView text_class_teacher_Val;

    @BindView(R.id.text_birth_date_Val)
    TextView text_birth_date_Val;

    @BindView(R.id.text_address_Val)
    TextView text_address_Val;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance(List<GetStudentDetailsPOJO.Datum> param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (list != null && list.size() > 0) {
            text_name_Val.setText(list.get(0).getStudentName());
            text_class_Val.setText(list.get(0).getClassName() + "-" + list.get(0).getSectionName());
            text_reg_Val.setText(list.get(0).getAdmissionNo());
            text_class_teacher_Val.setText("");
            text_birth_date_Val.setText(convertDateStringFormat(list.get(0).getDateOfBirth()));
            text_address_Val.setText(list.get(0).getPermntAddress());
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