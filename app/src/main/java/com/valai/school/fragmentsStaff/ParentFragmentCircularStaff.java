package com.valai.school.fragmentsStaff;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.activityAdmin.CircularParentDetailAdmin;
import com.valai.school.activityStaff.CircularParentDetailStaff;
import com.valai.school.adapter.CircularClassAdapter;
import com.valai.school.adapter.CircularClassAdapterStaff;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.interfaces.FragmentListnerCircularAdmin;
import com.valai.school.interfaces.FragmentListnerStaff;
import com.valai.school.modal.GetCircularClassPOJO;
import com.valai.school.modal.ParentCircularModal;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;
import com.valai.school.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class ParentFragmentCircularStaff extends BaseFragment implements FragmentListnerCircularAdmin {
    public static final String TAG = ParentFragmentCircularStaff.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    @BindView(R.id.next)
    Button next_button;

    private FragmentListnerCircularAdmin fragmentListnerCircularAdmin;
    private List<SignInPOJO.Datum> listSignInRes;
    private FragmentListnerStaff fragmentListner;
    private AnimationItem mSelectedItem;
    private List<GetCircularClassPOJO.Datum> getCircularClassPOJOArrayList;
    private ArrayList<ParentCircularModal> stringArrayList = new ArrayList<>();
    private CircularClassAdapterStaff circularClassAdapter;

    public ParentFragmentCircularStaff() {
        // Required empty public constructor
    }

    public static ParentFragmentCircularStaff newInstance(String param1, String param2) {
        ParentFragmentCircularStaff fragment = new ParentFragmentCircularStaff();
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
        View rootView = inflater.inflate(R.layout.fragment_circular, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCircularClassPOJOArrayList = new ArrayList<>();
        fragmentListnerCircularAdmin = this;
        AnimationItem[] mAnimationItems = getAnimationItems();
        //next_button.setVisibility(View.VISIBLE);
        mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.getRecycledViewPool().setMaxRecycledViews(0, 0);
        setAdapter();

        listSignInRes = new ArrayList<>();
        listSignInRes = fragmentListner.getSignInResultList();

        if (!isNetworkConnected()) {
            if (listSignInRes != null && listSignInRes.size() > 0) {
                getCircularClassPOJOArrayList = fragmentListner.getCircularClassList();
                if (getCircularClassPOJOArrayList != null && getCircularClassPOJOArrayList.size() > 0) {
                    setAdapter();
                } else {
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getCircularClassDetails();
        }

        setRecycleItemTouchListener();
    }

    private void setRecycleItemTouchListener() {
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                recycler_view, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    if (getCircularClassPOJOArrayList.get(position).isSelected()) {
                        getCircularClassPOJOArrayList.get(position).setSelected(false);
                        for (int i = 1; i < getCircularClassPOJOArrayList.size(); i++) {
                            getCircularClassPOJOArrayList.get(i).setSelected(false);
                            getCircularClassPOJOArrayList.get(i).setChecked(true);
                        }
                    } else {
                        getCircularClassPOJOArrayList.get(position).setSelected(true);
                        for (int i = 1; i < getCircularClassPOJOArrayList.size(); i++) {
                            getCircularClassPOJOArrayList.get(i).setSelected(true);
                            getCircularClassPOJOArrayList.get(i).setChecked(false);
                        }
                    }

                    setValuesInList(position, getCircularClassPOJOArrayList.get(position).isSelected());
                    circularClassAdapter.notifyDataSetChanged();
                } else {
                    if (!getCircularClassPOJOArrayList.get(0).isSelected()) {
                        if (getCircularClassPOJOArrayList.get(position).isSelected()) {
                            getCircularClassPOJOArrayList.get(position).setSelected(false);
                            getCircularClassPOJOArrayList.get(position).setChecked(true);
                        } else {
                            if (getCircularClassPOJOArrayList.get(position).isChecked()) {
                                getCircularClassPOJOArrayList.get(position).setSelected(true);
                                getCircularClassPOJOArrayList.get(position).setChecked(true);
                            } else {
                                getCircularClassPOJOArrayList.get(position).setSelected(true);
                                getCircularClassPOJOArrayList.get(position).setChecked(true);
                            }
                        }
                        setValuesInList(position, getCircularClassPOJOArrayList.get(position).isSelected());
                        circularClassAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void setValuesInList(int position, boolean isChecked) {
        if (isChecked) {
            ParentCircularModal modal = new ParentCircularModal();
            modal.setTitle(getCircularClassPOJOArrayList.get(position).getClassName());
            modal.setUserId(getCircularClassPOJOArrayList.get(position).getClassId());
            stringArrayList.add(modal);
        } else {
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).getTitle().equals(getCircularClassPOJOArrayList.get(position).getClassName())) {
                    stringArrayList.remove(i);
                }
            }
        }

        setNextButtonVisibility();
    }

    @OnClick(R.id.next)
    public void Next() {
        if (stringArrayList != null && stringArrayList.size() > 0) {
            Intent i = new Intent(getActivity(), CircularParentDetailStaff.class);
            i.putExtra("myList", stringArrayList);
            startActivity(i);
            fragmentListner.getAppPreferenceHelper().isStartIntent(true);
            if (stringArrayList != null) {
                stringArrayList.clear();
            }
            setNextButtonVisibility();
            if (getCircularClassPOJOArrayList != null && getCircularClassPOJOArrayList.size() > 0) {
                for (int j = 0; j < getCircularClassPOJOArrayList.size(); j++) {
                    getCircularClassPOJOArrayList.get(j).setSelected(false);
                    getCircularClassPOJOArrayList.get(j).setChecked(true);
                }
                circularClassAdapter.notifyDataSetChanged();
            }

        } else {
            showMessage(R.string.select_class);
        }
    }

    public void getCircularClassDetails() {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetCircularClassPOJO> call = restClientAPI.getCircularClassDetails(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(), listSignInRes.get(0).getStaffId(),
                listSignInRes.get(0).getUserTypeId(), listSignInRes.get(0).getUserRoleId());
        call.enqueue(new Callback<GetCircularClassPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCircularClassPOJO> call, @NonNull Response<GetCircularClassPOJO> response) {
                GetCircularClassPOJO getCircularClassPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getCircularClassPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getCircularClassPOJO.getResponseStatus().equals(TRUE)) {
                            getCircularClassPOJOArrayList.addAll(getCircularClassPOJO.getData());
                            for (int i = 0; i < getCircularClassPOJOArrayList.size(); i++) {
                                getCircularClassPOJOArrayList.get(i).setChecked(true);
                            }
                            fragmentListner.setCircularClassList(getCircularClassPOJOArrayList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getCircularClassPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getCircularClassPOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCircularClassPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        if (getCircularClassPOJOArrayList != null && getCircularClassPOJOArrayList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            circularClassAdapter = new CircularClassAdapterStaff(getContext(), getCircularClassPOJOArrayList, fragmentListnerCircularAdmin, fragmentListner);
            recycler_view.setAdapter(circularClassAdapter);
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

    @Override
    public void setList(ArrayList<ParentCircularModal> arrayList) {
        if (stringArrayList != null) {
            stringArrayList.clear();
        }
        stringArrayList.addAll(arrayList);
        setNextButtonVisibility();
        fragmentListner.getAppPreferenceHelper().isStartIntent(false);
    }

    private void setNextButtonVisibility() {
        if (stringArrayList.size() > 0) {
            next_button.setVisibility(View.VISIBLE);
        } else {
            next_button.setVisibility(View.GONE);
        }
    }
}