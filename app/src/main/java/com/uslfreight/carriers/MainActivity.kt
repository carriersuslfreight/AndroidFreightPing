package com.uslfreight.carriers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.uslfreight.carriers.location.LocationInteractor
import com.uslfreight.carriers.location.MainLocationPresenter
import com.uslfreight.carriers.location.MainLocationView
import com.uslfreight.carriers.location.TrackingState
import com.uslfreight.carriers.util.Constants
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), MainLocationView {

    private lateinit var state: TrackingState
    private lateinit var presenter: MainLocationPresenter
    private val PERMISSIONS_REQUEST_LOCATION_CODE = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainLocationPresenter(this, LocationInteractor(this), applicationContext)


        trackStateButton.setOnClickListener {
            checkPermissions()
        }

        presenter.initializeState()
    }

    override fun initializeView(phoneNumber: String?) {
        phoneNumber?.let {
            phoneEditText.setText(phoneNumber)
        }
    }

    override fun setTrackButtonState(trackingState: TrackingState) {
        this.state = trackingState

        when(trackingState) {
            is TrackingState.Tracking -> {
                trackStateButton.isEnabled = true
                phoneEditText.setText("")
                trackStateButton.text = trackingState.title
            }
            is TrackingState.NotTracking -> {
                trackStateButton.isEnabled = true
                phoneEditText.setText(trackingState.phoneNumber)
                trackStateButton.text = Constants.DEFAULT_BUTTON_STATE_TITLE
            }
            is TrackingState.Error -> {
                trackStateButton.isEnabled = false
            }
        }
    }

    private fun checkPermissions() {
        val coursePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val finePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (coursePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED) {
            presenter.stateButtonClicked()
        }
        else {
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
//            }
//            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSIONS_REQUEST_LOCATION_CODE)
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
//            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode) {
            PERMISSIONS_REQUEST_LOCATION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissions()
                }
                else {
                    // permission denied, show alert dialog
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun getTrackingButtonState(): TrackingState = state

    override fun getPhoneNumber(): String = phoneEditText.text.toString()
}
