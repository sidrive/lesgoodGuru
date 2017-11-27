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
    String code;
    @Nullable
    String title;
    @Nullable
    String status;
    @Nullable
    int amount;
    @Nullable
    int totalSiswa;
    @Nullable
    int totalPertemuan;
    @Nullable
    double fee;
    @Nullable
    double total;
    @Nullable
    List<Jadwal> pertemuan;
    @Nullable
    long ordertime;
    @Nullable
    long pertemuanTime;
    @Nullable
    double latitude;
    @Nullable
    double longitude;
    @Nullable
    String detailLocation;
    @Nullable
    String phone;
    @Nullable
    long createdAt;
    @Nullable
    long updatedAt;
    @Nullable
    String paket;
    @Nullable
    double discount;
    @Nullable
    String customerName;
    @Nullable
    String customerPhone;
    @Nullable
    String customerEmail;
    @Nullable
    String guruEmail;
    @Nullable
    String guruPhone;
    @Nullable
    String guruName;
    @Nullable
    String reference;
    @Nullable
    String paymentUrl;
    @Nullable
    String statusPayment;

    @Nullable
    int tarif;

    @Nullable
    String orderType;

    public Order(){

    }

    public Order(String oid, String code, String title){
        this.oid = oid;
        this.code = code;
        this.title = title;
    }

    public Order(String gid, String uid, String oid, String title, String status, int amount, List<Jadwal> pertemuan, long ordertime){
        this.gid = gid;
        this.uid = uid;
        this.oid = oid;
        this.title = title;
        this.status = status;
        this.amount = amount;
        this.fee = amount * 0.5;
        this.total = amount+fee;
        this.pertemuan = pertemuan;
        this.ordertime = ordertime;
    }

    @Nullable
    public String getReference() {
        return reference;
    }

    public void setReference(@Nullable String reference) {
        this.reference = reference;
    }

    @Nullable
    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(@Nullable String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    @Nullable
    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(@Nullable String statusPayment) {
        this.statusPayment = statusPayment;
    }

    @Nullable
    public int getTarif() {
        return tarif;
    }

    public void setTarif(@Nullable int tarif) {
        this.tarif = tarif;
    }

    @Nullable
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(@Nullable String orderType) {
        this.orderType = orderType;
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
    public long getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(@Nullable long ordertime) {
        this.ordertime = ordertime;
    }

    @Nullable
    public int getTotalSiswa() {
        return totalSiswa;
    }

    public void setTotalSiswa(@Nullable int totalSiswa) {
        this.totalSiswa = totalSiswa;
    }

    @Nullable
    public int getTotalPertemuan() {
        return totalPertemuan;
    }

    public void setTotalPertemuan(@Nullable int totalPertemuan) {
        this.totalPertemuan = totalPertemuan;
    }

    @Nullable
    public List<Jadwal> getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(@Nullable List<Jadwal> pertemuan) {
        this.pertemuan = pertemuan;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    public void setCode(@Nullable String code) {
        this.code = code;
    }

    @Nullable
    public long getPertemuanTime() {
        return pertemuanTime;
    }

    public void setPertemuanTime(@Nullable long pertemuanTime) {
        this.pertemuanTime = pertemuanTime;
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
    public String getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(@Nullable String detailLocation) {
        this.detailLocation = detailLocation;
    }


    @Nullable
    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable long createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@Nullable long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Nullable
    public String getPaket() {
        return paket;
    }

    public void setPaket(@Nullable String paket) {
        this.paket = paket;
    }

    @Nullable
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(@Nullable double discount) {
        this.discount = discount;
    }

    @Nullable
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(@Nullable String customerName) {
        this.customerName = customerName;
    }

    @Nullable
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(@Nullable String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Nullable
    public String getGuruEmail() {
        return guruEmail;
    }

    public void setGuruEmail(@Nullable String guruEmail) {
        this.guruEmail = guruEmail;
    }

    @Nullable
    public String getGuruPhone() {
        return guruPhone;
    }

    public void setGuruPhone(@Nullable String guruPhone) {
        this.guruPhone = guruPhone;
    }

    @Nullable
    public String getGuruName() {
        return guruName;
    }

    public void setGuruName(@Nullable String guruName) {
        this.guruName = guruName;
    }

    @Nullable
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(@Nullable String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
