package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valai.school.R;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetAttendancePOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;

public class AttendanceFragment extends BaseFragment {
    public static final String TAG = AttendanceFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private List<StudentListPOJO.Datum> studentList;
    private List<GetAttendancePOJO.Datum> listAttendance;
    private HashMap<Integer, List<GetAttendancePOJO.Datum>> hashMapDataList;

    public AttendanceFragment() {
        // Required empty public constructor
    }

    public static AttendanceFragment newInstance(String param1, String param2) {
        AttendanceFragment fragment = new AttendanceFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(viewpager);
        listAttendance = new ArrayList<>();
        studentList = new ArrayList<>();
        studentList = fragmentListner.getStudentList();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        setupViewPager();

        hashMapDataList = fragmentListner.getAttendanceList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    listAttendance = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (listAttendance != null && listAttendance.size() > 0) {
                        setupViewPager();
                    } else {
                        setupViewPager();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    setupViewPager();
                }

            } else {
                setupViewPager();
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getAttendanceRequestCall();
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

    private void setupViewPager() {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(MonthFragment.newInstance(), "Month");
        adapter.addFragment(YearFragment.newInstance(listAttendance, ""), "Year");

        viewpager.setAdapter(adapter);
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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

    private void getAttendanceRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetAttendancePOJO> call = restClientAPI.getAttendanceReportForParent(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(), studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());

        call.enqueue(new Callback<GetAttendancePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetAttendancePOJO> call, @NonNull Response<GetAttendancePOJO> response) {
                GetAttendancePOJO getAttendancePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getAttendancePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getAttendancePOJO.getResponseStatus().equals(TRUE)) {
                            if (getAttendancePOJO.getData() != null && getAttendancePOJO.getData().size() > 0) {
                                listAttendance.addAll(getAttendancePOJO.getData());
                                hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                        listAttendance);
                                fragmentListner.setAttendanceList(hashMapDataList);
                                setupViewPager();
                            } else {
                                setupViewPager();
                            }
                        } else {
                            setupViewPager();
                            showMessage(getAttendancePOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        setupViewPager();
                        showMessage(getAttendancePOJO.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAttendancePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setupViewPager();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }
}