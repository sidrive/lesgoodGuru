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
    public String uid;
    @Nullable
    public String phone;
    @Nullable
    public String email;
    @Nullable
    public String provider;
    @Nullable
    public String photo_url;
    @Nullable
    public String full_name;
    @Nullable
    public String gender;
    @Nullable
    public long birthday;
    @Nullable
    public boolean verified;
    @Nullable
    public double latitude;
    @Nullable
    public double longitude;
    @Nullable
    public String fullAddress;
    @Nullable
    public int totalSkill;
    @Nullable
    public float review;
    @Nullable
    public int startFrom;
    @Nullable
    public String religion;
    @Nullable
    public String pendidikan;
    @Nullable
    public String prodi;
    @Nullable
    public boolean active;
    @Nullable
    public String instagram;
    @Nullable
    public String facebook;
    @Nullable
    public long createdAt;
    @Nullable
    public long updateAt;
    @Nullable
    public String location;
    @Nullable
    public String about;
    @Nullable
    public boolean acceptTOS;
    @Nullable
    public String userType;

    @Nullable
    public String token;
    @Nullable
    public int saldo;
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

    public User(String uid, String phone, String email, String provider, String photo_url, String full_name, String token) {
        this.uid = uid;
        this.phone = phone;
        this.email = email;
        this.provider = provider;
        this.photo_url = photo_url;
        this.full_name = full_name;
        this.token = token;
    }

    @Nullable
    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(@Nullable int saldo) {
        this.saldo = saldo;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
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

    @Nullable
    public String getProdi() {
        return prodi;
    }

    public void setProdi(@Nullable String prodi) {
        this.prodi = prodi;
    }

    public void setActive(@Nullable boolean active) {
        this.active = active;
    }

    @Nullable
    public boolean isActive() {
        return active;
    }

    @Nullable
    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(@Nullable String instagram) {
        this.instagram = instagram;
    }

    @Nullable
    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(@Nullable String facebook) {
        this.facebook = facebook;
    }

    @Nullable
    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable long createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(@Nullable long updateAt) {
        this.updateAt = updateAt;
    }

    @Nullable
    public String getLocation() {
        return location;
    }

    public void setLocation(@Nullable String location) {
        this.location = location;
    }

    @Nullable
    public String getAbout() {
        return about;
    }

    public void setAbout(@Nullable String about) {
        this.about = about;
    }

    @Nullable
    public boolean isAcceptTOS() {
        return acceptTOS;
    }

    public void setAcceptTOS(@Nullable boolean acceptTOS) {
        this.acceptTOS = acceptTOS;
    }

    @Nullable
    public String getUserType() {
        return userType;
    }

    public void setUserType(@Nullable String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
            "uid='" + uid + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", provider='" + provider + '\'' +
            ", photo_url='" + photo_url + '\'' +
            ", full_name='" + full_name + '\'' +
            ", gender='" + gender + '\'' +
            ", birthday=" + birthday +
            ", verified=" + verified +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", fullAddress='" + fullAddress + '\'' +
            ", totalSkill=" + totalSkill +
            ", review=" + review +
            ", startFrom=" + startFrom +
            ", religion='" + religion + '\'' +
            ", pendidikan='" + pendidikan + '\'' +
            ", prodi='" + prodi + '\'' +
            ", active=" + active +
            ", instagram='" + instagram + '\'' +
            ", facebook='" + facebook + '\'' +
            ", createdAt=" + createdAt +
            ", updateAt=" + updateAt +
            ", location='" + location + '\'' +
            ", about='" + about + '\'' +
            ", acceptTOS=" + acceptTOS +
            ", userType='" + userType + '\'' +
            '}';
    }
}
