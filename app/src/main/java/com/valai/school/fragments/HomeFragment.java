package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valai.school.R;
import com.valai.school.adapter.CardPagerAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.CardItem;
import com.valai.school.utils.ShadowTransformer;

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

public class HomeFragment extends BaseFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private CardPagerAdapter cardPagerAdapter;
    private FragmentListner fragmentListner;
    private List<SignInPOJO.Datum> listSignInRes;
    private List<StudentListPOJO.Datum> studentList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        studentList = new ArrayList<>();
        listSignInRes = new ArrayList<>();
        listSignInRes = fragmentListner.getSignInResultList();
        if (!isNetworkConnected()) {
            studentList = fragmentListner.getStudentList();
            if (studentList != null && studentList.size() > 0) {
                setAdapter();
            } else {
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getStudentList();
        }
    }

    private void getStudentList() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<StudentListPOJO> call = restClientAPI.getStudentList(listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(), listSignInRes.get(0).getUserName(), listSignInRes.get(0).getUserTypeId());
        call.enqueue(new Callback<StudentListPOJO>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(@NonNull Call<StudentListPOJO> call, @NonNull Response<StudentListPOJO> response) {
                StudentListPOJO studentListPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (studentListPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (studentListPOJO.getResponseStatus().equals(TRUE)) {
                            studentList.addAll(studentListPOJO.getData());
                            fragmentListner.setStudentList(studentList);
                            setAdapter();
                        } else {
                            showMessage(studentListPOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(studentListPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentListPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        cardPagerAdapter = new CardPagerAdapter();
        for (int i = 0; i < studentList.size(); i++) {
            cardPagerAdapter.addCardItem(new CardItem(studentList.get(i).getStudName(),
                            "Class " + studentList.get(i).getClassName(),
                            studentList.get(i).getStudentImage(), studentList.get(i).getOrgId(), studentList.get(i).getAcademicId()),
                    getContext());
        }

        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
        viewPager.setAdapter(cardPagerAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);

        if (fragmentListner.getAppPreferenceHelper().getTopTitlePosition() != -1) {
            setTopTitle(fragmentListner.getAppPreferenceHelper().getTopTitlePosition());
            viewPager.setCurrentItem(fragmentListner.getAppPreferenceHelper().getTopTitlePosition());
            setSchoolInfo(fragmentListner.getAppPreferenceHelper().getTopTitlePosition());
        } else {
            setTopTitle(0);
            viewPager.setCurrentItem(0);
            fragmentListner.getAppPreferenceHelper().setTopTitlePosition(0);
            setSchoolInfo(fragmentListner.getAppPreferenceHelper().getTopTitlePosition());
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragmentListner.getAppPreferenceHelper().setTopTitlePosition(position);
                setTopTitle(position);
                setSchoolInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSchoolInfo(int pos) {
        if (studentList != null && studentList.size() > 0) {
            fragmentListner.setSchoolInfo(studentList.get(pos).getOrgName(),
                    studentList.get(pos).getOrgId(),
                    studentList.get(pos).getOrgLogo());
        }
    }

    private void setTopTitle(int position) {
        String textTopTitle = cardPagerAdapter.getCardList().get(position).getTitle().trim() + "-" +
                cardPagerAdapter.getCardList().get(position).getText().trim().replace("Class ", "");
        fragmentListner.setTopStudentDetails(textTopTitle);
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.card_nav_diary)
    public void onCardDiaryClick() {
        fragmentListner.setOnLoadHome(1, DiaryFragment.TAG);
    }

    @OnClick(R.id.card_nav_message)
    public void onCardMessageClick() {
        fragmentListner.setOnLoadHome(2, MessageFragment.TAG);
    }

    @OnClick(R.id.card_nav_fee)
    public void onCardFeeClick() {
        fragmentListner.setOnLoadHome(3, FeeFragment.TAG);
    }

    @OnClick(R.id.card_nav_attendance)
    public void onCardAttendanceClick() {
        fragmentListner.setOnLoadHome(4, AttendanceFragment.TAG);
    }

    @OnClick(R.id.card_exam_schedule)
    public void onCardExamClick() {
        fragmentListner.setOnLoadHome(5, ExamFragment.TAG);
    }

    @OnClick(R.id.card_nav_transport)
    public void onCardTransportClick() {
        fragmentListner.setOnLoadHome(6, TransportFragment.TAG);
    }

    @OnClick(R.id.card_nav_holiday)
    public void onCardHolidayListClick() {
        fragmentListner.setOnLoadHome(7, HolidayFragment.TAG);
    }
}