package com.uslfreight.carriers.location

import android.content.Context
import android.preference.PreferenceManager
import com.uslfreight.carriers.request.GetTimerRequest
import com.uslfreight.carriers.util.Constants
import java.util.regex.Pattern

interface MainLocationView {

    fun initializeView(phoneNumber: String?)
    fun setTrackButtonState(trackingState: TrackingState)
    fun getTrackingButtonState(): TrackingState
    fun getPhoneNumber(): String
}

class MainLocationPresenter(val mainLocationView: MainLocationView, val interactor: LocationInteractor, val context: Context): OnLocationRequestCallback{

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private var reportInteral: Long = 0L

    fun initializeState() {
        mainLocationView.setTrackButtonState(TrackingState.NotTracking(getSavedPhoneNumber()))
        mainLocationView.initializeView(getSavedPhoneNumber())
        interactor.setCallback(this)

        // Make timer request
//        interactor.requestIterationTime(GetTimerRequest())
    }

    override fun onReportIntervalSuccess(reportInteral: Long) {
        this.reportInteral = reportInteral
    }

    fun stateButtonClicked() {
        interactor.requestIterationTime(GetTimerRequest())
        val trackingState = mainLocationView.getTrackingButtonState()
        when (trackingState) {
            is TrackingState.Tracking -> {
                mainLocationView.setTrackButtonState(TrackingState.NotTracking(getSavedPhoneNumber()))
                interactor.stopReportingService()
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
                    interactor.startReportingService(phoneNumber, reportInteral)
                }

            }
            is TrackingState.Error -> {
                mainLocationView.setTrackButtonState(TrackingState.Error)
                interactor.stopReportingService()
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