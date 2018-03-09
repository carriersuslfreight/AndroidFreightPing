package com.uslfreight.carriers.location

import android.content.Context
import android.util.Log
import android.util.Xml
import com.uslfreight.carriers.network.NetworkResponseCallback
import com.uslfreight.carriers.request.GetTimerRequest
import com.uslfreight.carriers.service.LocationReportingService
import com.uslfreight.carriers.service.NetworkService
import com.uslfreight.carriers.util.Constants
import com.uslfreight.carriers.util.XMLUtil

interface OnLocationRequestCallback {
    fun onReportIntervalSuccess(reportInteral: Long)
}

class LocationInteractor(val networkService: NetworkService, val context: Context): NetworkResponseCallback {

    private val TAG = LocationInteractor::class.java.simpleName
    private lateinit var callback: OnLocationRequestCallback

    fun setCallback(callback: OnLocationRequestCallback) {
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

    fun startReportingService(phoneNumber: String, reportInteral: Long) {
        LocationReportingService.startActionReportLocation(context, phoneNumber, reportInteral)
    }

    fun stopReportingService() {
        LocationReportingService.stopActionReportLocation(context)
    }

    override fun onSuccess(message: String) {
        try {
            Log.d(TAG, "Received reporting interval success response: $message")
            val xmlUtil = XMLUtil(Xml.newPullParser())
            val value = xmlUtil.getTimerValue( message, Constants.DEFAULT_REPORTING_INTERVAL)
            callback.onReportIntervalSuccess(value * 60L * 1000L)
        }
        catch(e: Exception) {
            // NOOP, use default value
            callback.onReportIntervalSuccess(Constants.DEFAULT_REPORTING_INTERVAL * 60L * 1000L)
        }
    }

    override fun onFailure(message: String) {
        Log.d(TAG, "Received reporting interval failure response: $message")

        // Show alert or use default interval?
    }
}