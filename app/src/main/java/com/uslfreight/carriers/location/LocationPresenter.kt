package com.uslfreight.carriers.location

import android.content.Context
import android.preference.PreferenceManager
import com.uslfreight.carriers.service.LocationReportingService
import com.uslfreight.carriers.util.Constants
import java.util.regex.Pattern

interface MainLocationView {

    fun initializeView(phoneNumber: String?)
    fun setTrackButtonState(trackingState: TrackingState)
    fun getTrackingButtonState(): TrackingState
    fun getPhoneNumber(): String
}

class MainLocationPresenter(val mainLocationView: MainLocationView, val context: Context) {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun initializeState() {
        mainLocationView.setTrackButtonState(TrackingState.NotTracking(getSavedPhoneNumber()))
        mainLocationView.initializeView(getSavedPhoneNumber())
    }

    fun stateButtonClicked() {
        val trackingState = mainLocationView.getTrackingButtonState()
        when (trackingState) {
            is TrackingState.Tracking -> {
                mainLocationView.setTrackButtonState(TrackingState.NotTracking(getSavedPhoneNumber()))
                LocationReportingService.stopActionReportLocation(context)
            }
            is TrackingState.NotTracking -> {

                val phoneNumber = formatPhoneNumber(mainLocationView.getPhoneNumber())
                if (isValidPhoneFormat(phoneNumber)) {
                    val savedPhoneNumber = getSavedPhoneNumber()
                    if( !savedPhoneNumber.isNullOrBlank()) {
                        if (phoneNumber != savedPhoneNumber ) {
                            savePhoneNumber(phoneNumber)
                        }
                    }
                    else {
                        savePhoneNumber(phoneNumber)
                    }
                    mainLocationView.setTrackButtonState(TrackingState.Tracking(Constants.BUTTON_STATE_TITLE_TRACKING))
                    LocationReportingService.startActionReportLocation(context, phoneNumber)
                }

            }
            is TrackingState.Error -> {
                mainLocationView.setTrackButtonState(TrackingState.Error)
                LocationReportingService.stopActionReportLocation(context)
                // show error dialog
            }
        }
    }

    private fun getSavedPhoneNumber(): String? {
        val num = sharedPreferences.getString(Constants.PHONE_NUMBER, "")
        if( num.isNullOrBlank() ) {
            return null
        }
        return num
    }

    private fun savePhoneNumber(phoneNumber: String) {
        sharedPreferences.edit().putString(Constants.PHONE_NUMBER, phoneNumber).apply()
    }

    private fun isValidPhoneFormat(phoneNumber: String): Boolean = (phoneNumber.length == 10)

    private fun formatPhoneNumber(phoneNumber: String): String {
        var formatted = StringBuilder()
        val pattern = Pattern.compile("\\d+")
        val matcher = pattern.matcher(phoneNumber)
        while(matcher.find()) {
            formatted.append(matcher.group())
        }
        return formatted.toString()
    }
}