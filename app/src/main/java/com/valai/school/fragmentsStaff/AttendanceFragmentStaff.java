package com.valai.school.fragmentsStaff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.activityStaff.AttendanceSendStaffActivity;
import com.valai.school.adapter.AttendanceGetClassAdapter;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.interfaces.FragmentListnerStaff;
import com.valai.school.modal.GetClassAttendancePOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class AttendanceFragmentStaff extends BaseFragment {
    public static final String TAG = AttendanceFragmentStaff.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private OnFragmentInteractionListener mListener;
    private FragmentListnerStaff fragmentListner;
    private AnimationItem mSelectedItem;
    private List<SignInPOJO.Datum> listSignInRes;
    private View ChildView;
    private int recyclerViewItemPosition;
    private List<GetClassAttendancePOJO.Datum> getClassAttendanceList;

    public AttendanceFragmentStaff() {
        // Required empty public constructor
    }

    public static AttendanceFragmentStaff newInstance(String param1, String param2) {
        AttendanceFragmentStaff fragment = new AttendanceFragmentStaff();
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
        View rootView = inflater.inflate(R.layout.fragment_assignment_staff, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recycler_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    recyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    Intent i = new Intent(getActivity(), AttendanceSendStaffActivity.class);
                    i.putExtra("classId", getClassAttendanceList.get(recyclerViewItemPosition).getClassId());
                    i.putExtra("branch_Id", getClassAttendanceList.get(recyclerViewItemPosition).getBranchId());
                    i.putExtra("section_Id", getClassAttendanceList.get(recyclerViewItemPosition).getSectionId());
                    i.putExtra("sectionName", getClassAttendanceList.get(recyclerViewItemPosition).getSectionName());
                    startActivity(i);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        listSignInRes = new ArrayList<>();
        listSignInRes = fragmentListner.getSignInResultList();
        getClassAttendanceList = new ArrayList<>();
        setAdapter();

        if (!isNetworkConnected()) {
            if (listSignInRes != null && listSignInRes.size() > 0) {
                getClassAttendanceList = fragmentListner.getClassAttendanceList();
                if (getClassAttendanceList != null && getClassAttendanceList.size() > 0) {
                    setAdapter();
                } else {
                    setAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getClassForAttendance();
        }
    }

    private void getClassForAttendance() {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetClassAttendancePOJO> call = restClientAPI.getSectionDetails(listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(), listSignInRes.get(0).getStaffId(), listSignInRes.get(0).getClassId(),
                listSignInRes.get(0).getUserRoleId(), listSignInRes.get(0).getUserTypeId());

        call.enqueue(new Callback<GetClassAttendancePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetClassAttendancePOJO> call, @NonNull Response<GetClassAttendancePOJO> response) {
                GetClassAttendancePOJO getClassAttendancePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getClassAttendancePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getClassAttendancePOJO.getResponseStatus().equals(TRUE)) {
                            getClassAttendanceList.addAll(getClassAttendancePOJO.getData());
                            fragmentListner.setClassAttendanceList(getClassAttendanceList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getClassAttendancePOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getClassAttendancePOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetClassAttendancePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        if (getClassAttendanceList != null && getClassAttendanceList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            AttendanceGetClassAdapter attendanceGetClassAdapter = new AttendanceGetClassAdapter(getContext(), getClassAttendanceList);
            recycler_view.setAdapter(attendanceGetClassAdapter);
            runLayoutAnimation(recycler_view, mSelectedItem);
        } else {
            recycler_view.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListnerStaff) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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