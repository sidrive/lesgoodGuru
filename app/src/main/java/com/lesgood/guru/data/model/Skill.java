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
    int price1;
    @Nullable
    int price2;
    @Nullable
    int price3;
    @Nullable
    int price4;
    @Nullable
    int price5;
    @Nullable
    boolean have20;
    @Nullable
    boolean have30;
    @Nullable
    String desc;
    @Nullable
    String how;
    @Nullable
    String fasility;

    public Skill(){

    }

    public Skill(String code, String uid, String skill, String level, String desc){
        this.code = code;
        this.uid = uid;
        this.skill = skill;
        this.level = level;
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
    public int getPrice1() {
        return price1;
    }

    public void setPrice1(@Nullable int price1) {
        this.price1 = price1;
    }

    @Nullable
    public int getPrice2() {
        return price2;
    }

    public void setPrice2(@Nullable int price2) {
        this.price2 = price2;
    }

    @Nullable
    public int getPrice3() {
        return price3;
    }

    public void setPrice3(@Nullable int price3) {
        this.price3 = price3;
    }

    @Nullable
    public int getPrice4() {
        return price4;
    }

    public void setPrice4(@Nullable int price4) {
        this.price4 = price4;
    }

    @Nullable
    public int getPrice5() {
        return price5;
    }

    public void setPrice5(@Nullable int price5) {
        this.price5 = price5;
    }

    @Nullable
    public boolean isHave20() {
        return have20;
    }

    public void setHave20(@Nullable boolean have20) {
        this.have20 = have20;
    }

    @Nullable
    public boolean isHave30() {
        return have30;
    }

    public void setHave30(@Nullable boolean have30) {
        this.have30 = have30;
    }

    @Nullable
    public String getDesc() {
        return desc;
    }

    public void setDesc(@Nullable String desc) {
        this.desc = desc;
    }

    @Nullable
    public String getHow() {
        return how;
    }

    public void setHow(@Nullable String how) {
        this.how = how;
    }

    @Nullable
    public String getFasility() {
        return fasility;
    }

    public void setFasility(@Nullable String fasility) {
        this.fasility = fasility;
    }

    @Override
    public String toString() {
        return "Skill{" +
            "code='" + code + '\'' +
            ", uid='" + uid + '\'' +
            ", skill='" + skill + '\'' +
            ", level='" + level + '\'' +
            ", price1=" + price1 +
            ", price2=" + price2 +
            ", price3=" + price3 +
            ", price4=" + price4 +
            ", price5=" + price5 +
            ", have20=" + have20 +
            ", have30=" + have30 +
            ", desc='" + desc + '\'' +
            ", how='" + how + '\'' +
            ", fasility='" + fasility + '\'' +
            '}';
    }
}
