package com.uslfreight.carriers

import android.app.Application
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.uslfreight.carriers.service.LocationBroadcastReceiver
import com.uslfreight.carriers.util.Constants

class USLApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Register Broadcast Receiver with system
        val intentFilter = IntentFilter(Constants.BROADCAST_EVENT)
        val broadcastReceiver = LocationBroadcastReceiver()
        LocalBroadcastManager.getInstance(this).registerReceiver( broadcastReceiver, intentFilter)
    }
}