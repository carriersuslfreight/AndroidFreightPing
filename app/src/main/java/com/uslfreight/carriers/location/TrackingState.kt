package com.uslfreight.carriers.location

sealed class TrackingState {
    data class Tracking(val title: String): TrackingState()
    data class NotTracking(val phoneNumber: String?): TrackingState()
    object Error: TrackingState()
}