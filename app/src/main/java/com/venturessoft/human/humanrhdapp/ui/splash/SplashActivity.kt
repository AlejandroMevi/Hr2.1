package com.venturessoft.human.humanrhdapp.ui.splash

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.venturessoft.human.humanrhdapp.core.BSSID
import com.venturessoft.human.humanrhdapp.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val bssid = BSSID(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validatePermission()
    }

    private fun validatePermission(){
        val permissionsArray = mutableListOf<String>()
        val grented = PackageManager.PERMISSION_GRANTED
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != grented){
            permissionsArray.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != grented){
            permissionsArray.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (permissionsArray.isEmpty()){
            bssid.getBSSID()
        }else{
            ActivityCompat.requestPermissions(this, permissionsArray.toTypedArray(), 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            bssid.getBSSID()
        }else{
            validatePermission()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isGranted = true
        grantResults.forEach {permission->
            if (permission !=  PackageManager.PERMISSION_GRANTED){
                isGranted = false
            }
        }
        if (isGranted){
            bssid.getBSSID()
        }else{
            validatePermission()
        }
    }
}