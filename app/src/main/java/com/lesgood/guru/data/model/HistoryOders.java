package com.lesgood.guru.data.model;

/**
 * Created by sim-x on 12/20/17.
 */

public class HistoryOders {
  private String oid;
  private String oldGid;
  private String newGid;
  private String iid;
  private String status;
  private long createAt;

  public HistoryOders() {
  }

  public HistoryOders(String oid, String oldGid, String newGid, String iid, String status,
      long createAt) {
    this.oid = oid;
    this.oldGid = oldGid;
    this.newGid = newGid;
    this.iid = iid;
    this.status = status;
    this.createAt = createAt;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getOldGid() {
    return oldGid;
  }

  public void setOldGid(String oldGid) {
    this.oldGid = oldGid;
  }

  public String getNewGid() {
    return newGid;
  }

  public void setNewGid(String newGid) {
    this.newGid = newGid;
  }

  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(long createAt) {
    this.createAt = createAt;
  }
}
