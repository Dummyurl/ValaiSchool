package com.valai.school.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.adapter.MonthCalendarAdapter;
import com.valai.school.interfaces.FragmentListner;
import com.valai.school.modal.CalendarCollection;
import com.valai.school.modal.MonthAttendancePOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;

public class MonthFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public static final String TAG = MonthFragment.class.getSimpleName();
    @BindView(R.id.tv_month)
    TextView tv_month;

    @BindView(R.id.text_present)
    TextView text_present;

    @BindView(R.id.text_absent)
    TextView text_absent;

    @BindView(R.id.text_total)
    TextView text_total;

    @BindView(R.id.gv_calendar)
    GridView gridview;

    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;
    private MonthCalendarAdapter cal_adapter;
    private int month, year;
    private Calendar _calendar;
    private static final String dateTemplate = "MMMM yyyy";
    private static final String dateTemplate_Month = "MMMM-yyyy";
    public static List<String> day_string;
    private List<StudentListPOJO.Datum> studentList;
    private List<MonthAttendancePOJO.Datum> monthList;
    private ArrayList<CalendarCollection> monthCalList;
    private HashMap<Integer, List<MonthAttendancePOJO.Datum>> hashMapDataList;
    int is_present = 0;
    int is_absent = 0;
    int is_halfday = 0;

    public MonthFragment() {
        // Required empty public constructor
    }

    public static MonthFragment newInstance() {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
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
        tv_month.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

        monthList = new ArrayList<>();
        monthCalList = new ArrayList<>();
        studentList = new ArrayList<>();
        studentList = fragmentListner.getStudentList();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        hashMapDataList = fragmentListner.getMonthList();
        if (hashMapDataList == null) {
            hashMapDataList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (studentList.size() > 0) {
                if (hashMapDataList != null && hashMapDataList.size() > 0) {
                    monthList = hashMapDataList.get(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());
                    if (monthList != null && monthList.size() > 0) {
                        monthCalList.addAll(getMonthCalenderList(monthList));
                        setGridAdapter();
                    } else {
                        gridview.setAdapter(cal_adapter);
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    gridview.setAdapter(cal_adapter);
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                gridview.setAdapter(cal_adapter);
                showMessage(getString(R.string.internet_not_available));
            }

        } else if (studentList.size() > 0) {
            getMonthRequestCall();
        }
    }

    private void getMonthRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<MonthAttendancePOJO> call = restClientAPI.getMonthAttendanceReportForParent(
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getOrgId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getAcademicId(),
                studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId());

        call.enqueue(new Callback<MonthAttendancePOJO>() {
            @Override
            public void onResponse(@NonNull Call<MonthAttendancePOJO> call, @NonNull Response<MonthAttendancePOJO> response) {
                MonthAttendancePOJO monthAttendancePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (monthAttendancePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (monthAttendancePOJO.getResponseStatus().equals(TRUE)) {
                            monthList.addAll(monthAttendancePOJO.getData());
                            hashMapDataList.put(studentList.get(fragmentListner.getAppPreferenceHelper().getTopTitlePosition()).getStudentId(),
                                    monthList);
                            fragmentListner.setMonthList(hashMapDataList);
                            monthCalList.addAll(getMonthCalenderList(monthList));
                            setGridAdapter();
                        } else {
                            showMessage(monthAttendancePOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(monthAttendancePOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MonthAttendancePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                //showMessage(getString(R.string.internet_not_available));
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @OnClick(R.id.ib_prev)
    public void onPreviousClick() {
        if (!isNetworkConnected() && monthList == null) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        is_present = 0;
        is_absent = 0;
        is_halfday = 0;
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }

        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        for (int i = 0; i < monthList.size(); i++) {
            String attendanceDate = monthList.get(i).getAttendanceDate();
            String[] parts = attendanceDate.split("-", 2);
            String part1 = parts[0];
            String part2 = parts[1];
            int present = monthList.get(i).getIsPresent();
            if (part2.equals(DateFormat.format(dateTemplate_Month,
                    _calendar.getTime()))) {
                if (present == 1) {
                    is_present++;
                } else if (present == 2) {
                    is_absent++;
                } else {
                    is_halfday++;
                }
            }
        }
        Calendar monthStart = new GregorianCalendar(year, month - 1, 1);
        int total_days = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        int sum_days = is_present + is_halfday;
        text_present.setText("Present :" + " " + String.valueOf(sum_days));
        text_absent.setText("Absent :" + " " + String.valueOf(is_absent));
        int totalDays = sum_days + is_absent;
        text_total.setText("Total :" + " " + totalDays);

        setGridCellAdapterToDate(month, year);
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.Ib_next)
    public void onNextClick() {
        if (!isNetworkConnected() && monthList == null) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        is_present = 0;
        is_absent = 0;
        is_halfday = 0;
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        for (int i = 0; i < monthList.size(); i++) {
            String attendanceDate = monthList.get(i).getAttendanceDate();
            String[] parts = attendanceDate.split("-", 2);
            String part1 = parts[0];
            String part2 = parts[1];
            int present = monthList.get(i).getIsPresent();
            if (part2.equals(DateFormat.format(dateTemplate_Month,
                    _calendar.getTime()))) {
                if (present == 1) {
                    is_present++;
                } else if (present == 2) {
                    is_absent++;
                } else {
                    is_halfday++;
                }
            }
        }
        Calendar monthStart = new GregorianCalendar(year, month - 1, 1);
        int total_days = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        int sum_days = is_present + is_halfday;
        text_present.setText("Present :" + " " + String.valueOf(sum_days));
        text_absent.setText("Absent :" + " " + String.valueOf(is_absent));
        int totalDays = sum_days + is_absent;
        text_total.setText("Total :" + " " + totalDays);
        setGridCellAdapterToDate(month, year);
    }

    private void setGridAdapter() {
        cal_adapter = new MonthCalendarAdapter(getActivity(),
                R.id.date, month, year, monthCalList);
        day_string = new ArrayList<>();
        cal_adapter.notifyDataSetChanged();
        gridview.setAdapter(cal_adapter);
    }

    private void setGridCellAdapterToDate(int month, int year) {
        cal_adapter = new MonthCalendarAdapter(getActivity(),
                R.id.date, month, year, monthCalList);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        tv_month.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        cal_adapter.notifyDataSetChanged();
        gridview.setAdapter(cal_adapter);
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

    @SuppressLint("SetTextI18n")
    @Nullable
    private ArrayList<CalendarCollection> getMonthCalenderList(List<MonthAttendancePOJO.Datum> monthlist) {

        ArrayList<CalendarCollection> monthCalList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatterNew = new SimpleDateFormat("yyyy-MM-dd");

        for (int j = 0; j < monthlist.size(); j++) {
            String attendanceDate = monthlist.get(j).getAttendanceDate();
            String[] parts = attendanceDate.split("-", 2);
            String part1 = parts[0];
            String part2 = parts[1];
            int present = monthlist.get(j).getIsPresent();
            monthCalList.add(new CalendarCollection(attendanceDate, String.valueOf(present)));
            _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
            if (part2.equals(DateFormat.format(dateTemplate_Month,
                    _calendar.getTime()))) {
                if (present == 1) {
                    is_present++;
                } else if (present == 2) {
                    is_absent++;
                } else {
                    is_halfday++;
                }
            }
        }
        Calendar monthStart = new GregorianCalendar(year, month - 1, 1);
        int total_days = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        int sum_days = is_present + is_halfday;
        text_present.setText("Present :" + " " + String.valueOf(sum_days));
        text_absent.setText("Absent :" + " " + String.valueOf(is_absent));
        int totalDays = sum_days + is_absent;
        text_total.setText("Total :" + " " + totalDays);
        return monthCalList;
    }
}