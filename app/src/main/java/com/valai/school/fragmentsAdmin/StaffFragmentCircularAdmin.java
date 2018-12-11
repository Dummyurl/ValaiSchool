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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.activityAdmin.CircularStaffDetailAdmin;
import com.valai.school.adapter.CircularStaffAdapter;
import com.valai.school.fragments.BaseFragment;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.interfaces.FragmentListnerCircularAdmin;
import com.valai.school.modal.GetCircularStaffPOJO;
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

public class StaffFragmentCircularAdmin extends BaseFragment implements FragmentListnerCircularAdmin {
    public static final String TAG = StaffFragmentCircularAdmin.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.next)
    Button next_button;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    private FragmentListnerCircularAdmin fragmentListnerCircularAdmin;
    private List<SignInPOJO.Datum> listSignInRes;
    private FragmentListnerAdmin fragmentListner;
    private AnimationItem mSelectedItem;
    private List<GetCircularStaffPOJO.Datum> getCircularStaffPOJOArrayList;
    private ArrayList<ParentCircularModal> stringArrayList = new ArrayList<>();
    private CircularStaffAdapter circularStaffAdapter;

    public StaffFragmentCircularAdmin() {
        // Required empty public constructor
    }

    public static StaffFragmentCircularAdmin newInstance(String param1, String param2) {
        StaffFragmentCircularAdmin fragment = new StaffFragmentCircularAdmin();
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
        getCircularStaffPOJOArrayList = new ArrayList<>();
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
                getCircularStaffPOJOArrayList = fragmentListner.getCircularStaffList();
                if (getCircularStaffPOJOArrayList != null && getCircularStaffPOJOArrayList.size() > 0) {
                    setAdapter();
                } else {
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getCircularStaffDetails();
        }

        setRecycleItemTouchListener();
    }

    private void setRecycleItemTouchListener() {
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                recycler_view, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    if (getCircularStaffPOJOArrayList.get(position).isSelected()) {
                        getCircularStaffPOJOArrayList.get(position).setSelected(false);
                        for (int i = 1; i < getCircularStaffPOJOArrayList.size(); i++) {
                            getCircularStaffPOJOArrayList.get(i).setSelected(false);
                            getCircularStaffPOJOArrayList.get(i).setChecked(true);
                        }
                    } else {
                        getCircularStaffPOJOArrayList.get(position).setSelected(true);
                        for (int i = 1; i < getCircularStaffPOJOArrayList.size(); i++) {
                            getCircularStaffPOJOArrayList.get(i).setSelected(true);
                            getCircularStaffPOJOArrayList.get(i).setChecked(false);
                        }
                    }

                    setValuesInList(position, getCircularStaffPOJOArrayList.get(position).isSelected());
                    circularStaffAdapter.notifyDataSetChanged();
                } else {
                    if (!getCircularStaffPOJOArrayList.get(0).isSelected()) {
                        if (getCircularStaffPOJOArrayList.get(position).isSelected()) {
                            getCircularStaffPOJOArrayList.get(position).setSelected(false);
                            getCircularStaffPOJOArrayList.get(position).setChecked(true);
                        } else {
                            if (getCircularStaffPOJOArrayList.get(position).isChecked()) {
                                getCircularStaffPOJOArrayList.get(position).setSelected(true);
                                getCircularStaffPOJOArrayList.get(position).setChecked(true);
                            } else {
                                getCircularStaffPOJOArrayList.get(position).setSelected(true);
                                getCircularStaffPOJOArrayList.get(position).setChecked(true);
                            }
                        }
                        setValuesInList(position, getCircularStaffPOJOArrayList.get(position).isSelected());
                        circularStaffAdapter.notifyDataSetChanged();
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
            modal.setTitle(getCircularStaffPOJOArrayList.get(position).getTeacherName());
            modal.setUserId(getCircularStaffPOJOArrayList.get(position).getStaffId());
            stringArrayList.add(modal);
        } else {
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).getTitle().equals(getCircularStaffPOJOArrayList.get(position).getTeacherName())) {
                    stringArrayList.remove(i);
                }
            }
        }

        setNextButtonVisibility();
    }

    @OnClick(R.id.next)
    public void Next() {
        if (stringArrayList != null && stringArrayList.size() > 0) {
            Intent i = new Intent(getActivity(), CircularStaffDetailAdmin.class);
            i.putExtra("myList", stringArrayList);
            startActivity(i);
            fragmentListner.getAppPreferenceHelper().isStartIntent(true);
            if (stringArrayList != null) {
                stringArrayList.clear();
            }
            setNextButtonVisibility();
            if (getCircularStaffPOJOArrayList != null && getCircularStaffPOJOArrayList.size() > 0) {
                for (int j = 0; j < getCircularStaffPOJOArrayList.size(); j++) {
                    getCircularStaffPOJOArrayList.get(j).setSelected(false);
                    getCircularStaffPOJOArrayList.get(j).setChecked(true);
                }
                circularStaffAdapter.notifyDataSetChanged();
            }

        } else {
            showMessage(R.string.select_class);
        }
    }

    public void getCircularStaffDetails() {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetCircularStaffPOJO> call = restClientAPI.getCircularStaffDetails(
                listSignInRes.get(0).getOrgId());
        call.enqueue(new Callback<GetCircularStaffPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCircularStaffPOJO> call, @NonNull Response<GetCircularStaffPOJO> response) {
                GetCircularStaffPOJO getCircularStaffPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getCircularStaffPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getCircularStaffPOJO.getResponseStatus().equals(TRUE)) {
                            getCircularStaffPOJOArrayList.addAll(getCircularStaffPOJO.getData());
                            for (int i = 0; i < getCircularStaffPOJOArrayList.size(); i++) {
                                getCircularStaffPOJOArrayList.get(i).setChecked(true);
                            }
                            fragmentListner.setCircularStaffList(getCircularStaffPOJOArrayList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getCircularStaffPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getCircularStaffPOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCircularStaffPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        if (getCircularStaffPOJOArrayList != null && getCircularStaffPOJOArrayList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            circularStaffAdapter = new CircularStaffAdapter(getContext(), getCircularStaffPOJOArrayList, fragmentListnerCircularAdmin, fragmentListner);
            recycler_view.setAdapter(circularStaffAdapter);
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