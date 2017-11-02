package com.lesgood.guru.data.remote;


import com.lesgood.guru.data.model.maps.ResponseGeomap;
import com.lesgood.guru.data.network.NetworkModule;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Agus on 3/6/17.
 */

public interface APIService  {
    @Headers("Accept:application/json")
    @GET("geocode/json")
    Observable<ResponseGeomap> getAddress(@Query("key") String key, @Query("latlng")String latLng);

}
