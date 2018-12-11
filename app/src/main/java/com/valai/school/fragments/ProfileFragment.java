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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.valai.school.R;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.GetStudentDetailsPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.CircleTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.MODE_GET_STUDENT_DETAILS;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.ROOT_DOWNLOAD;
import static com.valai.school.utils.AppConstants.ROOT_GROUP_FOLDER;
import static com.valai.school.utils.AppConstants.ROOT_SLASH;
import static com.valai.school.utils.AppConstants.ROOT_STUDENT_PHOTO_FOLDER;
import static com.valai.school.utils.AppConstants.TRUE;

public class ProfileFragment extends BaseFragment {
    public static final String TAG = ProfileFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.student_image)
    ImageView student_image;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private List<StudentListPOJO.Datum> studentList;
    private List<GetStudentDetailsPOJO.Datum> listGetStudentDetails;
    private HashMap<Integer, List<GetStudentDetailsPOJO.Datum>> hashMapDataList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager.setOffscreenPageLimit(3);
        listGetStudentDetails = new ArrayList<>();
        tabs.setupWithViewPager(viewpager);

        studentList = new ArrayList<>();
        studentList = fragmentListner.getStudentList();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        hashMapDataList = fragmentListner.getStudentProfile();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    listGetStudentDetails = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());

                    if (listGetStudentDetails != null && listGetStudentDetails.size() > 0) {
                        setupViewPager();
                    } else {
                        setupViewPager();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    setupViewPager();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                setupViewPager();
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getStudentProfileRequestCall();
        }
    }

    private class Adapter extends FragmentStatePagerAdapter {
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

    // Request Rest Client API Call For SignIn
    public void getStudentProfileRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetStudentDetailsPOJO> call = restClientAPI.getStudentProfile(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(), MODE_GET_STUDENT_DETAILS);
        call.enqueue(new Callback<GetStudentDetailsPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetStudentDetailsPOJO> call, @NonNull Response<GetStudentDetailsPOJO> response) {
                GetStudentDetailsPOJO getStudentDetailsPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getStudentDetailsPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getStudentDetailsPOJO.getResponseStatus().equals(TRUE)) {
                            listGetStudentDetails.addAll(getStudentDetailsPOJO.getData());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                    listGetStudentDetails);
                            fragmentListner.setStudentProfile(hashMapDataList);

                            if (listGetStudentDetails.size() > 0) {

                                if (!listGetStudentDetails.get(0).getPhoto().equals("")) {
                                    Log.e("PhotoPath", "PhotoPath>>" + ROOT_DOWNLOAD + ROOT_GROUP_FOLDER + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId() + ROOT_STUDENT_PHOTO_FOLDER + studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId() + ROOT_SLASH +
                                            listGetStudentDetails.get(0).getPhoto());

                                    Glide.with(getContext()).load(ROOT_DOWNLOAD + ROOT_GROUP_FOLDER +
                                            studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId() +
                                            ROOT_STUDENT_PHOTO_FOLDER +
                                            studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId() +
                                            ROOT_SLASH +
                                            listGetStudentDetails.get(0).getPhoto())
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                            .error(R.mipmap.student_icon)
                                            .placeholder(R.mipmap.student_icon)
                                            .transform(new CircleTransform(getContext()))
                                            .into(student_image);
                                }
                            }
                            setupViewPager();
                        } else {
                            showMessage(getStudentDetailsPOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(getStudentDetailsPOJO.getResponseMessage());
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetStudentDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setupViewPager();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setupViewPager() {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(PersonalFragment.newInstance(listGetStudentDetails, ""), getString(R.string.personal));
        adapter.addFragment(ParentFragment.newInstance(listGetStudentDetails, ""), getString(R.string.parent));
        adapter.addFragment(HealthFragment.newInstance(listGetStudentDetails, ""), getString(R.string.health));
        viewpager.setAdapter(adapter);
    }
}