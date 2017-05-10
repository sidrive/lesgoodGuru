package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 5/3/17.
 */

public class Category {
    @NonNull
    String id;
    @Nullable
    String name;
    @NonNull
    String level;

    public Category(){

    }

    public Category(String id, String name, String level){
        this.id = id;
        this.name = name;
        this.level = level;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @NonNull
    public String getLevel() {
        return level;
    }

    public void setLevel(@NonNull String level) {
        this.level = level;
    }
}
