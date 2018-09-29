package com.uslfreight.carriers.location

import android.content.SharedPreferences
import android.util.Log
import com.uslfreight.carriers.request.GetTimerRequest
import com.uslfreight.carriers.service.LocationReportingService
import com.uslfreight.carriers.service.NetworkService
import com.uslfreight.carriers.util.Constants
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Log::class, Constants::class, LocationReportingService::class)
class LocationPresenterTest {

    private lateinit var view: MainLocationView
    private lateinit var interactor: LocationInteractor
    private lateinit var networkService: NetworkService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenter: MainLocationPresenter
    private lateinit var timerRequest: GetTimerRequest
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var callback: OnLocationRequestCallback

    @Before
    fun setUp() {
        PowerMockito.mockStatic(Log::class.java)
        PowerMockito.mockStatic(Constants::class.java)
        PowerMockito.mockStatic(LocationReportingService::class.java)

        view = Mockito.mock(MainLocationView::class.java)
        assertNotNull(view)

        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        assertNotNull(sharedPreferences)

        editor = Mockito.mock(SharedPreferences.Editor::class.java)
        assertNotNull(editor)

        networkService = Mockito.mock(NetworkService::class.java)
        assertNotNull(networkService)

        interactor = Mockito.mock(LocationInteractor::class.java)
        assertNotNull(interactor)

        timerRequest = GetTimerRequest()
        assertNotNull(timerRequest)

        callback = MainLocationPresenterImpl(view, interactor, sharedPreferences)
        assertNotNull(callback)

        presenter = MainLocationPresenterImpl(view, interactor, sharedPreferences)
        assertNotNull(presenter)
    }

    @Test
    fun testInitializeState() {
        presenter.initializeState(true)
        verify(interactor).setCallback(any())
        verify(interactor).requestIterationTime(any())
    }

    @Test
    fun testOnReportIntervalSuccess() {
        callback.onReportIntervalSuccess(0L)
    }

    @Test
    fun testStateButtonClickedTracking(){
        `when`(view.getTrackingButtonState()).thenReturn(TrackingState.Tracking("Start Tracking"))
        presenter.stateButtonClicked()
        verify(view).setTrackButtonState(TrackingState.NotTracking(any()))
        verify(interactor).stopReportingService()
    }

    @Test
    @Ignore
    fun testStateButtonClickedNotTracking(){
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(view.getTrackingButtonState()).thenReturn(TrackingState.NotTracking("Stop Tracking"))
        `when`(view.getPhoneNumber()).thenReturn("(123)456-7890")
        presenter.stateButtonClicked()
        verify(view).setTrackButtonState(TrackingState.Tracking(any()))
        verify(interactor).startReportingService(any(), any())
    }

    @Test
    fun testStateButtonClickedError(){
        `when`(view.getTrackingButtonState()).thenReturn(TrackingState.Error)
        presenter.stateButtonClicked()
        verify(view).setTrackButtonState(TrackingState.NotTracking(null))
        verify(view).initializeView(null)
        verify(interactor).stopReportingService()
    }

    // Handle how kotlin will return Null in verify for anyObject(), and any() mocks
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

}