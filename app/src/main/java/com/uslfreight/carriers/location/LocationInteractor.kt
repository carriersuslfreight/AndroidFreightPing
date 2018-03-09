package com.uslfreight.carriers.location

import android.content.Context
import android.util.Log
import com.uslfreight.carriers.network.NetworkResponseCallback
import com.uslfreight.carriers.request.GetTimerRequest
import com.uslfreight.carriers.service.LocationReportingService
import com.uslfreight.carriers.service.NetworkService
import com.uslfreight.carriers.util.Constants

interface LocationCallback { }

class LocationInteractor(val networkService: NetworkService, val context: Context): NetworkResponseCallback {

    private val TAG = LocationInteractor::class.java.simpleName
    private lateinit var callback: LocationCallback
    private var reportingInteral = Constants.DEFAULT_REPORTING_INTERVAL

    fun setCallback(callback: LocationCallback) {
        this.callback = callback
    }

    fun requestIterationTime(request: GetTimerRequest) {
        try {
            networkService.sendRequest(request, this)
        }
        catch(e: Exception) {
            onFailure(e.message ?: "" )
        }
    }

    fun startReportingService(phoneNumber: String) {
        LocationReportingService.startActionReportLocation(context, phoneNumber, reportingInteral)
    }

    fun stopReportingService() {
        LocationReportingService.stopActionReportLocation(context)
    }

    override fun onSuccess(message: String) {
        Log.d(TAG, "Received reporting interval success response: $message")

    }

    override fun onFailure(message: String) {
        Log.d(TAG, "Received reporting interval failure response: $message")
    }
}