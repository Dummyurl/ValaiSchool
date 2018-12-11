package com.valai.school.fragmentsAdmin;

import android.content.Context;
import android.content.Intent;
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
import com.valai.school.activityAdmin.AssignmentDetailAdmin;
import com.valai.school.adapter.AssignmentSectionAdapter;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.modal.GetAssignmentSectionPOJO;
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

public class AssignmentFragmentAdmin extends BaseFragment {
    public static final String TAG = AssignmentFragmentAdmin.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private View ChildView;
    private int recyclerViewItemPosition;
    private List<SignInPOJO.Datum> listSignInRes;
    private FragmentListnerAdmin fragmentListner;
    private AnimationItem mSelectedItem;
    private List<GetAssignmentSectionPOJO.Datum> getAssignmentSectionPOJOArrayList;

    public AssignmentFragmentAdmin() {
        // Required empty public constructor
    }

    public static AssignmentFragmentAdmin newInstance(String param1, String param2) {
        AssignmentFragmentAdmin fragment = new AssignmentFragmentAdmin();
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
        View rootView = inflater.inflate(R.layout.fragment_assignment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAssignmentSectionPOJOArrayList = new ArrayList<>();
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
                    Intent i = new Intent(getActivity(), AssignmentDetailAdmin.class);
                    i.putExtra("sectionName", getAssignmentSectionPOJOArrayList.get(recyclerViewItemPosition).getSectionName());
                    i.putExtra("sectionId", getAssignmentSectionPOJOArrayList.get(recyclerViewItemPosition).getSectionId());
                    i.putExtra("classId", getAssignmentSectionPOJOArrayList.get(recyclerViewItemPosition).getClassId());
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
        setAdapter();

        listSignInRes = new ArrayList<>();
        listSignInRes = fragmentListner.getSignInResultList();

        if (!isNetworkConnected()) {
            if (listSignInRes != null && listSignInRes.size() > 0) {
                getAssignmentSectionPOJOArrayList = fragmentListner.getAssignmentAdminList();
                if (getAssignmentSectionPOJOArrayList != null && getAssignmentSectionPOJOArrayList.size() > 0) {
                    setAdapter();
                } else {
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getAssignmentSectionDetails();
        }
    }

    public void getAssignmentSectionDetails() {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetAssignmentSectionPOJO> call = restClientAPI.getAssignmentSectionDetails(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(),
                listSignInRes.get(0).getStaffId(),
                listSignInRes.get(0).getClassId(),
                listSignInRes.get(0).getUserTypeId(),
                listSignInRes.get(0).getUserRoleId());
        call.enqueue(new Callback<GetAssignmentSectionPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetAssignmentSectionPOJO> call, @NonNull Response<GetAssignmentSectionPOJO> response) {
                GetAssignmentSectionPOJO getAssignmentSectionPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getAssignmentSectionPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getAssignmentSectionPOJO.getResponseStatus().equals(TRUE)) {
                            getAssignmentSectionPOJOArrayList.addAll(getAssignmentSectionPOJO.getData());
                            fragmentListner.setAssignmentAdminList(getAssignmentSectionPOJOArrayList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getAssignmentSectionPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getAssignmentSectionPOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAssignmentSectionPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        if (getAssignmentSectionPOJOArrayList != null && getAssignmentSectionPOJOArrayList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            AssignmentSectionAdapter assignmentSectionAdapter = new AssignmentSectionAdapter(getContext(), getAssignmentSectionPOJOArrayList);
            recycler_view.setAdapter(assignmentSectionAdapter);
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
            fragmentListner = (FragmentListnerAdmin) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}