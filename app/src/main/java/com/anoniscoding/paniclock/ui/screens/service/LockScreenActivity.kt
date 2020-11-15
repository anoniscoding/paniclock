package com.anoniscoding.paniclock.ui.screens.service

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.extensions.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LockScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)
        makeStatusBarTransparent()
    }
}