package com.anoniscoding.paniclock.ui.screens.extensions

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

fun Fragment.showSnackbar(
    snackbarText: String,
    timeLength: Int,
    @ColorInt snackBarColor: Int = Color.BLACK
): Snackbar? {
    activity?.let {
        val snack = Snackbar.make(it.findViewById(android.R.id.content), snackbarText, timeLength)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        view.layoutParams = params
        snack.view.setBackgroundColor(snackBarColor)

        snack.show()
        return snack
    }
    return null
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}