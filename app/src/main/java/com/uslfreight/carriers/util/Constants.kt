package com.uslfreight.carriers.util

object Constants {

    val LOCATION_BASE_URL = "https://carriers.uslfreight.com"

    val API_USERNAME = "p.gosling@icloud.com"
    val API_PASSWORD = "hJ4R=LsYvqSV4sUb"
    val PHONE_NUMBER = "phoneNumber"

    val DEFAULT_CONNECTION_TIMEOUT_SEC = 60
    val DEFAULT_READ_TIMEOUT_SEC = 60
    val MEDIA_TYPE_FORM = "application/x-www-form-urlencoded"
    val DEFAULT_BUTTON_STATE_TITLE = "Start Tracking"
    val BUTTON_STATE_TITLE_TRACKING = "Stop Tracking"
    val DEFAULT_REPORTING_INTERVAL = 10L

    // Service Events
    val BROADCAST_EVENT = "com.uslfreight.carriers.BROADCAST_EVENT"
    val LOCATION_REPORTING_SERVICE_ACTION = "LocationReportingService"
    val LOCATION_REPORTING_BROADCAST_ACTION = "LocationReportingServiceBroadcast"
    val REQUEST_APP_SETTINGS_CODE = 9999
    val LOCATION_REPORTING_INTERVAL = "locationReportingInterval"

    // Error Messages
    val VALIDATION_DIALOG_TITLE = "Phone Number Validation"
    val VALIDATION_DIALOG_MESSAGE = "Please enter a valid 10 digit phone number, including area code and number."
    val VALIDATION_POS_BUTTON = "OK"

    val SETTINGS_DIALOG_TITLE = "Enable Location"
    val SETTINGS_DIALOG_MESSAGE = "Locations Settings are set to 'Off'.\nThis application required Location permissions in order to run."
    val SETTINGS_DIALOG_POS_BUTTON = "Location Settings"
    val SETTINGS_DIALOG_NEG_BUTTON = "Cancel"

    val INTERRUPTED_THREAD_ERROR = "The application was unable to allocate sufficient resources to run. Please restart the application."
    val NETWORK_REQUEST_CALL_FAILURE = "Unable to complete location update request.  Please ensure that you have network connectivity and restart the application."
    val LOCATION_REQUEST_ERROR = "The GPS location could not be determined at this time."

    // Toast messages
    val LOCATION_REPORTING_STOPPED = "Location reporting has been stopped."
    val LOCATION_PERMISSION_GRANTED = "Location permission granted"
}