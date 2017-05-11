package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 5/11/17.
 */

public class Event {
    @NonNull
    String oid;
    @Nullable
    String name;
    @Nullable
    long startTime;
    @Nullable
    long endTime;
    @Nullable
    String color;

    @NonNull
    public String getOid() {
        return oid;
    }

    public void setOid(@NonNull String oid) {
        this.oid = oid;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
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

    @Nullable
    public String getColor() {
        return color;
    }

    public void setColor(@Nullable String color) {
        this.color = color;
    }
}
