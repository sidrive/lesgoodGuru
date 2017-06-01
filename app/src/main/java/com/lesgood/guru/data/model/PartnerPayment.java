package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 2/27/17.
 */

public class PartnerPayment {
    @NonNull
    String uid;
    @Nullable
    String bank;
    @Nullable
    int bankCode;
    @Nullable
    String account;
    @Nullable
    String name;
    @Nullable
    String update_at;

    public PartnerPayment(){

    }

    public PartnerPayment(String uid){
        this.uid = uid;
    }

    public PartnerPayment(String uid, String bank, int bankCode, String account, String name, String update_at){
        this.uid = uid;
        this.bank = bank;
        this.bankCode = bankCode;
        this.account = account;
        this.name = name;
        this.update_at = update_at;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
