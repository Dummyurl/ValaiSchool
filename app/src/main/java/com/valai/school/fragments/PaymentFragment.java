package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.PaymentAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetFeeDetailPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.MODE_FEE_DETAIL;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class PaymentFragment extends BaseFragment {
    public static final String TAG = PaymentFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private AnimationItem mSelectedItem;
    private List<GetFeeDetailPOJO.Datum> listFee;
    private List<StudentListPOJO.Datum> studentList;
    private HashMap<Integer, List<GetFeeDetailPOJO.Datum>> hashMapDataList;
    private PaymentAdapter paymentAdapter;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listFee = new ArrayList<>();
        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        setAdapter();

        studentList = new ArrayList<>();
        studentList = fragmentListner.getStudentList();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        hashMapDataList = fragmentListner.getPaymentList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }
        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    listFee = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (listFee != null && listFee.size() > 0) {
                        setAdapter();
                    } else {
                        setAdapter();
                        //showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    setAdapter();
                }
            } else {
                setAdapter();
                //showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getPaymentRequestCall();
        }
    }

    private void setAdapter() {
        if (listFee != null && listFee.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (paymentAdapter == null) {
                paymentAdapter = new PaymentAdapter(getContext(), listFee);
                recycler_view.setAdapter(paymentAdapter);
                runLayoutAnimation(recycler_view, mSelectedItem);
            } else {
                paymentAdapter.notifyDataSetChanged();
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

    public void getPaymentRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetFeeDetailPOJO> call = restClientAPI.getFeeReportCardForParent(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getSectionId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(), MODE_FEE_DETAIL);

        call.enqueue(new Callback<GetFeeDetailPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetFeeDetailPOJO> call, @NonNull Response<GetFeeDetailPOJO> response) {
                GetFeeDetailPOJO getFeeDetailPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getFeeDetailPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getFeeDetailPOJO.getResponseStatus().equals(TRUE)) {
                            listFee.addAll(getFeeDetailPOJO.getData());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                    listFee);
                            fragmentListner.setPaymentList(hashMapDataList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getFeeDetailPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getFeeDetailPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetFeeDetailPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }
}