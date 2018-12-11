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
import com.valai.school.adapter.TransportAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetTransportPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.modal.TransportModal;
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
import static com.valai.school.utils.AppConstants.MODE_TRANSPORT_REPORT;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class TransportFragment extends BaseFragment {
    public static final String TAG = TransportFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private AnimationItem mSelectedItem;
    private List<TransportModal> listTrans;
    private TransportAdapter transportAdapter;
    private List<StudentListPOJO.Datum> studentList;
    private HashMap<Integer, List<TransportModal>> hashMapDataList;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    public TransportFragment() {
        // Required empty public constructor
    }

    public static TransportFragment newInstance(String param1, String param2) {
        TransportFragment fragment = new TransportFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_transport, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listTrans = new ArrayList<>();
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

        hashMapDataList = fragmentListner.getTransportList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    listTrans = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());

                    if (listTrans != null && listTrans.size() > 0) {
                        setAdapter();
                    } else {
                        setAdapter();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    setAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }

            } else {
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getTransportRequestCall();
        }
    }

    private void setAdapter() {
        if (listTrans != null && listTrans.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (transportAdapter == null) {
                transportAdapter = new TransportAdapter(getContext(), listTrans);
                recycler_view.setAdapter(transportAdapter);
                runLayoutAnimation(recycler_view, mSelectedItem);
            } else {
                transportAdapter.notifyDataSetChanged();
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

    public void getTransportRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetTransportPOJO> call = restClientAPI.getTransportReport(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(), MODE_TRANSPORT_REPORT);
        call.enqueue(new Callback<GetTransportPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetTransportPOJO> call, @NonNull Response<GetTransportPOJO> response) {
                GetTransportPOJO getTransportPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getTransportPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getTransportPOJO.getResponseStatus().equals(TRUE)) {
                            if (getTransportPOJO.getData() != null && getTransportPOJO.getData().size() > 0) {
                                listTrans.add(new TransportModal("Route Name", getTransportPOJO.getData().get(0).getPickupRouteNo(), R.drawable.route));
                                listTrans.add(new TransportModal("Pickup Point", getTransportPOJO.getData().get(0).getPickupstop(), R.drawable.pickup));
                                listTrans.add(new TransportModal("Pickup Time", getTransportPOJO.getData().get(0).getPickupTime(), R.drawable.pickup_time));
                                listTrans.add(new TransportModal("Drop Point", getTransportPOJO.getData().get(0).getDropstop(), R.drawable.drop));
                                listTrans.add(new TransportModal("Drop Time", getTransportPOJO.getData().get(0).getDropTime(), R.drawable.pickup_time));
                                listTrans.add(new TransportModal("Vehicle Registration No.", getTransportPOJO.getData().get(0).getPickupBusNo(), R.drawable.reg_no));
                                listTrans.add(new TransportModal("Helper Phone Number", getTransportPOJO.getData().get(0).getDropHelpermobile(), R.drawable.phone));
                                listTrans.add(new TransportModal("Driver Phone Number", getTransportPOJO.getData().get(0).getDropDrivermobile(), R.drawable.phone));
                                hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                        listTrans);
                                fragmentListner.setTransportList(hashMapDataList);
                                setAdapter();
                            } else {
                                setBlankList();
                                setAdapter();
                            }

                        } else {
                            showMessage(getTransportPOJO.getResponseMessage());
                            setBlankList();
                            hideLoading();
                            setAdapter();
                        }
                    } else {
                        setBlankList();
                        hideLoading();
                        showMessage(getTransportPOJO.getResponseMessage());
                        setAdapter();
                    }
                } else {
                    setBlankList();
                    hideLoading();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetTransportPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setBlankList();
                hideLoading();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setBlankList() {
        listTrans.add(new TransportModal("Route Name", "", R.drawable.route));
        listTrans.add(new TransportModal("Pickup Point", "", R.drawable.pickup));
        listTrans.add(new TransportModal("Pickup Time", "", R.drawable.pickup_time));
        listTrans.add(new TransportModal("Drop Point", "", R.drawable.drop));
        listTrans.add(new TransportModal("Drop Time", "", R.drawable.pickup_time));
        listTrans.add(new TransportModal("Vehicle Registration No.", "", R.drawable.reg_no));
        listTrans.add(new TransportModal("Helper Phone Number", "", R.drawable.phone));
        listTrans.add(new TransportModal("Driver Phone Number", "", R.drawable.phone));
        hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                listTrans);
        fragmentListner.setTransportList(hashMapDataList);
    }
}
