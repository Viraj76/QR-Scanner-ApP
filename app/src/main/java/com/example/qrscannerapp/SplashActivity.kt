package com.example.qrscannerapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

class SplashActivity : AppCompatActivity() {

    companion object{
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkForPermission()
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA )== PackageManager.PERMISSION_GRANTED){
            goToMainActivity()
        }
        else{
            requestUseForPermission()
        }
    }

    private fun requestUseForPermission() {
       ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA) , CAMERA_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                goToMainActivity()
            } else if(isUserPermissio)
        }
    }

    private fun goToMainActivity() {

    }
}