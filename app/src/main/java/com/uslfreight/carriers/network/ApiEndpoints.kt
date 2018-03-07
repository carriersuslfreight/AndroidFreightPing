package com.uslfreight.carriers.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

interface ApiEndpoints {
    @POST("/ws/index.asmx/ReportLocation")
    fun reportLocation():Call<ResponseBody>
    //    Call<ReportLocationResponse> reportLocation(@Query("Phone") String phoneNumber, @Query("Latitude") String latitude, @Query("Longitude") String longitude);
    //    Call<ResponseBody> reportLocation(@Query("Phone") String phoneNumber, @Query("Latitude") String latitude, @Query("Longitude") String longitude);
}