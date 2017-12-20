package com.lesgood.guru.data.model;

import android.support.annotation.Nullable;

/**
 * Created by sim-x on 12/18/17.
 */

public class Withdraw  {
  public Withdraw(){}

  @Nullable
  private String uid;

  @Nullable
  private String wid;

  @Nullable
  private long createAt;

  @Nullable
  String status;

  @Nullable
  private int saldo;

  @Nullable
  private String paymentId;

  public Withdraw(String uid, String wid, long createAt, String status, int saldo,
      String paymentId) {
    this.uid = uid;
    this.wid = wid;
    this.createAt = createAt;
    this.status = status;
    this.saldo = saldo;
    this.paymentId = paymentId;
  }

  @Nullable
  public String getUid() {
    return uid;
  }

  public void setUid(@Nullable String uid) {
    this.uid = uid;
  }

  @Nullable
  public String getWid() {
    return wid;
  }

  public void setWid(@Nullable String wid) {
    this.wid = wid;
  }

  @Nullable
  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(@Nullable long createAt) {
    this.createAt = createAt;
  }

  @Nullable
  public String getStatus() {
    return status;
  }

  public void setStatus(@Nullable String status) {
    this.status = status;
  }

  @Nullable
  public int getSaldo() {
    return saldo;
  }

  public void setSaldo(@Nullable int saldo) {
    this.saldo = saldo;
  }

  @Nullable
  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(@Nullable String paymentId) {
    this.paymentId = paymentId;
  }
}
