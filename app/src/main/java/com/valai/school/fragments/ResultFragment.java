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
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.ResultAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetAcademicYearPOJO;
import com.valai.school.modal.GetExamPOJO;
import com.valai.school.modal.GetReportCardPOJO;
import com.valai.school.modal.GetResultSchedulePOJO;
import com.valai.school.modal.GetResultTypePOJO;
import com.valai.school.modal.GetTermExamIdPOJO;
import com.valai.school.modal.GetTermPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.MODE_GET;
import static com.valai.school.utils.AppConstants.MODE_GET_REPORT_CARD;
import static com.valai.school.utils.AppConstants.MODE_GET_TERM_AND_EXAM;
import static com.valai.school.utils.AppConstants.MODE_READ;
import static com.valai.school.utils.AppConstants.PDF_EXTENTION;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.ROOT_DOWNLOAD;
import static com.valai.school.utils.AppConstants.ROOT_GROUP_FOLDER;
import static com.valai.school.utils.AppConstants.ROOT_REPORT_CARD;
import static com.valai.school.utils.AppConstants.ROOT_REPORT_CARD_FOLDER;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;


public class ResultFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = ResultFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.spinnerExams)
    Spinner spinnerExams;

    @BindView(R.id.text_marks_obtained)
    TextView text_marks_obtained;

    @BindView(R.id.text_total_marks)
    TextView text_total_marks;

    @BindView(R.id.text_total_percentage)
    TextView text_total_percentage;

    @BindView(R.id.text_grade)
    TextView text_grade;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private AnimationItem mSelectedItem;
    private List<GetResultSchedulePOJO.Result> listResult;
    private List<GetResultTypePOJO.Datum> listResultType;
    private List<StudentListPOJO.Datum> studentList;
    private List<GetTermPOJO.Datum> getTermList;
    private List<GetExamPOJO.Datum> getExamList;
    private GetResultSchedulePOJO.Data getResultScheduleData;
    private HashMap<String, List<GetResultSchedulePOJO.Result>> hashMapDataList;
    private HashMap<String, GetResultSchedulePOJO.Data> hashMapDataListSchedule;
    private HashMap<Integer, List<GetResultTypePOJO.Datum>> hashMapDataListResultType;
    private ResultAdapter resultAdapter;
    private int examId;
    private int termId_;
    private String termName_;
    private int examId_;
    private String examName_;
    private String current_year;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listResultType = new ArrayList<>();
        listResult = new ArrayList<>();
        getTermList = new ArrayList<>();
        getExamList = new ArrayList<>();

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

        hashMapDataListResultType = fragmentListner.getResultTypeList();
        if (hashMapDataListResultType == null) {
            hashMapDataListResultType = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataListResultType != null && hashMapDataListResultType.size() > 0) {
                    listResultType = hashMapDataListResultType.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (listResultType != null && listResultType.size() > 0) {
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
        } else if(studentList.size() > 0){
            getResultTypeRequestCall();
        }
    }

    private void setSpinnerItems() {
        if (listResultType != null && listResultType.size() > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < listResultType.size(); i++) {
                list.add(listResultType.get(i).getExamName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerExams.setAdapter(dataAdapter);
            spinnerExams.setOnItemSelectedListener(this);
        }
    }

    private void setAdapter() {
        if (listResult != null && listResult.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (resultAdapter == null) {
                resultAdapter = new ResultAdapter(getContext(), listResult);
                recycler_view.setAdapter(resultAdapter);
                runLayoutAnimation(recycler_view, mSelectedItem);
            } else {
                resultAdapter.notifyDataSetChanged();
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

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        hashMapDataList = fragmentListner.getResultList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        hashMapDataListSchedule = fragmentListner.getResultScheduleData();
        if (hashMapDataListSchedule == null) {
            hashMapDataListSchedule = new HashMap<>();
        }
        examId = listResultType.get(i).getExamId();
        Log.e("StudentId", "StudentId>>" + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
        Log.e("examId", "examId>>" + examId);

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0 && hashMapDataListSchedule != null && hashMapDataListSchedule.size() > 0) {
                    listResult = hashMapDataList.get(
                            studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId);
                    getResultScheduleData = hashMapDataListSchedule.get(
                            studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId);
                    if (listResult != null && listResult.size() > 0) {
                        String[] total_marks = getResultScheduleData.getTotalMarkAttain().split("\\.");
                        String total_marks_ = total_marks[0];

                        String[] total_max_marks = getResultScheduleData.getTotalMaxMark().split("\\.");
                        String total_max_marks_ = total_max_marks[0];

                        String[] total_percentage = getResultScheduleData.getPercentageObtained().split("\\.");
                        String total_percentage_ = total_percentage[0];

                        text_marks_obtained.setText(total_marks_);
                        text_total_marks.setText(total_max_marks_);
                        text_total_percentage.setText(total_percentage_ + "%");
                        text_grade.setText(getResultScheduleData.getOverallGrade());
                        setAdapter();
                    } else {
                        text_marks_obtained.setText("NA");
                        text_total_marks.setText("NA");
                        text_total_percentage.setText("NA");
                        text_grade.setText("NA");
                        setAdapter();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    text_marks_obtained.setText("NA");
                    text_total_marks.setText("NA");
                    text_total_percentage.setText("NA");
                    text_grade.setText("NA");
                    setAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }

            } else {
                text_marks_obtained.setText("NA");
                text_total_marks.setText("NA");
                text_total_percentage.setText("NA");
                text_grade.setText("NA");
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getResultRequestCall();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void getResultTypeRequestCall() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetResultTypePOJO> call = restClientAPI.getReportType(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(), 0, MODE_READ);

        call.enqueue(new Callback<GetResultTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetResultTypePOJO> call, @NonNull Response<GetResultTypePOJO> response) {
                GetResultTypePOJO getResultTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getResultTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getResultTypePOJO.getResponseStatus().equals(TRUE)) {
                            if (getResultTypePOJO.getData() != null && getResultTypePOJO.getData().size() > 0) {
                                listResultType.addAll(getResultTypePOJO.getData());
                                hashMapDataListResultType.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                        listResultType);
                                fragmentListner.setResultTypeList(hashMapDataListResultType);
                                setSpinnerItems();
                            } else {
                                setSpinnerItems();
                            }
                        } else {
                            setSpinnerItems();
                        }
                    } else {
                        setSpinnerItems();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetResultTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setSpinnerItems();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    public void getResultRequestCall() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetResultSchedulePOJO> call = restClientAPI.getStudentExamResultReport(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                examId, MODE_GET_REPORT_CARD);

        call.enqueue(new Callback<GetResultSchedulePOJO>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<GetResultSchedulePOJO> call, @NonNull Response<GetResultSchedulePOJO> response) {
                GetResultSchedulePOJO getResultSchedulePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getResultSchedulePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getResultSchedulePOJO.getResponseStatus().equals(TRUE)) {
                            if (listResult.size() > 0) {
                                listResult.clear();
                            }
                            listResult.addAll(getResultSchedulePOJO.getData().getResult());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId,
                                    listResult);
                            getResultScheduleData = getResultSchedulePOJO.getData();
                            hashMapDataListSchedule.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "," + examId,
                                    getResultScheduleData);

                            fragmentListner.setResultList(hashMapDataList);
                            fragmentListner.setResultScheduleData(hashMapDataListSchedule);
                            String[] total_marks = getResultScheduleData.getTotalMarkAttain().split("\\.");
                            String total_marks_ = total_marks[0];

                            String[] total_max_marks = getResultScheduleData.getTotalMaxMark().split("\\.");
                            String total_max_marks_ = total_max_marks[0];

                            String[] total_percentage = getResultScheduleData.getPercentageObtained().split("\\.");
                            String total_percentage_ = total_percentage[0];

                            text_marks_obtained.setText(total_marks_);
                            text_total_marks.setText(total_max_marks_);
                            text_total_percentage.setText(total_percentage_ + "%");
                            text_grade.setText(getResultSchedulePOJO.getData().getOverallGrade());
                            setAdapter();
                        } else {
                            if (listResult.size() > 0) {
                                listResult.clear();
                            }
                            text_marks_obtained.setText("NA");
                            text_total_marks.setText("NA");
                            text_total_percentage.setText("NA");
                            text_grade.setText("NA");
                            showMessage(getResultSchedulePOJO.getResponseMessage());
                            setAdapter();
                        }
                    } else {
                        if (listResult.size() > 0) {
                            listResult.clear();
                        }
                        text_marks_obtained.setText("NA");
                        text_total_marks.setText("NA");
                        text_total_percentage.setText("NA");
                        text_grade.setText("NA");
                        showMessage(getResultSchedulePOJO.getResponseMessage());
                        setAdapter();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetResultSchedulePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @OnClick(R.id.image_report_download)
    void downloadClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        if (listResult != null && listResult.size() > 0) {
            getTermExamId();
        }
    }

    private void getTermExamId() {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetTermExamIdPOJO> call = restClientAPI.getTermExamId(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                examId, MODE_GET_TERM_AND_EXAM);

        call.enqueue(new Callback<GetTermExamIdPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetTermExamIdPOJO> call, @NonNull Response<GetTermExamIdPOJO> response) {
                GetTermExamIdPOJO getTermExamIdPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getTermExamIdPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getTermExamIdPOJO.getResponseStatus().equals(TRUE)) {
                            getTerm(getTermExamIdPOJO.getData().getResult().get(0).getTermId(), getTermExamIdPOJO.getData().getResult().get(0).getExamId());
                        } else {
                            hideLoading();
                            showMessage(getTermExamIdPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getTermExamIdPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetTermExamIdPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getTerm(final Integer term_id, final Integer exam_id) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetTermPOJO> call = restClientAPI.getTerm(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId());

        call.enqueue(new Callback<GetTermPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetTermPOJO> call, @NonNull Response<GetTermPOJO> response) {
                GetTermPOJO getTermPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getTermPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getTermPOJO.getResponseStatus().equals(TRUE)) {
                            getTermList.addAll(getTermPOJO.getData());
                            for (int i = 0; i < getTermList.size(); i++) {
                                if (term_id == getTermList.get(i).getTermId()) {
                                    termId_ = getTermList.get(i).getTermId();
                                    termName_ = getTermList.get(i).getTermName();
                                    getExam(termId_, exam_id);
                                }
                            }
                        } else {
                            hideLoading();
                            showMessage(getTermPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getTermPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetTermPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
                hideLoading();
            }
        });
    }

    private void getExam(Integer termId_, final Integer examId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetExamPOJO> call = restClientAPI.getExam(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(), termId_,
                0);

        call.enqueue(new Callback<GetExamPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExamPOJO> call, @NonNull Response<GetExamPOJO> response) {
                GetExamPOJO getExamPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getExamPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getExamPOJO.getResponseStatus().equals(TRUE)) {
                            getExamList.addAll(getExamPOJO.getData());
                            for (int i = 0; i < getExamList.size(); i++) {
                                if (examId == getExamList.get(i).getExamId()) {
                                    examId_ = getExamList.get(i).getExamId();
                                    examName_ = getExamList.get(i).getExamName();
                                    getAcademicYear();
                                }
                            }
                        } else {
                            hideLoading();
                            showMessage(getExamPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getExamPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExamPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
                hideLoading();
            }
        });
    }

    private void getAcademicYear() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetAcademicYearPOJO> call = restClientAPI.getAcademicYearMaster(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                MODE_GET);

        call.enqueue(new Callback<GetAcademicYearPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetAcademicYearPOJO> call, @NonNull Response<GetAcademicYearPOJO> response) {
                GetAcademicYearPOJO getAcademicYearPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getAcademicYearPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getAcademicYearPOJO.getResponseStatus().equals(TRUE)) {
                            current_year = String.valueOf(getAcademicYearPOJO.getData().get(0).getAcademicYrsFrom()) + "-" +
                                    String.valueOf(getAcademicYearPOJO.getData().get(0).getAcademicYrsTo());
                            getReportCard();
                        } else {
                            hideLoading();
                            showMessage(getAcademicYearPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getAcademicYearPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAcademicYearPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
                hideLoading();
            }
        });
    }

    private void getReportCard() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetReportCardPOJO> call = restClientAPI.getReportCard(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getSectionId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                termId_, examId_, termName_, examName_, current_year);
        call.enqueue(new Callback<GetReportCardPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetReportCardPOJO> call, @NonNull Response<GetReportCardPOJO> response) {
                GetReportCardPOJO getReportCardPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getReportCardPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getReportCardPOJO.getResponseStatus().equals(TRUE)) {
                            Log.e("Success", "Success");
                            hideLoading();
                            Log.e("ReportCard", "Path>>" + ROOT_DOWNLOAD
                                    + ROOT_GROUP_FOLDER + ROOT_REPORT_CARD_FOLDER
                                    + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId()
                                    + ROOT_REPORT_CARD + "-" + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "-" + termId_ + PDF_EXTENTION);

                            fragmentListner.getFileDownloadCall(ROOT_DOWNLOAD
                                            + ROOT_GROUP_FOLDER + ROOT_REPORT_CARD_FOLDER
                                            + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId()
                                            + ROOT_REPORT_CARD + "-" + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "-" + termId_ + PDF_EXTENTION,
                                    ROOT_REPORT_CARD + "-" + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId() + "-" + termId_ + PDF_EXTENTION);
                        } else {
                            hideLoading();
                            showMessage(getReportCardPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getReportCardPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetReportCardPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
                hideLoading();
            }
        });
    }
}