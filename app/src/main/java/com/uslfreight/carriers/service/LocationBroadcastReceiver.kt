package com.uslfreight.carriers.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.uslfreight.carriers.util.Constants

interface OnBroadCastReceivedListener {
    fun onSuccess(message: String?)
    fun onError(message: String?)
}

class LocationBroadcastReceiver(val listener: OnBroadCastReceivedListener) : BroadcastReceiver() {

    private val TAG = LocationBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        val asyncTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, Int, String>() {
            override fun doInBackground(vararg params: String): String {
                pendingResult?.finish()
                return intent.getStringExtra(Constants.LOCATION_REPORTING_BROADCAST_ACTION)
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)

                when(result) {
                    Constants.NETWORK_REQUEST_CALL_FAILURE,
                    Constants.INTERRUPTED_THREAD_ERROR,
                    Constants.LOCATION_REQUEST_ERROR -> {
                        listener.onError(result)
                    }
                    else -> {
                        listener.onSuccess(result)
                    }
                }
            }
        }
        asyncTask.execute()
    }
}
