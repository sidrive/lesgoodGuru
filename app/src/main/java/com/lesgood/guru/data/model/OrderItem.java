package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Agus on 5/10/17.
 */

public class OrderItem {
    @NonNull
    String oid;
    @NonNull
    String product_name;
    @NonNull
    String price;
    @Nullable
    int qty;

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
}
