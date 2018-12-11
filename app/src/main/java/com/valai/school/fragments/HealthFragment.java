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

public class HealthFragment extends BaseFragment {
    public static final String TAG = HealthFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<GetStudentDetailsPOJO.Datum> list;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    @BindView(R.id.text_blood_group_Val)
    TextView text_blood_group_Val;

    @BindView(R.id.text_height_Val)
    TextView text_height_Val;

    @BindView(R.id.text_weight_Val)
    TextView text_weight_Val;

    @BindView(R.id.text_allergy_details_Val)
    TextView text_allergy_details_Val;

    public HealthFragment() {
        // Required empty public constructor
    }

    public static HealthFragment newInstance(List<GetStudentDetailsPOJO.Datum> param1, String param2) {
        HealthFragment fragment = new HealthFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_health, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (list != null && list.size() > 0) {
            text_blood_group_Val.setText(list.get(0).getBloodGroup());
            if (!String.valueOf(list.get(0).getHeight()).equals("null")) {
                text_height_Val.setText(String.valueOf(list.get(0).getHeight()));
            } else {
                text_height_Val.setText("");
            }

            if (!String.valueOf(list.get(0).getWeight()).equals("null")) {
                text_weight_Val.setText(String.valueOf(list.get(0).getWeight()));
            } else {
                text_weight_Val.setText("");
            }

            StringBuilder csvBuilder = new StringBuilder();
            if (list.get(0).getAlrgEgg() == 1) {
                csvBuilder.append("Egg");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgFish() == 1) {
                csvBuilder.append("Fish");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgMilk() == 1) {
                csvBuilder.append("Milk");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgPeanut() == 1) {
                csvBuilder.append("Peanut");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgShellfish() == 1) {
                csvBuilder.append("Shellfish");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgSoy() == 1) {
                csvBuilder.append("Soy");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgTreenut() == 1) {
                csvBuilder.append("Treenut");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgWheat() == 1) {
                csvBuilder.append("Wheat");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgDtpvaccine() == 1) {
                csvBuilder.append("Dtpvaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgPoliovaccine() == 1) {
                csvBuilder.append("Poliovaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgMealsvaccine() == 1) {
                csvBuilder.append("Mealsvaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgMumpsvaccine() == 1) {
                csvBuilder.append("Mumpsvaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgRubellavaccine() == 1) {
                csvBuilder.append("Rubellavaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgVaricellavaccine() == 1) {
                csvBuilder.append("Varicellavaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getAlrgHibvaccine() == 1) {
                csvBuilder.append("Hibvaccine");
                csvBuilder.append(",");
            }

            if (list.get(0).getASalrgPneumococcalconjugate() == 1) {
                csvBuilder.append("ASalrgPneumococcalconjugate");
                csvBuilder.append(",");
            }

            String csv = csvBuilder.toString();
            if (csv.length() > 0) {
                String allergy = csv.substring(0, csv.length() - 1);
                text_allergy_details_Val.setText(allergy);
            } else {
                text_allergy_details_Val.setText("");
            }
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