package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 5/1/17.
 */

public class OTPdata {
    @NonNull
    String key;
    @Nullable
    String kode;
    @Nullable
    String phone;
    @Nullable
    String status;

    public OTPdata(){

    }

    public OTPdata(String key, String kode, String phone, String status){
        this.key = key;
        this.kode = kode;
        this.phone = phone;
        this.status = status;
    }


    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    @Nullable
    public String getKode() {
        return kode;
    }

    public void setKode(@Nullable String kode) {
        this.kode = kode;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }
}
