package com.uslfreight.carriers.util

object Constants {

    const val LOCATION_BASE_URL = "https://carriers.uslfreight.com"

    const val API_USERNAME = "p.gosling@icloud.com"
    const val API_PASSWORD = "hJ4R=LsYvqSV4sUb"
    const val PHONE_NUMBER = "phoneNumber"

    const val DEFAULT_CONNECTION_TIMEOUT_SEC = 60
    const val DEFAULT_READ_TIMEOUT_SEC = 60
    const val MEDIA_TYPE_FORM = "application/x-www-form-urlencoded"
    const val DEFAULT_BUTTON_STATE_TITLE = "Start Tracking"
    const val BUTTON_STATE_TITLE_TRACKING = "Stop Tracking"
    const val DEFAULT_REPORTING_INTERVAL = 10L

    // Service Events
    const val BROADCAST_EVENT = "com.uslfreight.carriers.BROADCAST_EVENT"
    const val LOCATION_REPORTING_SERVICE_ACTION = "LocationReportingService"
    const val LOCATION_REPORTING_BROADCAST_ACTION = "LocationReportingServiceBroadcast"
    const val REQUEST_APP_SETTINGS_CODE = 9999
    const val LOCATION_REPORTING_INTERVAL = "locationReportingInterval"

    // Error Messages
    const val VALIDATION_DIALOG_TITLE = "Phone Number Validation"
    const val VALIDATION_DIALOG_MESSAGE = "Please enter a valid 10 digit phone number, including area code and number."
    const val VALIDATION_POS_BUTTON = "OK"

    const val NETWORK_ERROR_TITLE = "Network Connection Error"
    const val NETWORK_ERROR_MESSAGE = "The application was unable to complete location update.  Please check the device's network connection."

    const val SETTINGS_DIALOG_TITLE = "Enable Location"
    const val SETTINGS_DIALOG_MESSAGE = "Locations Settings are set to 'Off'.\nThis application required Location permissions in order to run."
    const val SETTINGS_DIALOG_POS_BUTTON = "Location Settings"
    const val SETTINGS_DIALOG_NEG_BUTTON = "Cancel"

    const val INTERRUPTED_THREAD_ERROR = "The application was unable to allocate sufficient resources to run. Please restart the application."
    const val NETWORK_REQUEST_CALL_FAILURE = "Unable to complete location update request.  Please ensure that you have network connectivity and restart the application."
    const val LOCATION_REQUEST_ERROR = "The GPS location could not be determined at this time."

    // Toast messages
    const val LOCATION_REPORTING_STOPPED = "Location reporting has been stopped."
    const val LOCATION_PERMISSION_GRANTED = "Location permission granted"
}