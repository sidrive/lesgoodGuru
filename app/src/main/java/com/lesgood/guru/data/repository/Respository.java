package com.lesgood.guru.data.repository;

import com.lesgood.guru.data.model.maps.ResponseGeomap;
import io.reactivex.Observable;

/**
 * Created by sim-x on 11/1/17.
 */

public interface Respository {
  Observable<ResponseGeomap> getAddress(String key,String latling);
}
