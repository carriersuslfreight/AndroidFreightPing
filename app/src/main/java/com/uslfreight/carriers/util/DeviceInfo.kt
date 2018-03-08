package com.uslfreight.carriers.util

import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log


class DeviceInfo(app: Application) {

    private val TAG = DeviceInfo::class.java.simpleName

    /** The Unique device ID, the IMEI for GSM and the MEID for CDMA phones.  */
    val uniqueDeviceId: String?

    /** The OS build name and version.  */
    val osBuild: String

    /** The OS version.  */
    val osVersion: String

    /** The phone/device model.  */
    val model: String

    /** The phone/device API Level.(Added for hybrid changes)  */
    val deviceAPI: Int

    /** A 64-bit number (as a hex string) that is randomly generated on the device's first boot
     * and should remain constant for the lifetime of the device
     * (The value may change if a factory reset is performed on the device.)
     */
    val androidDeviceId: String?

    val platformOsVersion: String

    init {
        var version = ""
        var deviceID: String? = ""
        var androidOsVer = ""

        try {
            deviceID = Settings.Secure.getString(app.applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
            if (deviceID == null || deviceID == "9774d56d682e549c") {
                val tman = app.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                if (tman != null) {
                    deviceID = tman.deviceId
                } else {
                    deviceID = ""
                }
            }

            androidOsVer = System.getProperty("os.version")
            version = System.getProperty("os.name") + " v " + androidOsVer
        } catch (e: Exception) {
            Log.e(TAG, "Unable to initialize", e)
        }

        androidDeviceId = deviceID
        uniqueDeviceId = deviceID
        osBuild = version
        osVersion = androidOsVer
        model = Build.MODEL
        deviceAPI = Build.VERSION.SDK_INT
        platformOsVersion = Build.VERSION.RELEASE
    }
}