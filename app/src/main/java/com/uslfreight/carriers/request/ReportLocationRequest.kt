package com.uslfreight.carriers.request

import com.uslfreight.carriers.network.ApiEndpoints
import com.uslfreight.carriers.util.Constants
import okhttp3.ResponseBody
import retrofit2.Call

class ReportLocationRequest(
        private val phoneNumber: String,
        private val latitude: String,
        private val longitude: String): NetworkRequest {

    private val TAG = ReportLocationRequest::class.java.simpleName
    private val headersMap = HashMap<String, String>()
    private val userAgent = "android"

    override fun addHeader(key: String, value: String) {
        headersMap[key] = value
    }

    override fun getRequestTag(): String = TAG

    override fun getBaseUrl(): String = Constants.LOCATION_BASE_URL

    override fun getRequestBody(): String {
        return "Phone=${phoneNumber}&Username=${Constants.API_USERNAME}&Password=${Constants.API_PASSWORD}&UserAgent=${userAgent}&Latitude=${latitude}&Longitude=${longitude}"
    }

    override fun getHeadersMap(): Map<String, String> =
            headersMap.apply {
                put("Content-Type", "application/x-www-form-urlencoded")
            }

    override fun getRequestEndpoint(apiEndpoints: ApiEndpoints): Call<ResponseBody> {
        return apiEndpoints.postReportLocation()
//        return apiEndpoints.getReportLocation(phoneNumber, Constants.API_USERNAME, Constants.API_PASSWORD, userAgent, latitude, longitude)
    }
}