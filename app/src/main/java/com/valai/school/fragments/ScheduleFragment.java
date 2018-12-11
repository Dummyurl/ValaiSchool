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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.valai.school.R;
import com.valai.school.adapter.ScheduleAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetExamSchedulePOJO;
import com.valai.school.modal.GetExamTypePOJO;
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
import static com.valai.school.utils.AppConstants.MODE_GET_EXAM_REPORT;
import static com.valai.school.utils.AppConstants.MODE_GET_EXAM_TYPE;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class ScheduleFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = ScheduleFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CharSequence> param1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private AnimationItem mSelectedItem;
    private ArrayAdapter<String> dataAdapter;
    private List<StudentListPOJO.Datum> studentList;
    private List<GetExamSchedulePOJO.Datum> listExamSchedule;
    private List<GetExamTypePOJO.Datum> listExamType;
    private HashMap<Integer, List<GetExamTypePOJO.Datum>> hashMapDataListExamType;
    private HashMap<String, List<GetExamSchedulePOJO.Datum>> hashMapList;
    private int examId;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.spinnerExams)
    Spinner spinnerExams;

    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listExamType = new ArrayList<>();
        listExamSchedule = new ArrayList<>();

        studentList = new ArrayList<>();
        studentList = fragmentListner.getStudentList();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        setAdapter();

        hashMapDataListExamType = fragmentListner.getExamTypeList();
        if (hashMapDataListExamType == null) {
            hashMapDataListExamType = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataListExamType != null && hashMapDataListExamType.size() > 0) {
                    listExamType = hashMapDataListExamType.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (listExamType != null && listExamType.size() > 0) {
                        setSpinnerItems();
                    } else {
                        setSpinnerItems();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    setSpinnerItems();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                setSpinnerItems();
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getExamTypeRequestCall();
        }
    }

    private void setSpinnerItems() {
        if (listExamType != null && listExamType.size() > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < listExamType.size(); i++) {
                list.add(listExamType.get(i).getExamName());
            }
            if (dataAdapter == null) {
                dataAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerExams.setAdapter(dataAdapter);
                spinnerExams.setOnItemSelectedListener(this);
            } else {
                dataAdapter.notifyDataSetChanged();
            }
        }
    }

    private void setAdapter() {
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(), listExamSchedule);
        recycler_view.setAdapter(scheduleAdapter);
        runLayoutAnimation(recycler_view, mSelectedItem);
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

    @SuppressLint("UseSparseArrays")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        hashMapList = fragmentListner.getExamScheduleList();
        if (hashMapList == null) {
            hashMapList = new HashMap<>();
        }
        examId = listExamType.get(i).getExamId();
        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapList != null && hashMapList.size() > 0) {
                    if (hashMapList.size() > 0 && hashMapList.containsKey(
                            studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId)) {
                        if (listExamSchedule.size() > 0) {
                            listExamSchedule.clear();
                        }
                        listExamSchedule = hashMapList.get(
                                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId);
                        if (listExamSchedule != null && listExamSchedule.size() > 0) {
                            setAdapter();
                        } else {
                            setEmptyAdapter();
                        }
                    } else {
                        setEmptyAdapter();
                    }
                } else {
                    setEmptyAdapter();
                }
            } else {
                setEmptyAdapter();
            }
        } else if (studentList.size() > 0) {
            getExamScheduleRequestCall();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setEmptyAdapter() {
        listExamSchedule = new ArrayList<>();
        setAdapter();
        showMessage(getString(R.string.internet_not_available));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void getExamTypeRequestCall() {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetExamTypePOJO> call = restClientAPI.getExamType(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                MODE_GET_EXAM_TYPE);

        call.enqueue(new Callback<GetExamTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExamTypePOJO> call, @NonNull Response<GetExamTypePOJO> response) {
                GetExamTypePOJO getExamTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getExamTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getExamTypePOJO.getResponseStatus().equals(TRUE)) {
                            if (getExamTypePOJO.getData() != null && getExamTypePOJO.getData().size() > 0) {
                                listExamType.addAll(getExamTypePOJO.getData());
                                hashMapDataListExamType.put(
                                        studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                        listExamType);
                                fragmentListner.setExamTypeList(hashMapDataListExamType);
                                setSpinnerItems();
                            } else {
                                setSpinnerItems();
                            }
                        } else {
                            setSpinnerItems();
                            showMessage(getExamTypePOJO.getResponseMessage());
                        }
                    } else {
                        setSpinnerItems();
                        showMessage(getExamTypePOJO.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExamTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setSpinnerItems();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    public void getExamScheduleRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetExamSchedulePOJO> call = restClientAPI.getStudentExamScheduleReport(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getBranchId(),
                examId, MODE_GET_EXAM_REPORT);

        call.enqueue(new Callback<GetExamSchedulePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExamSchedulePOJO> call, @NonNull Response<GetExamSchedulePOJO> response) {
                GetExamSchedulePOJO getExamSchedulePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getExamSchedulePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getExamSchedulePOJO.getResponseStatus().equals(TRUE)) {
                            hideLoading();
                            if (listExamSchedule.size() > 0) {
                                listExamSchedule.clear();
                            }
                            listExamSchedule = getExamSchedulePOJO.getData();
                            hashMapList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId,
                                    listExamSchedule);
                            fragmentListner.setExamScheduleList(hashMapList);
                            setAdapter();
                        } else {
                            if (listExamSchedule.size() > 0) {
                                listExamSchedule.clear();
                            }
                            hideLoading();
                            showMessage(getExamSchedulePOJO.getResponseMessage());
                            setAdapter();
                        }
                    } else {
                        if (listExamSchedule.size() > 0) {
                            listExamSchedule.clear();
                        }
                        hideLoading();
                        showMessage(getExamSchedulePOJO.getResponseMessage());
                        setAdapter();
                    }
                } else {
                    if (listExamSchedule.size() > 0) {
                        listExamSchedule.clear();
                    }
                    hideLoading();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExamSchedulePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                if (listExamSchedule.size() > 0) {
                    listExamSchedule.clear();
                }
                hideLoading();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }
}