package com.uslfreight.carriers.location

import android.content.Context
import android.util.Log
import com.uslfreight.carriers.request.GetTimerRequest
import com.uslfreight.carriers.service.LocationReportingService
import com.uslfreight.carriers.service.NetworkService
import com.uslfreight.carriers.util.Constants
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Log::class, Constants::class, LocationReportingService::class)
class LocationInteractorTest {

    private lateinit var networkService: NetworkService
    private lateinit var interactor: LocationInteractor
    private lateinit var context: Context
    private lateinit var getTimerRequest: GetTimerRequest

    @Before
    fun setUp() {
        PowerMockito.mockStatic(Log::class.java)
        PowerMockito.mockStatic(Constants::class.java)
        PowerMockito.mockStatic(LocationReportingService::class.java)

        networkService = Mockito.mock(NetworkService::class.java)
        assertNotNull(networkService)

        context = Mockito.mock(Context::class.java)
        assertNotNull(context)

        getTimerRequest = GetTimerRequest()
        assertNotNull(getTimerRequest)

        interactor = LocationInteractorImpl(networkService, context)
    }

    @Test
    fun testRequestIterationTime() {
        interactor.requestIterationTime(getTimerRequest)
        verify(networkService).sendRequest(any(), any())
    }

    // Handle how kotlin will return Null in verify for anyObject(), and any() mocks
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
}