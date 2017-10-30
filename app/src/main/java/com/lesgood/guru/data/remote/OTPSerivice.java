package com.lesgood.guru.data.remote;


import com.lesgood.guru.data.model.OTP;
import com.lesgood.guru.data.model.OTPresponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Agus on 2/9/17.
 */
public interface OTPSerivice {

    @GET("verify/json")
    Call<OTP> send_otp(@Query("api_key") String api_key, @Query("api_secret") String api_secret, @Query("number") String nohp, @Query("brand") String brand);

    @GET("verify/check/json")
    Call<OTPresponse> check_otp(@Query("api_key") String api_key, @Query("api_secret") String api_secret, @Query("request_id") String request_id, @Query("code") String code);
}
