package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 6/13/17.
 */

public class Pengalaman {
    @NonNull
    String id;
    @NonNull
    String title;
    @Nullable
    String sertifikat_url;

    public Pengalaman(){

    }

    public Pengalaman(String id, String title){
        this.id = id;
        this.title = title;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public String getSertifikat_url() {
        return sertifikat_url;
    }

    public void setSertifikat_url(@Nullable String sertifikat_url) {
        this.sertifikat_url = sertifikat_url;
    }
}
