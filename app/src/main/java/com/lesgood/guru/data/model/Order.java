package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Agus on 2/24/17.
 */

public class Order {
    @NonNull
    String gid;
    @NonNull
    String uid;
    @NonNull
    String oid;
    @Nullable
    String title;
    @Nullable
    String status;
    @Nullable
    int amount;
    @Nullable
    double fee;
    @Nullable
    double total;
    @Nullable
    long meettime;
    @Nullable
    long ordertime;

    public Order(){

    }

    public Order(String oid){
        this.oid = oid;
    }

    public Order(String gid, String uid, String oid, String title, String status, int amount, long meettime, long ordertime){
        this.gid = gid;
        this.uid = uid;
        this.oid = oid;
        this.title = title;
        this.status = status;
        this.amount = amount;
        this.fee = amount * 0.1;
        this.total = amount+fee;
        this.meettime = meettime;
        this.ordertime = ordertime;
    }


    @NonNull
    public String getGid() {
        return gid;
    }

    public void setGid(@NonNull String gid) {
        this.gid = gid;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    @NonNull
    public String getOid() {
        return oid;
    }

    public void setOid(@NonNull String oid) {
        this.oid = oid;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Nullable
    public int getAmount() {
        return amount;
    }

    public void setAmount(@Nullable int amount) {
        this.amount = amount;
    }

    @Nullable
    public double getFee() {
        return fee;
    }

    public void setFee(@Nullable double fee) {
        this.fee = fee;
    }

    @Nullable
    public double getTotal() {
        return total;
    }

    public void setTotal(@Nullable double total) {
        this.total = total;
    }

    @Nullable
    public long getMeettime() {
        return meettime;
    }

    public void setMeettime(@Nullable long meettime) {
        this.meettime = meettime;
    }

    @Nullable
    public long getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(@Nullable long ordertime) {
        this.ordertime = ordertime;
    }
}
