package com.uslfreight.carriers.request

import com.uslfreight.carriers.network.ApiEndpoints
import com.uslfreight.carriers.util.Constants
import okhttp3.ResponseBody
import retrofit2.Call

class GetTimerRequest: NetworkRequest {

    private val TAG = GetTimerRequest::class.java.simpleName
    private val headersMap = HashMap<String, String>()

    override fun addHeader(key: String, value: String) { }

    override fun getRequestTag(): String = TAG

    override fun getBaseUrl(): String = Constants.LOCATION_BASE_URL

    override fun getRequestBody(): String? {
//        return "Username=${Constants.API_USERNAME}&Password=${Constants.API_PASSWORD}"
        return ""
    }

    override fun getHeadersMap(): Map<String, String> =
            headersMap.apply {
//                put("Content-Type", "application/x-www-form-urlencoded")
//                put("Content-Type", "text/xml; charset=utf-8")
            }

    override fun getRequestEndpoint(apiEndpoints: ApiEndpoints): Call<ResponseBody> {
//        return apiEndpoints.postGetTimer()
        return apiEndpoints.getGetTimer(Constants.API_USERNAME, Constants.API_PASSWORD)
    }
}