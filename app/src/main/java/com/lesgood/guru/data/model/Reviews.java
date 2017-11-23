package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ikun on 22/11/17.
 */

public class Reviews {

    @NonNull
    String id;

    @NonNull
    String title;

    @Nullable
    float rating;

    @NonNull
    String reviewer;


    public Reviews(){

    }

    public Reviews(String id, String title, float rating, String reviewer){

        this.id = id;
        this.title = title;
        this.rating  = rating;
        this.reviewer   = reviewer;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public float getRating() {
        return rating;
    }

    public void setRating(@Nullable float rating) {
        this.rating = rating;
    }

    @Nullable
    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(@Nullable String reviewer) {
        this.reviewer = reviewer;
    }

}
