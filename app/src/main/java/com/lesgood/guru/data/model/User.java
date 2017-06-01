package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.Serializable;

/**
 * Created by Agus on 4/16/17.
 */

public class User implements Serializable {
    @NonNull
    String uid;
    @Nullable
    String phone;
    @Nullable
    String email;
    @Nullable
    String provider;
    @Nullable
    String photo_url;
    @Nullable
    String full_name;
    @Nullable
    String gender;
    @Nullable
    long birthday;
    @Nullable
    boolean verified;
    @Nullable
    double latitude;
    @Nullable
    double longitude;
    @Nullable
    String fullAddress;
    @Nullable
    int totalSkill;
    @Nullable
    float review;
    @Nullable
    int startFrom;
    @Nullable
    String religion;
    @Nullable
    String pendidikan;
    @Nullable
    boolean active;

    public static User newInstance(FirebaseUser firebaseUser, UserInfo provider) {
        User user = new User(firebaseUser.getUid());
        user.setProvider(provider.getProviderId());
        // TODO : refactoring
        if (provider.getProviderId().equals("password")) {
            user.setEmail(firebaseUser.getEmail());

        } else {

        }

        return user;
    }

    public User() {
    }

    public User(String uid) {
        this.uid = uid;
    }

    public User(String uid, String phone, String email, String provider, String photo_url, String full_name) {
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.provider = provider;
        this.photo_url = photo_url;
        this.full_name = full_name;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(@Nullable String photo_url) {
        this.photo_url = photo_url;
    }

    @Nullable
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(@Nullable String full_name) {
        this.full_name = full_name;
    }

    @Nullable
    public String getProvider() {
        return provider;
    }

    public void setProvider(@Nullable String provider) {
        this.provider = provider;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    public void setGender(@Nullable String gender) {
        this.gender = gender;
    }

    @Nullable
    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable long birthday) {
        this.birthday = birthday;
    }

    @Nullable
    public boolean isVerified() {
        return verified;
    }

    public void setVerified(@Nullable boolean verified) {
        this.verified = verified;
    }

    @Nullable
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable double latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable double longitude) {
        this.longitude = longitude;
    }

    @Nullable
    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(@Nullable String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Nullable
    public int getTotalSkill() {
        return totalSkill;
    }

    public void setTotalSkill(@Nullable int totalSkill) {
        this.totalSkill = totalSkill;
    }

    @Nullable
    public float getReview() {
        return review;
    }

    public void setReview(@Nullable float review) {
        this.review = review;
    }

    @Nullable
    public int getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(@Nullable int startFrom) {
        this.startFrom = startFrom;
    }

    @Nullable
    public String getReligion() {
        return religion;
    }

    public void setReligion(@Nullable String religion) {
        this.religion = religion;
    }

    @Nullable
    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(@Nullable String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public void setActive(@Nullable boolean active) {
        this.active = active;
    }

    @Nullable
    public boolean isActive() {
        return active;
    }
}
