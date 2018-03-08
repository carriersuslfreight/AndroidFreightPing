package com.uslfreight.carriers.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiEndpoints {

    @POST("/ws/index.asmx/ReportLocation")
    fun postReportLocation():Call<ResponseBody>

    @GET("/ws/index.asmx/ReportLocation")
    fun getReportLocation(
            @Query("Phone") phoneNumber: String,
            @Query("Username") username: String,
            @Query("Password") password: String,
            @Query("UserAgent") userAgent: String,
            @Query("Latitude") latitude: String,
            @Query("Longitude") longitude: String
    ):Call<ResponseBody>
}