package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Agus on 3/3/17.
 */

public class Location implements Serializable{
    @NonNull
    String uid;
    @Nullable
    String name;
    @Nullable
    double lat;
    @Nullable
    double lng;
    @Nullable
    String address;
    @Nullable
    String address_2;
    @Nullable
    String kabupaten_id;
    @Nullable
    String kabupaten_name;
    @Nullable
    String province_id;
    @Nullable
    String province_name;
    @Nullable
    String zip_code;

    public Location(){

    }
    public Location(String uid){
        this.uid = uid;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getKabupaten_id() {
        return kabupaten_id;
    }

    public void setKabupaten_id(String kabupaten_id) {
        this.kabupaten_id = kabupaten_id;
    }

    public String getKabupaten_name() {
        return kabupaten_name;
    }

    public void setKabupaten_name(String kabupaten_name) {
        this.kabupaten_name = kabupaten_name;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
