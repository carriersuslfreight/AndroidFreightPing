package com.uslfreight.carriers.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast



class LocationBroadcastReceiver : BroadcastReceiver() {

    private val TAG = LocationBroadcastReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()

        val asyncTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, Int, String>() {
            override fun doInBackground(vararg params: String): String {
                val sb = StringBuilder()
                sb.append("Action: " + intent.action + "\n")
                sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n")

                Log.d(TAG, "Received broadcast action: ${intent.action}, intent: $intent")
                Toast.makeText(context, "Received broadcast", Toast.LENGTH_LONG).show()

                pendingResult.finish()
                return sb.toString()
            }
        }
        asyncTask.execute()
    }
}
