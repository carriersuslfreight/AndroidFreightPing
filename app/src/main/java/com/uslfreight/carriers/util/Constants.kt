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

    val LOCATION_REPORTING_SERVICE_TAG = "LocationReportingService"

    val COMPATIBILITY_DIALOG_TITLE = "Device Compatibility"
    val COMPATIBILITY_DIALOG_MESSAGE = "This device is not supported."

    val VALIDATION_DIALOG_TITLE = "Phone Number Validation"
    val VALIDATION_DIALOG_MESSAGE = "Please enter a valid 10 digit phone number, including area code and number."
    val VALIDATION_DIALOG_EXISTING_PROCESS_TITLE = "Application Status"
    val VALIDATION_DIALOG_EXISTING_PROCESS_MESSAGE = "Application enrollment has already been completed."
    val VALIDATION_DIALOG_EXISTING_PROCESS_MESSAGE_DISABLED = "The application has been stopped."
    val VALIDATION_DIALOG_EXISTING_PROCESS_MESSAGE_STARTED = "The application has been enrolled. Please click OK to close the application"

    val CONFIRMATION_DIALOG_TITLE = "Confirm Phone Number"
    val CONFIRMATION_DIALOG_POS_BUTTON = "Confirm"
    val CONFIRMATION_DIALOG_NEG_BUTTON = "Retry"
    val CONFIRMATION_DIALOG_MESSAGE = "You have entered %s. Is this correct?"

    val SETTINGS_DIALOG_TITLE = "Enable Location"
    val SETTINGS_DIALOG_MESSAGE = "Your Locations Settings are set to 'Off'.\nPlease Enable Location to use this application."
    val SETTINGS_DIALOG_POS_BUTTON = "Location Settings"
    val SETTINGS_DIALOG_NEG_BUTTON = "Cancel"

    val NOTIFICATION_TITLE = "Location Update Service"
    val NOTIFICATION_MESSAGE = "Periodic location update"

    // Error Messages
    val ENROLLMENT_REQUEST_CALL_FAILURE = "Unable to complete enrollment request successfully.  Please try again."
    val ENROLLMENT_REQUEST_INPUT_VALIDATION_ERROR = "Required enrollment parameters cannot be determined"
    val NETWORK_REQUEST_CALL_FAILURE = "Unable to complete location update request successfully"
    val NETWORK_REQUEST_INPUT_VALIDATION_ERROR = "Required location parameters cannot be determined"
}