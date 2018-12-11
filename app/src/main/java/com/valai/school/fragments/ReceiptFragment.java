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
import com.valai.school.adapter.ReceiptAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.interfaces.ReceiptGenerate;
import com.valai.school.modal.GetPrintReceiptUrlDownloadPOJO;
import com.valai.school.modal.GetPrintReceiptUrlPOJO;
import com.valai.school.modal.GetReceiptPOJO;
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
import static com.valai.school.utils.AppConstants.FEE_COMMON_PATH;
import static com.valai.school.utils.AppConstants.FEE_RECEIPT_NAME;
import static com.valai.school.utils.AppConstants.MODE_RECEIPT_REGENERATE_LIST;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.ROOT_DOWNLOAD;
import static com.valai.school.utils.AppConstants.ROOT_GROUP_FOLDER;
import static com.valai.school.utils.AppConstants.ROOT_SLASH;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class ReceiptFragment extends BaseFragment implements ReceiptGenerate {
    public static final String TAG = ReceiptFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private ReceiptGenerate receiptGenerate;
    private AnimationItem mSelectedItem;
    private List<GetReceiptPOJO.Datum> listReceipt;
    private List<StudentListPOJO.Datum> studentList;
    private HashMap<Integer, List<GetReceiptPOJO.Datum>> hashMapDataList;
    private ReceiptAdapter receiptAdapter;

    public ReceiptFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    public static ReceiptFragment newInstance(String param1, String param2) {
        ReceiptFragment fragment = new ReceiptFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_receipt, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        receiptGenerate = this;
        listReceipt = new ArrayList<>();
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

        hashMapDataList = fragmentListner.getReceiptList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    listReceipt = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (listReceipt != null && listReceipt.size() > 0) {
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
            getReceiptRequestCall();
        }
    }

    private void setAdapter() {
        if (listReceipt != null && listReceipt.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (receiptAdapter == null) {
                receiptAdapter = new ReceiptAdapter(getContext(), listReceipt, receiptGenerate);
                recycler_view.setAdapter(receiptAdapter);
                runLayoutAnimation(recycler_view, mSelectedItem);
            } else {
                receiptAdapter.notifyDataSetChanged();
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

    public void getReceiptRequestCall() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetReceiptPOJO> call = restClientAPI.getFeeGeneratedReceipt(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getSectionId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                0, MODE_RECEIPT_REGENERATE_LIST);
        call.enqueue(new Callback<GetReceiptPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetReceiptPOJO> call, @NonNull Response<GetReceiptPOJO> response) {
                GetReceiptPOJO getReceiptPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {

                    if (getReceiptPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getReceiptPOJO.getResponseStatus().equals(TRUE)) {
                            listReceipt.addAll(getReceiptPOJO.getData());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                    listReceipt);
                            fragmentListner.setReceiptList(hashMapDataList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getReceiptPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getReceiptPOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetReceiptPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @Override
    public void getPrintReceiptUrlRequestCall(final int pos) {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetPrintReceiptUrlPOJO> call = restClientAPI.getPrintReceiptUrl(listReceipt.get(pos).getOrgId());
        call.enqueue(new Callback<GetPrintReceiptUrlPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetPrintReceiptUrlPOJO> call, @NonNull Response<GetPrintReceiptUrlPOJO> response) {
                GetPrintReceiptUrlPOJO getPrintReceiptUrlPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getPrintReceiptUrlPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getPrintReceiptUrlPOJO.getResponseStatus().equals(TRUE)) {
                            if (!getPrintReceiptUrlPOJO.getData().get(0).getPageApi().equals("")) {
                                getPrintReceiptUrlDownloadRequestCall(getPrintReceiptUrlPOJO.getData().get(0).getPageApi(), pos);
                            }
                        } else {
                            hideLoading();
                            showMessage(getPrintReceiptUrlPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getPrintReceiptUrlPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPrintReceiptUrlPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getPrintReceiptUrlDownloadRequestCall(String appendUrl, int pos) {
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient(appendUrl + "/");

        Call<GetPrintReceiptUrlDownloadPOJO> call = restClientAPI.getReCreateReceiptUrl(listReceipt.get(pos).getOrgId(),
                listReceipt.get(pos).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getClassId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getSectionId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                listReceipt.get(pos).getReceiptCode(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudName());

        call.enqueue(new Callback<GetPrintReceiptUrlDownloadPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetPrintReceiptUrlDownloadPOJO> call, @NonNull Response<GetPrintReceiptUrlDownloadPOJO> response) {
                GetPrintReceiptUrlDownloadPOJO getPrintReceiptUrlDownloadPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getPrintReceiptUrlDownloadPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getPrintReceiptUrlDownloadPOJO.getResponseStatus().equals(TRUE)) {
                            Log.e("PDF", "PATH>>" + ROOT_DOWNLOAD + ROOT_GROUP_FOLDER + FEE_COMMON_PATH + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId() + ROOT_SLASH + FEE_RECEIPT_NAME);
                            hideLoading();
                            downloadFile(ROOT_DOWNLOAD + ROOT_GROUP_FOLDER + FEE_COMMON_PATH + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId() + ROOT_SLASH + FEE_RECEIPT_NAME);
                            //showMessage(getPrintReceiptUrlDownloadPOJO.getResponseMessage());
                        } else {
                            hideLoading();
                            showMessage(getPrintReceiptUrlDownloadPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(getPrintReceiptUrlDownloadPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetPrintReceiptUrlDownloadPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @SuppressLint("InlinedApi")
    private void downloadFile(String fileUrl) {
        fragmentListner.getFileDownloadCall(fileUrl, FEE_RECEIPT_NAME);
    }
}