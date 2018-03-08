package com.uslfreight.carriers.util

object Constants {

    val LOCATION_BASE_URL = "https://carriers.uslfreight.com"

    val API_USERNAME = "p.gosling@icloud.com"
    val API_PASSWORD = "hJ4R=LsYvqSV4sUb"
    val PHONE_NUMBER = "phoneNumber"

    val DEFAULT_CONNECTION_TIMEOUT_SEC = 60
    val DEFAULT_READ_TIMEOUT_SEC = 60
    val MEDIA_TYPE_JSON_TEXT = "application/json; charset=utf-8"

    val DEFAULT_BUTTON_STATE_TITLE = "Start Tracking"
    val BUTTON_STATE_TITLE_TRACKING = "Stop Tracking"

//    val DEFAULT_REPORTING_INTERVAL = 600000L // 10 * 60 * 1000 10 minutes
    val DEFAULT_REPORTING_INTERVAL = 1000L

    val BROADCAST_EVENT = "com.uslfreight.carriers.BROADCAST_EVENT"

    // System Messages
    val LOCATION_REPORTING_SERVICE_ACTION = "LocationReportingService"
    val LOCATION_REPORTING_BROADCAST_ACTION = "LocationReportingServiceBroadcast"

    val COMPATIBILITY_DIALOG_TITLE = "Device Compatibility"
    val COMPATIBILITY_DIALOG_MESSAGE = "This device is not supported."

    val VALIDATION_DIALOG_TITLE = "Phone Number Validation"
    val VALIDATION_DIALOG_MESSAGE = "Please enter a valid 10 digit phone number, including area code and number."

    val SETTINGS_DIALOG_TITLE = "Enable Location"
    val SETTINGS_DIALOG_MESSAGE = "Your Locations Settings are set to 'Off'.\nPlease Enable Location to use this application."
    val SETTINGS_DIALOG_POS_BUTTON = "Location Settings"
    val SETTINGS_DIALOG_NEG_BUTTON = "Cancel"

    val LOCATION_REPORTING_STOPPED = "Location reporting has been stopped."

    // Error Messages
    val INTERRUPTED_THREAD_ERROR = "The application was unable to allocate sufficient resources to run. Please restart the application."
    val NETWORK_REQUEST_CALL_FAILURE = "Unable to complete location update request.  Please ensure that you have network connectivity and restart the application."
    val LOCATION_REQUEST_ERROR = "The GPS location could not be determined at this time."
}