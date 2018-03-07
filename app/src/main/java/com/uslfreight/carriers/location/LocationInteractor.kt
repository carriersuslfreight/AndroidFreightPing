package com.uslfreight.carriers.location

import android.content.Context


class LocationInteractor(val context: Context) {

    private lateinit var callback: LocationCallback

    fun setCallback(callback: LocationCallback) {
        this.callback = callback
    }

    fun startReportingService(phoneNumber: String) {
        LocationReportingService.startActionReportLocation(context, phoneNumber)
    }

    fun stopReportingService() {
        LocationReportingService.stopActionReportLocation(context)
    }
}