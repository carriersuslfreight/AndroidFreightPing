package com.uslfreight.carriers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.uslfreight.carriers.location.LocationInteractor
import com.uslfreight.carriers.location.MainLocationPresenter
import com.uslfreight.carriers.location.MainLocationView
import com.uslfreight.carriers.location.TrackingState
import com.uslfreight.carriers.service.NetworkService
import com.uslfreight.carriers.util.Constants
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainLocationView {

    private lateinit var state: TrackingState
    private lateinit var presenter: MainLocationPresenter
    private val PERMISSIONS_REQUEST_LOCATION_CODE = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainLocationPresenter(this, LocationInteractor(NetworkService(), this), applicationContext)

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

        when (trackingState) {
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
        val builder = AlertDialog.Builder(this)
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
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
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
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(Constants.VALIDATION_POS_BUTTON, { dialog, id ->
                    dialog.dismiss()
                }).create().show()

    }

    override fun getTrackingButtonState(): TrackingState = state

    override fun getPhoneNumber(): String = phoneEditText.text.toString()
}
