package com.uslfreight.carriers.location

import android.app.IntentService
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.uslfreight.carriers.util.Constants

class LocationReportingService() : IntentService(Constants.LOCATION_REPORTING_SERVICE_TAG) {

    private val TAG = LocationReportingService::class.java.simpleName
    private var runFlag = true

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_REPORT_LOCATION == action) {
                val phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER)
                handleActionReportLocation(phoneNumber)
            }
        }
    }

    private fun handleActionReportLocation(phoneNumber: String) {
        try {
            while(runFlag) {
                Thread.sleep(Constants.DEFAULT_REPORTING_INTERVAL)
                Log.d(TAG, "Updating location with phone number $phoneNumber")
            }
        } catch (e: InterruptedException) {
            // Restore interrupt status.
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On destroy called")
        runFlag = false
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
            val intent = Intent(context, LocationReportingService::class.java)
            context.stopService(intent)
        }
    }
}
