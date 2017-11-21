package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sim-x on 11/20/17.
 */

public class TimeSchedule {
  @NonNull
  String id;

  @Nullable
  String time;

  @Nullable
  String day;

  @Nullable
  String key;

  @Nullable
  String day_time;

  @Nullable
  String day_uid;

  public TimeSchedule(){}

  public TimeSchedule(@NonNull String id, String time, String day, String key,
      String day_time, String day_uid) {
    this.id = id;
    this.time = time;
    this.day = day;
    this.key = key;
    this.day_time = day_time;
    this.day_uid = day_uid;
  }

  @Nullable
  public String getKey() {
    return key;
  }

  @Nullable
  public String getDay_uid() {
    return day_uid;
  }

  public void setDay_uid(@Nullable String day_uid) {
    this.day_uid = day_uid;
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

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
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
}
