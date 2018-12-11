package com.valai.school.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.valai.school.R;
import com.valai.school.modal.CalendarCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static java.util.Calendar.DAY_OF_WEEK;

/**
 * @author by Mohit Arora on 31/1/18.
 */
public class MonthCalendarAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String tag = MonthCalendarAdapter.class.getSimpleName();
    private final Context _context;

    private final List<String> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdays = new String[]{"Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private TextView date;
    private TextView color;
    private TextView num_events_per_day;
    private final HashMap<String, Integer> eventsPerMonthMap;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "dd-MMM-yyyy");
    private ArrayList<CalendarCollection> date_collection_arr;
    private Date MyDate;

    // Days in Current Month
    public MonthCalendarAdapter(Context context, int textViewResourceId,
                                int month, int year, ArrayList<CalendarCollection> date_collection_arr) {
        super();
        this._context = context;
        this.list = new ArrayList<String>();
        this.date_collection_arr = date_collection_arr;
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(DAY_OF_WEEK));
        printMonth(month, year);
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }

    private String getMonthAsString(int i) {
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            list.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i)
                    + "-GREY"
                    + "-"
                    + getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);
        }
        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            Log.d(currentMonthName, String.valueOf(i) + " "
                    + getMonthAsString(currentMonth) + " " + yy);
            if (i == getCurrentDayOfMonth()) {
                list.add(String.valueOf(i) + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            } else {
                list.add(String.valueOf(i) + "-"
                        + getMonthAsString(currentMonth) + "-" + yy);
            }
        }
        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
            list.add(String.valueOf(i + 1) + "-GREY" + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }

    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                int month) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.cal_item, parent, false);
        }

        // Get a reference to the Day gridcell
        date = (TextView) row.findViewById(R.id.date);
        color = (TextView) row.findViewById(R.id.color);
        date.setOnClickListener(this);
        date.setTag(position);
        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
            if (eventsPerMonthMap.containsKey(theday)) {

            }
        }

        date.setText(theday);
        date.setTextColor(Color.BLACK);
        for (int i = 0; i < date_collection_arr.size(); i++) {
            CalendarCollection cal_obj = date_collection_arr.get(i);
            String date = cal_obj.date;

            if (list.get(position).equals(date)) {
                color.setBackgroundColor(Color.GREEN);
                if (cal_obj.event_message.equals("1") || cal_obj.event_message.equals("3")) {
                    color.setBackgroundColor(Color.GREEN);
                } else {
                    color.setBackgroundColor(Color.RED);
                }

            }
        }
        if (day_color[1].equals("GREY")) {
            date.setTextColor(Color.LTGRAY);
        }

        if (!list.get(position).contains("GREY")) {

            for (int i = 0; i < list.size(); i++) {
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    MyDate = newDateFormat.parse(list.get(i));
                    newDateFormat.applyPattern("EEEE d MMM yyyy");
                    String MyDate1 = newDateFormat.format(MyDate);
                    String match_date;
                    if (MyDate1.contains("Sunday")) {
                        @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat("EEEE d MMM yyyy").parse(MyDate1);
                        @SuppressLint("SimpleDateFormat") String change_date = new SimpleDateFormat("dd-MMMM-yyyy").format(date1);
                        String[] separated = change_date.split("-");
                        String date_ = separated[0];
                        String month = separated[1];
                        String year = separated[2];
                        if (Integer.parseInt(date_) < 10) {
                            match_date = date_.substring(1) + "-" + month + "-" + year;
                        } else {
                            match_date = date_ + "-" + month + "-" + year;
                        }
                        if (list.get(position).equals(match_date)) {
                            date.setTextColor(Color.parseColor("#d50000"));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        Date current_date = new Date();
        String[] parts = dateFormat.format(current_date).split("-", 2);
        String part1 = parts[0];
        String part2 = parts[1];
        String new_date;
        if (Integer.parseInt(part1) < 10) {
            new_date = part1.substring(1) + "-" + part2;
        } else {
            new_date = part1 + "-" + part2;
        }
        if (list.get(position).equals(new_date)) {
            date.setBackgroundResource(R.drawable.current_circle);
            date.setTextColor(Color.WHITE);
        }
        return row;
    }

    @Override
    public void onClick(View view) {
//        String date_month_year = (String) view.getTag();
//        try {
//            Date parsedDate = dateFormatter.parse(date_month_year);
//            Log.d(tag, "Parsed Date: " + parsedDate.toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    private int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    private void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }
}