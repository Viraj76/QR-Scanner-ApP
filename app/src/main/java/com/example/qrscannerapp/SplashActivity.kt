package com.example.qrscannerapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.WindowInsets
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.nio.channels.InterruptedByTimeoutException

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hidingActionAndStatusBar()

        Handler(Looper.getMainLooper()).postDelayed({
            checkForPermission()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },1000)


    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            goToMainActivity()
        } else {
            requestUseForPermission()
        }
    }

    private fun requestUseForPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToMainActivity()
            } else if (isUserPermanentlyDenied()) {
                showGoToAppSettingDialog()
            } else {
                requestUseForPermission()
            }
        }
    }

    private fun showGoToAppSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grant Permission!")
            .setMessage("We need camera permission to scan QR code . Go to App Setting and manage permission")
            .setPositiveButton("Grant") { _, _ ->
                goToAppSetting()
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "We Need permission to this application", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
            .show()
    }

    private fun goToAppSetting() {
        val intent =
            Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
        intent.apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun isUserPermanentlyDenied(): Boolean {
        return !shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun hidingActionAndStatusBar() {
        supportActionBar?.hide()  //action  bar
        if (Build. VERSION.SDK_INT >= Build. VERSION_CODES.R) {
            val decorView = this.window.decorView
            decorView.windowInsetsController?.hide(WindowInsets. Type.statusBars())
        } //add one plugin in app module , id 'kotlin-android-extensions' ans write this code
    }
}