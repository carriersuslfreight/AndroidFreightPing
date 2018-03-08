package com.uslfreight.carriers.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.uslfreight.carriers.exception.LocationRequestException

class LocationManagementUtil (val context: Context){

    fun getLocation(): Pair<String, String> {
        var latitude = ""
        var longitude = ""

        // Need to check permissions again, user may have changed settings
        val coursePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        val finePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (coursePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var location: Location?
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }

                if (location != null) {
                    latitude = java.lang.Double.toString(location.latitude)
                    longitude = java.lang.Double.toString(location.longitude)
                }
            }
        } else {
            // Permission not granted
            throw LocationRequestException(Constants.LOCATION_REQUEST_ERROR)
        }

        return Pair(latitude, longitude)
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) { }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}