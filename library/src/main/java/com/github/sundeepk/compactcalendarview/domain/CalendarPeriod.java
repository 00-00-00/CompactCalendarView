package com.github.sundeepk.compactcalendarview.domain;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Arjun on 21-10-2015.
 */
public class CalendarPeriod {


    private HashMap<Integer, DateHolder> period = new HashMap<>();

    private final int color;

    private class DateHolder {
    private final long startTimeInMillis;
    private final long stopTimeInMillis;

        public DateHolder(long startTimeInMillis, long stopTimeInMillis) {
            this.startTimeInMillis = startTimeInMillis;
            this.stopTimeInMillis = stopTimeInMillis;
        }

        public long getStartTimeInMillis() {
            return startTimeInMillis;
        }

        public long getStopTimeInMillis() {
            return stopTimeInMillis;
        }
    }

    public long getStartPeriodforMonth(int month)
    {
        DateHolder c = period.get(month);
        if(c!=null)
            return c.getStartTimeInMillis();
        else
            return -1;
    }

    public long getStopPeriodforMonth(int month)
    {
        DateHolder c = period.get(month);
        if(c!=null)
            return c.getStopTimeInMillis();
        else
            return -1;
    }

    public CalendarPeriod(long startTimeInMillis, long stopTimeInMillis, int color) {
        this.color = color;
        getCalendarPeriod(startTimeInMillis,stopTimeInMillis);
    }

    void getCalendarPeriod(long startTimeInMillis, long stopTimeInMillis) {


        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(new Date(startTimeInMillis));
        int startMonth = startCalendar.get(Calendar.MONTH);

        Calendar stopCalendar = Calendar.getInstance();
        stopCalendar.setTime(new Date(stopTimeInMillis));
        int stopMonth = startCalendar.get(Calendar.MONTH);

        if(stopMonth > startMonth)
        {
            Calendar inter = Calendar.getInstance();
            inter.set(Calendar.DAY_OF_MONTH, startCalendar.getActualMaximum(Calendar.MONTH));
            period.put(startMonth, new DateHolder(startCalendar.getTimeInMillis(), inter.getTimeInMillis()));
            startMonth++;
            startCalendar.set(Calendar.MONTH,startMonth);
            getCalendarPeriod(startCalendar.getTimeInMillis(),stopTimeInMillis);
        }
        else if(stopMonth == startMonth)
        {
            period.put(startMonth, new DateHolder(startCalendar.getTimeInMillis(),stopTimeInMillis));
        }
        else
        {
            Log.e("Calendar : ","End time cannot be smaller than Start time");
        }

    }

    public HashMap<Integer, DateHolder> getPeriod() {
        return period;
    }

    public int getColor() {
        return color;
    }
}
