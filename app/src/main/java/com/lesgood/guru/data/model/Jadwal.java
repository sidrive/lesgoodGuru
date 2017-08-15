package com.lesgood.guru.data.model;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Agus on 6/14/17.
 */

public class Jadwal implements Serializable{
    @Expose @SerializedName("name")
    public String mName;
    @Expose
    @SerializedName("dayOfMonth")
    public int mDayOfMonth;
    @Expose @SerializedName("startTime")
    public long mStartTime;
    @Expose @SerializedName("endTime")
    public long mEndTime;
    @Expose @SerializedName("color")
    public String mColor;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.mDayOfMonth = dayOfMonth;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        this.mStartTime = startTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long endTime) {
        this.mEndTime = endTime;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    @SuppressLint("SimpleDateFormat")
    public WeekViewEvent toWeekViewEvent(){

        // Parse time.
        long start = getStartTime();
        long end  = getEndTime();

        // Initialize start and end time.
        Calendar now = Calendar.getInstance();
        Calendar startTime = (Calendar) now.clone();
        startTime.setTimeInMillis(start);
        startTime.set(Calendar.YEAR, now.get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, getDayOfMonth());
        Calendar endTime = (Calendar) startTime.clone();
        endTime.setTimeInMillis(end);
        endTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));

        // Create an week view event.
        WeekViewEvent weekViewEvent = new WeekViewEvent();
        weekViewEvent.setName(getName());
        weekViewEvent.setStartTime(startTime);
        weekViewEvent.setEndTime(endTime);
        weekViewEvent.setColor(Color.parseColor(getColor()));

        return weekViewEvent;
    }
}
