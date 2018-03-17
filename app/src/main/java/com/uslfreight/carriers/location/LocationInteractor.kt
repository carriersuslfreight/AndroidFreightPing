package com.uslfreight.carriers.location

import android.content.Context
import android.util.Log
import android.util.Xml
import com.crashlytics.android.Crashlytics
import com.uslfreight.carriers.network.NetworkResponseCallback
import com.uslfreight.carriers.request.GetTimerRequest
import com.uslfreight.carriers.service.LocationReportingService
import com.uslfreight.carriers.service.NetworkService
import com.uslfreight.carriers.util.Constants
import com.uslfreight.carriers.util.XMLUtil

interface OnLocationRequestCallback {
    fun onReportIntervalSuccess(reportInteral: Long)
    fun onReportIntervalFailure(e: Throwable)
}

interface LocationInteractor {
    fun setCallback(callback: OnLocationRequestCallback)
    fun requestIterationTime(request: GetTimerRequest)
    fun startReportingService(phoneNumber: String, reportInteral: Long)
    fun stopReportingService()
}

class LocationInteractorImpl (private val networkService: NetworkService, private val context: Context): LocationInteractor, NetworkResponseCallback {

    private val TAG = LocationInteractor::class.java.simpleName
    private lateinit var callback: OnLocationRequestCallback

    override fun setCallback(callback: OnLocationRequestCallback) {
        this.callback = callback
    }

    override fun requestIterationTime(request: GetTimerRequest) {
        try {
            networkService.sendRequest(request, this)
        }
        catch(e: Exception) {
            Crashlytics.logException(e)
            onFailure(e.localizedMessage)
        }
    }

    override fun startReportingService(phoneNumber: String, reportInteral: Long) {
        LocationReportingService.startActionReportLocation(context, phoneNumber, reportInteral)
    }

    override fun stopReportingService() {
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
            Crashlytics.logException(e)
            callback.onReportIntervalSuccess(Constants.DEFAULT_REPORTING_INTERVAL * 60L * 1000L)
        }
    }

    override fun onFailure(message: String) {
        callback.onReportIntervalFailure(Exception("Received reporting interval failure $message"))
    }
}