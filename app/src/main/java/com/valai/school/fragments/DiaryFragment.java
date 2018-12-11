package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetDiaryPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.MODE_GET_PARENT_VIEW;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;

public class DiaryFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    public static final String TAG = DiaryFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.vpPager)
    ViewPager vpPager;

    @BindView(R.id.tvMonthName)
    TextView tvMonthName;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListener;
    private int currentItemPos = 0;
    private List<StudentListPOJO.Datum> studentList;
    private GetDiaryPOJO.Data data = null;
    private HashMap<Integer, GetDiaryPOJO.Data> hashMapDataList;
    private String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public DiaryFragment() {
        // Required empty public constructor
    }

    public static DiaryFragment newInstance(String param1, String param2) {
        DiaryFragment fragment = new DiaryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar _calendar = Calendar.getInstance(Locale.getDefault());
        currentItemPos = _calendar.get(Calendar.MONTH);
        tvMonthName.setText(monthArray[currentItemPos]);

        studentList = new ArrayList<>();
        studentList = fragmentListener.getStudentList();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        hashMapDataList = fragmentListener.getDiary();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    data = hashMapDataList.get(studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (data != null) {
                        setViewPagerAdapter();
                    } else {
                        setViewPagerAdapter();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    setViewPagerAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                setViewPagerAdapter();
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getDiaryRequestCall();
        }
    }

    private void setViewPagerAdapter() {
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(this);
        vpPager.setCurrentItem(currentItemPos);
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
            fragmentListener = (FragmentListner) context;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItemPos = position;
        tvMonthName.setText(monthArray[currentItemPos]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return 12;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            //Log.e("position", "position>>" + position);
            return DiaryPagerFragment.newInstance(data, position);
        }
    }

    @OnClick(R.id.ib_Prev)
    public void onPreviousClick() {
        setPreviousMonth();
    }

    @OnClick(R.id.ib_Next)
    public void onNextClick() {
        setNextMonth();
    }

    @SuppressLint("SetTextI18n")
    protected void setNextMonth() {
//        if (currentItemPos == 11) {
//            return;
//        }
        currentItemPos++;
        if (currentItemPos > 0 && currentItemPos <= 11) {
            tvMonthName.setText(monthArray[currentItemPos]);
            vpPager.setCurrentItem(currentItemPos);
        } else {
            currentItemPos = 0;
            tvMonthName.setText(monthArray[currentItemPos]);
            vpPager.setCurrentItem(currentItemPos);
        }
    }

    @SuppressLint("SetTextI18n")
    protected void setPreviousMonth() {
        currentItemPos--;
        if (currentItemPos >= 0) {
            tvMonthName.setText(monthArray[currentItemPos]);
            vpPager.setCurrentItem(currentItemPos);
        } else {
            currentItemPos = 11;
            tvMonthName.setText(monthArray[currentItemPos]);
            vpPager.setCurrentItem(currentItemPos);
        }
    }

    // Request Rest Client API Call For SignIn
    public void getDiaryRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetDiaryPOJO> call = restClientAPI.getDiaryForParent(
                studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                MODE_GET_PARENT_VIEW);
        call.enqueue(new Callback<GetDiaryPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetDiaryPOJO> call, @NonNull Response<GetDiaryPOJO> response) {
                GetDiaryPOJO getDiaryPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getDiaryPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getDiaryPOJO.getResponseStatus().equals(TRUE)) {
                            data = getDiaryPOJO.getData();
                            hashMapDataList.put(studentList.get(fragmentListener.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(), data);
                            fragmentListener.setDiary(hashMapDataList);
                            setViewPagerAdapter();
                        } else {
                            showMessage(getDiaryPOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(getDiaryPOJO.getResponseMessage());
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDiaryPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setViewPagerAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }
}