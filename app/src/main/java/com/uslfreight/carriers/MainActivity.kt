package com.uslfreight.carriers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.uslfreight.carriers.location.LocationInteractor
import com.uslfreight.carriers.location.MainLocationPresenter
import com.uslfreight.carriers.location.MainLocationView
import com.uslfreight.carriers.location.TrackingState
import com.uslfreight.carriers.util.Constants
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainLocationView {

    private lateinit var state: TrackingState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val presenter = MainLocationPresenter(this, LocationInteractor(this), applicationContext)

        trackStateButton.setOnClickListener {
            presenter.stateButtonClicked()
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

    override fun getTrackingButtonState(): TrackingState = state

    override fun getPhoneNumber(): String = phoneEditText.text.toString()
}
