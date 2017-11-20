package com.lesgood.guru.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sim-x on 11/20/17.
 */

public class Days {
  @NonNull
  String id;
  @Nullable
  String name;
  public Days(){}
  public Days(@NonNull String id, String name) {
    this.id = id;
    this.name = name;
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  @Nullable
  public String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }
}
