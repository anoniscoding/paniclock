package com.anoniscoding.paniclock.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View

private enum class FanSpeed(val label: String) {
    OFF("fan_off"),
    LOW("fan_low"),
    MEDIUM("fan_medium"),
    HIGH("fan_high");
}

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

}