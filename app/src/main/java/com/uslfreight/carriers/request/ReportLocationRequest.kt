package com.uslfreight.carriers.request

import com.uslfreight.carriers.network.ApiEndpoints
import com.uslfreight.carriers.util.Constants
import okhttp3.ResponseBody
import retrofit2.Call

class ReportLocationRequest(
        val phoneNumber: String,
        val latitude: String,
        val longitude: String): NetworkRequest {

    private val TAG = ReportLocationRequest::class.java.simpleName
    private val headersMap = HashMap<String, String>()
    private val userAgent = "userAgent"

    override fun getRequestTag(): String = TAG

    override fun getBaseUrl(): String = Constants.LOCATION_BASE_URL

    override fun getRequestBody(): String {
        return "Phone=${phoneNumber}&Username=${Constants.API_USERNAME}&Password=${Constants.API_PASSWORD}&Latitude=${latitude}&Longitude=${longitude}&UserAgent=${userAgent}"
    }

    override fun getHeadersMap(): Map<String, String> =
            headersMap.apply {
                put("Content-Type", "application/x-www-form-urlencoded")
            }

    override fun getRequestEndpoint(apiEndpoints: ApiEndpoints): Call<ResponseBody> {
        return apiEndpoints.reportLocation()
    }
}