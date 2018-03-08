package com.uslfreight.carriers.service

import android.app.IntentService
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.uslfreight.carriers.exception.LocationRequestException
import com.uslfreight.carriers.network.NetworkResponseCallback
import com.uslfreight.carriers.request.ReportLocationRequest
import com.uslfreight.carriers.util.Constants
import com.uslfreight.carriers.util.LocationManagementUtil

class LocationReportingService() : IntentService(Constants.LOCATION_REPORTING_SERVICE_ACTION), NetworkResponseCallback {

    private val TAG = LocationReportingService::class.java.simpleName
    private var runFlag = true
    private lateinit var phoneNumber: String
    private lateinit var broadcastIntent: Intent

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_REPORT_LOCATION == action) {
                phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER)
                handleActionReportLocation()
            }
        }
    }

    private fun handleActionReportLocation() {
        val locationManagement = LocationManagementUtil(applicationContext)
        val networkService = NetworkService()
        try {
            while (runFlag) {
                updateLocation(locationManagement, networkService)
                Thread.sleep(Constants.DEFAULT_REPORTING_INTERVAL)
            }
        }
        catch (ie: InterruptedException) {
            Thread.currentThread().interrupt()
            sendBroadcastEvent(Constants.INTERRUPTED_THREAD_ERROR)
        }
        catch( le: LocationRequestException ) {
            Thread.currentThread().interrupt()
            sendBroadcastEvent(le.message ?: Constants.LOCATION_REQUEST_ERROR)
        }
    }

    private fun updateLocation(locationManagement: LocationManagementUtil, networkService: NetworkService) {
        val locationPair = locationManagement.getLocation()
        val latitude = locationPair.first
        val longitude = locationPair.second
        Log.d(TAG, "Attempting to update location with latitude: $latitude, and longitude: $longitude")
        val request = ReportLocationRequest(phoneNumber, latitude, longitude)
        networkService.sendRequest(request, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        runFlag = false
        sendBroadcastEvent(Constants.LOCATION_REPORTING_STOPPED)
    }

    override fun onSuccess(message: String) {
       Log.d(TAG, "Received report update call response: $message")
    }

    override fun onFailure(message: String) {
        Log.d(TAG, "Received report update call failure response: $message")
        sendBroadcastEvent(Constants.NETWORK_REQUEST_CALL_FAILURE)
    }

    private fun sendBroadcastEvent(message: String) {
        broadcastIntent = Intent(Constants.BROADCAST_EVENT)
        broadcastIntent.putExtra(Constants.LOCATION_REPORTING_BROADCAST_ACTION, message)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    companion object {
        private val ACTION_REPORT_LOCATION = "com.uslfreight.carriers.location.action.REPORT"

        fun startActionReportLocation(context: Context, phoneNumber: String) {
            Log.d(TAG, "Initializing location reporting with phone number: $phoneNumber")
            val intent = Intent(context, LocationReportingService::class.java)
            intent.action = ACTION_REPORT_LOCATION
            intent.putExtra(Constants.PHONE_NUMBER, phoneNumber)
            context.startService(intent)
        }

        fun stopActionReportLocation(context: Context) {
            Log.d(TAG, "Stopping location reporting")
            val intent = Intent(context, LocationReportingService::class.java)
            context.stopService(intent)
        }
    }
}
