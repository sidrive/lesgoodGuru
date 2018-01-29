package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sim-x on 11/20/17.
 */

public class TimeSchedule {
  @NonNull
  String id;
  @NonNull
  String schedule_id;
  @Nullable
  String time;

  @Nullable
  String day;

  @Nullable
  String dayFormated;

  @Nullable
  String key;

  @Nullable
  String day_time;

  @Nullable
  String day_uid;

  @Nullable
  long startTime;
  @Nullable
  long endTime;

  public TimeSchedule(){}

  public TimeSchedule(@NonNull String id, @NonNull String schedule_id, String time,
      String day, String dayFormated, String key, String day_time, String day_uid, long startTime,
      long endTime) {
    this.id = id;
    this.schedule_id = schedule_id;
    this.time = time;
    this.day = day;
    this.dayFormated = dayFormated;
    this.key = key;
    this.day_time = day_time;
    this.day_uid = day_uid;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  @NonNull
  public String getSchedule_id() {
    return schedule_id;
  }

  public void setSchedule_id(@NonNull String schedule_id) {
    this.schedule_id = schedule_id;
  }

  @Nullable
  public String getTime() {
    return time;
  }

  public void setTime(@Nullable String time) {
    this.time = time;
  }

  @Nullable
  public String getDay() {
    return day;
  }

  public void setDay(@Nullable String day) {
    this.day = day;
  }

  @Nullable
  public String getDayFormated() {
    return dayFormated;
  }

  public void setDayFormated(@Nullable String dayFormated) {
    this.dayFormated = dayFormated;
  }

  @Nullable
  public String getKey() {
    return key;
  }

  public void setKey(@Nullable String key) {
    this.key = key;
  }

  @Nullable
  public String getDay_time() {
    return day_time;
  }

  public void setDay_time(@Nullable String day_time) {
    this.day_time = day_time;
  }

  @Nullable
  public String getDay_uid() {
    return day_uid;
  }

  public void setDay_uid(@Nullable String day_uid) {
    this.day_uid = day_uid;
  }

  @Nullable
  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(@Nullable long startTime) {
    this.startTime = startTime;
  }

  @Nullable
  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(@Nullable long endTime) {
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "TimeSchedule{" +
        "id='" + id + '\'' +
        ", schedule_id='" + schedule_id + '\'' +
        ", time='" + time + '\'' +
        ", day='" + day + '\'' +
        ", dayFormated='" + dayFormated + '\'' +
        ", key='" + key + '\'' +
        ", day_time='" + day_time + '\'' +
        ", day_uid='" + day_uid + '\'' +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        '}';
  }
}
