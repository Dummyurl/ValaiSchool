package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.valai.school.adapter.AssignmentAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.interfaces.GetFileDownload;
import com.valai.school.modal.GetAssignmentPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.MODE_GET_PARENT_ASSIGNMENT;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class AssignmentFragment extends BaseFragment implements GetFileDownload {
    public static final String TAG = AssignmentFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private AnimationItem mSelectedItem;
    private List<StudentListPOJO.Datum> studentList;
    private List<GetAssignmentPOJO.Datum> getAssignmentArrayList;
    private HashMap<Integer, List<GetAssignmentPOJO.Datum>> hashMapDataList;
    private GetFileDownload fileDownload;

    public AssignmentFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
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

        fileDownload = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_assignment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAssignmentArrayList = new ArrayList<>();

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

        hashMapDataList = fragmentListner.getAssignmentList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {

                    getAssignmentArrayList = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());

                    if (getAssignmentArrayList != null && getAssignmentArrayList.size() > 0) {
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
            getAssignmentRequestCall();
        }
    }

    private void setAdapter() {
        if (getAssignmentArrayList != null && getAssignmentArrayList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            AssignmentAdapter assignmentAdapter = new AssignmentAdapter(getContext(), getAssignmentArrayList, studentList, fileDownload, fragmentListner, hashMapDataList);
            recycler_view.setAdapter(assignmentAdapter);
            runLayoutAnimation(recycler_view, mSelectedItem);
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

    public void getAssignmentRequestCall() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetAssignmentPOJO> call = restClientAPI.getAssignmentForParent(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getBranchId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getSectionId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                MODE_GET_PARENT_ASSIGNMENT);
        call.enqueue(new Callback<GetAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetAssignmentPOJO> call, @NonNull Response<GetAssignmentPOJO> response) {
                GetAssignmentPOJO getAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            for (int i = 0; i < getAssignmentPOJO.getData().size(); i++) {
                                String directoryName = "";

                                if (getAssignmentPOJO.getData().get(i).getFileName().contains(".doc")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".docx")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".xls")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".xlsx")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".pdf")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".txt")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".csv")) {

                                    // Word document
                                    directoryName = getString(R.string.app_name_docs);

                                } else if (getAssignmentPOJO.getData().get(i).getFileName().contains(".jpg")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".jpeg")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".png")) {

                                    // JPG,JPEG,PNG file
                                    directoryName = getString(R.string.app_name_images);

                                } else if (getAssignmentPOJO.getData().get(i).getFileName().contains(".gif")) {

                                    // GIF file
                                    directoryName = getString(R.string.app_name_gif);

                                } else if (getAssignmentPOJO.getData().get(i).getFileName().contains(".mp4")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".3gp")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".avi")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".flv")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".mov")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".m4a")) {

                                    // Video file
                                    directoryName = getString(R.string.app_name_video);

                                } else if (getAssignmentPOJO.getData().get(i).getFileName().contains(".mp3")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".wmv")
                                        || getAssignmentPOJO.getData().get(i).getFileName().contains(".ogg")) {

                                    // Audio Video file
                                    directoryName = getString(R.string.app_name_audio);

                                }

                                File outputFile;
                                if (!directoryName.equals("")) {
                                    outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                            + File.separator
                                            + getString(R.string.app_name) + File.separator + directoryName + File.separator,
                                            getAssignmentPOJO.getData().get(i).getFileName());
                                } else {
                                    outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                            + File.separator
                                            + getString(R.string.app_name) + File.separator,
                                            getAssignmentPOJO.getData().get(i).getFileName());
                                }

                                if (outputFile.exists()) {
                                    if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {

                                        // Word document
                                        getAssignmentPOJO.getData().get(i).setDocDownloaded(true);

                                    } else if (outputFile.toString().contains(".pdf")) {

                                        // PDF file
                                        getAssignmentPOJO.getData().get(i).setPdfDownloaded(true);

                                    } else if (outputFile.toString().contains(".xls") || outputFile.toString().contains(".xlsx")) {

                                        // Excel file
                                        getAssignmentPOJO.getData().get(i).setExcelFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".jpg")) {

                                        // JPG file
                                        getAssignmentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".jpeg")) {

                                        // JPEG file
                                        getAssignmentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".png")) {

                                        // PNG file
                                        getAssignmentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".txt")) {

                                        // Text file
                                        getAssignmentPOJO.getData().get(i).setTxtDownloaded(true);

                                    } else if (outputFile.toString().contains(".csv")) {

                                        // CSV file
                                        getAssignmentPOJO.getData().get(i).setCsvFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".gif")) {

                                        // GIF file
                                        getAssignmentPOJO.getData().get(i).setGifFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".mp4")
                                            || outputFile.toString().contains(".3gp")
                                            || outputFile.toString().contains(".avi")
                                            || outputFile.toString().contains(".flv")
                                            || outputFile.toString().contains(".mov")
                                            || outputFile.toString().contains(".m4a")
                                            || outputFile.toString().contains(".mp3")
                                            || outputFile.toString().contains(".wmv")
                                            || outputFile.toString().contains(".ogg")) {

                                        // Audio Video file
                                        getAssignmentPOJO.getData().get(i).setAudioVideoDownloaded(true);

                                    } else {
                                        getAssignmentPOJO.getData().get(i).setImageDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setPdfDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setDocDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setTxtDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setExcelFileDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setCsvFileDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setAudioVideoDownloaded(false);
                                        getAssignmentPOJO.getData().get(i).setGifFileDownloaded(false);
                                    }

                                } else {
                                    getAssignmentPOJO.getData().get(i).setImageDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setPdfDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setDocDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setTxtDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setExcelFileDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setCsvFileDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setAudioVideoDownloaded(false);
                                    getAssignmentPOJO.getData().get(i).setGifFileDownloaded(false);
                                }
                            }
                            getAssignmentArrayList.addAll(getAssignmentPOJO.getData());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                    getAssignmentArrayList);
                            fragmentListner.setAssignmentList(hashMapDataList);
                            setAdapter();
                        } else {
                            //showMessage(getAssignmentPOJO.getResponseMessage());
                        }
                    } else {
                        //showMessage(getAssignmentPOJO.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void getFileDownloadCall(String fileUrl, final String filename) {
        fragmentListner.getFileDownloadCall(fileUrl, filename);
    }

    @Override
    public boolean isInterNetConnected() {
        return isNetworkConnected();
    }

    @Override
    public void showMessage() {
        showMessage(getString(R.string.internet_not_available));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}