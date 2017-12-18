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
  private long createAt;

  @Nullable
  String status;

  @Nullable
  private int saldo;

  @Nullable
  private String paymetId;

  public Withdraw(String uid, long createAt, String status, int saldo, String paymetId) {
    this.uid = uid;
    this.createAt = createAt;
    this.status = status;
    this.saldo = saldo;
    this.paymetId = paymetId;
  }

  @Nullable
  public String getUid() {
    return uid;
  }

  public void setUid(@Nullable String uid) {
    this.uid = uid;
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
  public String getPaymetId() {
    return paymetId;
  }

  public void setPaymetId(@Nullable String paymetId) {
    this.paymetId = paymetId;
  }
}
