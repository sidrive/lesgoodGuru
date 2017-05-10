package com.lesgood.guru.data.remote;


import com.lesgood.guru.data.model.Provinces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Agus on 3/6/17.
 */

public interface APIService {

    @GET("locations/1/province_list")
    Call<Provinces> getProvince();

    @GET("locations/{id}/children")
    Call<Provinces> getChildren(@Path("id") String id);

}
