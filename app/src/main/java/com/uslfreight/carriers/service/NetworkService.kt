package com.uslfreight.carriers.service

import android.util.Log
import com.uslfreight.carriers.network.ApiEndpoints
import com.uslfreight.carriers.network.NetworkResponseCallback
import com.uslfreight.carriers.request.NetworkRequest
import com.uslfreight.carriers.util.Constants
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetworkService {

    private val TAG = NetworkService::class.java.simpleName

    private val connectTimeout = Constants.DEFAULT_CONNECTION_TIMEOUT_SEC
    private val readTimeout = Constants.DEFAULT_READ_TIMEOUT_SEC
    private val mediaType = MediaType.parse(Constants.MEDIA_TYPE_JSON_TEXT)

    fun sendRequest(networkRequest: NetworkRequest, callback: NetworkResponseCallback) {
        val body = RequestBody.create(mediaType, networkRequest.getRequestBody())
        Log.d(networkRequest.getRequestTag(), "Request body: " + networkRequest.getRequestBody())

        val client = getOkhttpClient(networkRequest.getHeadersMap(), body)
        val retrofit = getRetrofit(client, networkRequest.getBaseUrl())

        val api = retrofit.create(ApiEndpoints::class.java)
        val call = networkRequest.getRequestEndpoint(api)

        Log.d(TAG, "Attempting call with url: " + call.request().toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.body() != null) {
                    try {
                        var bodyContent = response.body()!!.string()
                        callback.onSuccess(bodyContent)
                    } catch (e: IOException) {
                        Log.e(TAG, "Unable to retrieve response", e)
                        onFailure(call, e)
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                callback.onFailure(Constants.NETWORK_REQUEST_CALL_FAILURE)
                Log.e(TAG, Constants.NETWORK_REQUEST_CALL_FAILURE, throwable)
            }
        })
    }


    private fun getOkhttpClient(headers: Map<String, String>, body: RequestBody): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val ongoing = chain.request().newBuilder()
                    ongoing.headers(Headers.of(headers))
                    ongoing.post(body)
                    chain.proceed(ongoing.build())
                }
                .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
                .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
        return builder.build()
    }

    private fun getRetrofit(okHttpClient: okhttp3.Call.Factory, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .callFactory(okHttpClient)
                .build()
    }
}