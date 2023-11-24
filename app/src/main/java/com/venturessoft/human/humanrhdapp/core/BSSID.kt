package com.venturessoft.human.humanrhdapp.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import com.venturessoft.human.humanrhdapp.ui.login.ui.LoginActivity
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class BSSID (private val activity: Activity) {

    fun getBSSID() {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                val mWifiManager: WifiManager = (activity.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                if (mWifiManager.connectionInfo.ssid.contains("AndroidWifi")){
                    User.ambiente = Constants.CONTROLCALIDAD
                }
            }
            goToLogin()
        }
    }

    private fun goToLogin(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intentActivity = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intentActivity)
            activity.finish()
        },1500)
    }
}