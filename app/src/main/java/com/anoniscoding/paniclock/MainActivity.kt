package com.anoniscoding.paniclock

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.anoniscoding.paniclock.ui.screens.extensions.makeStatusBarTransparent
import com.anoniscoding.paniclock.ui.screens.service.LockScreenService
import dagger.hilt.android.AndroidEntryPoint


private const val REQUEST_PERMISSIONS = 200

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var permissionAccepted = false
    private val permissions: Array<String> = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeStatusBarTransparent()
        startService(Intent(this, LockScreenService::class.java))
    }

    fun requestAllPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 1)
        }
    }

    override fun onAttachedToWindow() {
        this.window.addFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        super.onAttachedToWindow()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionAccepted = if (requestCode == REQUEST_PERMISSIONS) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }
}