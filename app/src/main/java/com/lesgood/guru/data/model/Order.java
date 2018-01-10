package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Agus on 2/24/17.
 */

public class Order {
    @NonNull
    private String gid;
    @NonNull
    private String uid;

    @NonNull
    private String oid;

    @NonNull
    private String oldOid;

    @NonNull
    private String iid;
    @Nullable
    private String code;
    @Nullable
    private String title;
    @Nullable
    private String status;
    @Nullable
    private int amount;
    @Nullable
    private int totalSiswa;
    @Nullable
    private int totalPertemuan;
    @Nullable
    private double fee;
    @Nullable
    private double total;
    @Nullable
    private List<Jadwal> pertemuan;
    @Nullable
    private long ordertime;
    @Nullable
    private long pertemuanTime;
    @Nullable
    private String detailLocation;
    @Nullable
    private String phone;
    @Nullable
    private long createdAt;
    @Nullable
    private long updatedAt;
    @Nullable
    private String paket;
    @Nullable
    private double discount;
    @Nullable
    private String reference;
    @Nullable
    private String paymentUrl;
    @Nullable
    private String statusPayment;

    @Nullable
    private String statusGantiGuru;

    @Nullable
    private int tarif;

    @Nullable
    private String orderType;
    @Nullable
    private Double lat;
    @Nullable
    private Double lng;

    public Order(){}

    public Order(String oid, String code, String title){
        this.oid = oid;
        this.code = code;
        this.title = title;
    }

    @Nullable
    public Double getLat() {
        return lat;
    }

    public void setLat(@Nullable Double lat) {
        this.lat = lat;
    }

    @Nullable
    public Double getLng() {
        return lng;
    }

    public void setLng(@Nullable Double lng) {
        this.lng = lng;
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

    @NonNull
    public String getOldOid() {
        return oldOid;
    }

    public void setOldOid(@NonNull String oldOid) {
        this.oldOid = oldOid;
    }

    @NonNull
    public String getIid() {
        return iid;
    }

    public void setIid(@NonNull String iid) {
        this.iid = iid;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    public void setCode(@Nullable String code) {
        this.code = code;
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
    public List<Jadwal> getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(@Nullable List<Jadwal> pertemuan) {
        this.pertemuan = pertemuan;
    }

    @Nullable
    public long getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(@Nullable long ordertime) {
        this.ordertime = ordertime;
    }

    @Nullable
    public long getPertemuanTime() {
        return pertemuanTime;
    }

    public void setPertemuanTime(@Nullable long pertemuanTime) {
        this.pertemuanTime = pertemuanTime;
    }

    @Nullable
    public String getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(@Nullable String detailLocation) {
        this.detailLocation = detailLocation;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
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
    public String getStatusGantiGuru() {
        return statusGantiGuru;
    }

    public void setStatusGantiGuru(@Nullable String statusGantiGuru) {
        this.statusGantiGuru = statusGantiGuru;
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
}
