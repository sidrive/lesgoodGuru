package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 5/10/17.
 */

public class Skill {
    @NonNull
    String code;
    @NonNull
    String uid;
    @Nullable
    String skill;
    @Nullable
    String level;
    @Nullable
    int price;
    @Nullable
    String desc;

    public Skill(){

    }

    public Skill(String code, String uid, String skill, String level, int price, String desc){
        this.code = code;
        this.uid = uid;
        this.skill = skill;
        this.level = level;
        this.price = price;
        this.desc = desc;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    @Nullable
    public String getSkill() {
        return skill;
    }

    public void setSkill(@Nullable String skill) {
        this.skill = skill;
    }

    @Nullable
    public String getLevel() {
        return level;
    }

    public void setLevel(@Nullable String level) {
        this.level = level;
    }

    @Nullable
    public int getPrice() {
        return price;
    }

    public void setPrice(@Nullable int price) {
        this.price = price;
    }

    @Nullable
    public String getDesc() {
        return desc;
    }

    public void setDesc(@Nullable String desc) {
        this.desc = desc;
    }
}
