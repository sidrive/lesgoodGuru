package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 5/10/17.
 */

public class OrderItem {
    @NonNull
    String key;
    @NonNull
    String oid;
    @NonNull
    String product_name;
    @NonNull
    String price;
    @Nullable
    int qty;
    @Nullable
    long time;
    String customer_name;

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    @NonNull
    public String getOid() {
        return oid;
    }

    public void setOid(@NonNull String oid) {
        this.oid = oid;
    }

    @NonNull
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(@NonNull String product_name) {
        this.product_name = product_name;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    public void setPrice(@NonNull String price) {
        this.price = price;
    }

    @Nullable
    public int getQty() {
        return qty;
    }

    public void setQty(@Nullable int qty) {
        this.qty = qty;
    }

    @Nullable
    public long getTime() {
        return time;
    }

    public void setTime(@Nullable long time) {
        this.time = time;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
