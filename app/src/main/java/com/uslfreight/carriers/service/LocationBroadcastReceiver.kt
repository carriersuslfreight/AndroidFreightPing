package com.uslfreight.carriers.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.uslfreight.carriers.util.Constants


class LocationBroadcastReceiver : BroadcastReceiver() {

    private val TAG = LocationBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        val asyncTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, Int, String>() {
            override fun doInBackground(vararg params: String): String {
                Log.d(TAG, "Received broadcast action: ${intent.action}, intent: $intent")
                pendingResult?.finish()
                return intent.getStringExtra(Constants.LOCATION_REPORTING_BROADCAST_ACTION)
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            }
        }
        asyncTask.execute()
    }
}
