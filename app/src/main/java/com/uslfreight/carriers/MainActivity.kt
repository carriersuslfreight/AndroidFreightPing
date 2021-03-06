package com.uslfreight.carriers

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.uslfreight.carriers.location.LocationInteractorImpl
import com.uslfreight.carriers.location.MainLocationPresenter
import com.uslfreight.carriers.location.MainLocationPresenterImpl
import com.uslfreight.carriers.location.MainLocationView
import com.uslfreight.carriers.location.TrackingState
import com.uslfreight.carriers.service.LocationBroadcastReceiver
import com.uslfreight.carriers.service.NetworkServiceImpl
import com.uslfreight.carriers.service.OnBroadCastReceivedListener
import com.uslfreight.carriers.util.Constants
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainLocationView {

    private lateinit var state: TrackingState
    private lateinit var presenter: MainLocationPresenter
    private val PERMISSIONS_REQUEST_LOCATION_CODE = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Crashlytics
        if (! Fabric.isInitialized() ) {
            Fabric.with(this, Crashlytics())
        }

        // Register Broadcast Receiver with system
        val intentFilter = IntentFilter(Constants.BROADCAST_EVENT)
        val broadcastReceiver = LocationBroadcastReceiver(object: OnBroadCastReceivedListener {
            override fun onSuccess(message: String?) {
                message?.let {
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
            }
            override fun onError(message: String?) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                presenter.initializeState(false)
            }
        })
        LocalBroadcastManager.getInstance(this).registerReceiver( broadcastReceiver, intentFilter)

        presenter = MainLocationPresenterImpl(
                this,
                LocationInteractorImpl(NetworkServiceImpl(), this),
                PreferenceManager.getDefaultSharedPreferences(this))

        trackStateButton.setOnClickListener {
            checkPermissions()
        }

        presenter.initializeState(true)
    }

    override fun initializeView(phoneNumber: String?) {
        phoneNumber?.let {
            phoneEditText.setText(phoneNumber)
        }
        trackStateButton.text = Constants.DEFAULT_BUTTON_STATE_TITLE
    }

    override fun setTrackButtonState(trackingState: TrackingState) {
        this.state = trackingState

        when (trackingState) {
            is TrackingState.Tracking -> {
                trackStateButton.isEnabled = true
                phoneEditText.setText("")
                trackStateButton.text = trackingState.title
            }
            is TrackingState.NotTracking -> {
                trackStateButton.isEnabled = true
                initializeView(trackingState.phoneNumber)
            }
            is TrackingState.Error -> {
                trackStateButton.isEnabled = true
                initializeView(null)
            }
        }
    }

    private fun checkPermissions() {
        val coursePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val finePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (coursePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED) {
            presenter.stateButtonClicked()
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermissions()
                } else {
                    showPermissionDialog()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun showPermissionDialog() {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this, R.style.alertDialogStyle)
        builder.setTitle(Constants.SETTINGS_DIALOG_TITLE)
        builder.setMessage(Constants.SETTINGS_DIALOG_MESSAGE)
        builder.setPositiveButton(Constants.SETTINGS_DIALOG_POS_BUTTON, { dialog, id ->
            goToSettings()
        })
        builder.setNegativeButton(Constants.SETTINGS_DIALOG_NEG_BUTTON, { dialog, id ->
            dialog.dismiss()
        })
        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun goToSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", packageName, null)
        startActivityForResult(intent, Constants.REQUEST_APP_SETTINGS_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constants.REQUEST_APP_SETTINGS_CODE -> {
                Toast.makeText(this, Constants.LOCATION_PERMISSION_GRANTED, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun showErrorDialog(title: String, message: String) {
        AlertDialog.Builder(this, R.style.alertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(Constants.VALIDATION_POS_BUTTON, { dialog, id ->
                    dialog.dismiss()
                }).create().show()

    }

    override fun getTrackingButtonState(): TrackingState = state

    override fun getPhoneNumber(): String = phoneEditText.text.toString()
}
