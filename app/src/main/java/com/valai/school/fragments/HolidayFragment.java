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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.HolidayCalendarAdapter;
import com.valai.school.adapter.HolidayListAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.CalendarCollection;
import com.valai.school.modal.GetHolidaysPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.utils.AnimationItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import static com.valai.school.utils.AppConstants.MODE_HOLIDAY_REPORT;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class HolidayFragment extends BaseFragment {
    public static final String TAG = HolidayFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.tv_month)
    TextView tv_month;

    @BindView(R.id.gv_calendar)
    GridView gridview;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.llHolidays)
    LinearLayout llHolidays;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private int month, year;
    private Calendar _calendar;
    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private AnimationItem mSelectedItem;
    private GregorianCalendar cal_month;
    private HolidayCalendarAdapter cal_adapter;
    private HolidayListAdapter holidayListAdapter;
    private List<StudentListPOJO.Datum> studentList;
    private List<GetHolidaysPOJO.Datum> holidaysList;
    private HashMap<Integer, List<GetHolidaysPOJO.Datum>> hashMapDataList;
    private ArrayList<CalendarCollection> holidaysCalList;
    public static List<String> day_string;
    private static final String dateTemplate = "MMMM yyyy";

    public HolidayFragment() {
        // Required empty public constructor
    }

    public static HolidayFragment newInstance(String param1, String param2) {
        HolidayFragment fragment = new HolidayFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_holiday, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        holidaysList = new ArrayList<>();
        holidaysCalList = new ArrayList<>();

        //setGridAdapter();

        tv_month.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

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

        hashMapDataList = fragmentListner.getHolidayList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    holidaysList = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());

                    if (holidaysList != null && holidaysList.size() > 0) {
                        setAdapter();
                        holidaysCalList.addAll(getHolidaysCalenderList(holidaysList));
                        setGridAdapter();
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
            getHolidaysRequestCall();
        }
    }

    @OnClick(R.id.ib_prev)
    public void onPreviousClick() {
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        setGridCellAdapterToDate(month, year);
    }

    @OnClick(R.id.Ib_next)
    public void onNextClick() {
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }
        setGridCellAdapterToDate(month, year);
    }


    private void setGridCellAdapterToDate(int month, int year) {
        cal_adapter = new HolidayCalendarAdapter(getActivity(),
                R.id.date, month, year, holidaysCalList);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        tv_month.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        cal_adapter.notifyDataSetChanged();
        gridview.setAdapter(cal_adapter);
    }


    private void setGridAdapter() {
        cal_adapter = new HolidayCalendarAdapter(getActivity(),
                R.id.date, month, year, holidaysCalList);
        day_string = new ArrayList<>();
        cal_adapter.notifyDataSetChanged();
        gridview.setAdapter(cal_adapter);
    }

    private void setAdapter() {
        if (holidaysList != null && holidaysList.size() > 0) {
            llHolidays.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (holidayListAdapter == null) {
                holidayListAdapter = new HolidayListAdapter(getContext(), holidaysList);
                recycler_view.setAdapter(holidayListAdapter);
                runLayoutAnimation(recycler_view, mSelectedItem);
            } else {
                holidayListAdapter.notifyDataSetChanged();
                runLayoutAnimation(recycler_view, mSelectedItem);
            }
        } else {
            llHolidays.setVisibility(View.GONE);
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
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void getHolidaysRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetHolidaysPOJO> call = restClientAPI.getStudentHolidayReport(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(), MODE_HOLIDAY_REPORT);
        call.enqueue(new Callback<GetHolidaysPOJO>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(@NonNull Call<GetHolidaysPOJO> call, @NonNull Response<GetHolidaysPOJO> response) {
                GetHolidaysPOJO getHolidaysPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getHolidaysPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getHolidaysPOJO.getResponseStatus().equals(TRUE)) {
                            holidaysList.addAll(getHolidaysPOJO.getData());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(), holidaysList);
                            fragmentListner.setHolidayList(hashMapDataList);
                            setAdapter();
                            holidaysCalList.addAll(getHolidaysCalenderList(holidaysList));
                            setGridAdapter();
                        } else {
                            showMessage(getHolidaysPOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(getHolidaysPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetHolidaysPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @Nullable
    private ArrayList<CalendarCollection> getHolidaysCalenderList(List<GetHolidaysPOJO.Datum> holidaysList) {

        ArrayList<CalendarCollection> holidaysCalList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterNew = new SimpleDateFormat("yyyy-MM-dd");

        for (int j = 0; j < holidaysList.size(); j++) {
            String str_date = holidaysList.get(j).getHolidayStart();
            String end_date = holidaysList.get(j).getHolidayEnd();
            String isActive;
            if (holidaysList.get(j).getIsActive() == 1) {
                isActive = "Active";
            } else {
                isActive = "InActive";
            }

            List<Date> dates = new ArrayList<>();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate;
            Date endDate;
            try {
                startDate = formatter.parse(str_date);
                endDate = formatter.parse(end_date);
                long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
                long endTime = endDate.getTime(); // create your end time here, possibly using Calendar or Date
                long curTime = startDate.getTime();
                while (curTime <= endTime) {
                    dates.add(new Date(curTime));
                    curTime += interval;
                }
                for (int i = 0; i < dates.size(); i++) {
                    Date lDate = dates.get(i);
                    String ds = formatterNew.format(lDate);
                    holidaysCalList.add(new CalendarCollection(ds, isActive));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return holidaysCalList;
    }
}
