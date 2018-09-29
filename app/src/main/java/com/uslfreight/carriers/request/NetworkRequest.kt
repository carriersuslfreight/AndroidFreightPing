package com.uslfreight.carriers.request

import com.uslfreight.carriers.network.ApiEndpoints
import okhttp3.ResponseBody
import retrofit2.Call

interface NetworkRequest {
    fun addHeader(key: String, value: String)
    fun getRequestTag(): String
    fun getBaseUrl(): String
    fun getRequestBody():String?
    fun getHeadersMap(): Map<String, String>
    fun getRequestEndpoint(apiEndpoints: ApiEndpoints): Call<ResponseBody>
}