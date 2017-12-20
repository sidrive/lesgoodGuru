package com.lesgood.guru.data.model;

/**
 * Created by sim-x on 12/20/17.
 */

public class Invoices {
  private int amount;
  private int tarif;
  private int total;
  private String code;
  private String iid;
  private int totalSiswa;
  private double fee;
  private int totalPertemuan;
  private int discount;
  private String oid;
  private String status;

  public Invoices() {
  }

  public Invoices(int amount, int tarif, int total, String code, String iid, int totalSiswa,
      double fee, int totalPertemuan, int discount, String oid, String status) {
    this.amount = amount;
    this.tarif = tarif;
    this.total = total;
    this.code = code;
    this.iid = iid;
    this.totalSiswa = totalSiswa;
    this.fee = fee;
    this.totalPertemuan = totalPertemuan;
    this.discount = discount;
    this.oid = oid;
    this.status = status;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getTarif() {
    return tarif;
  }

  public void setTarif(int tarif) {
    this.tarif = tarif;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public int getTotalSiswa() {
    return totalSiswa;
  }

  public void setTotalSiswa(int totalSiswa) {
    this.totalSiswa = totalSiswa;
  }

  public double getFee() {
    return fee;
  }

  public void setFee(double fee) {
    this.fee = fee;
  }

  public int getTotalPertemuan() {
    return totalPertemuan;
  }

  public void setTotalPertemuan(int totalPertemuan) {
    this.totalPertemuan = totalPertemuan;
  }

  public int getDiscount() {
    return discount;
  }

  public void setDiscount(int discount) {
    this.discount = discount;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
