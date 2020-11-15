package com.anoniscoding.paniclock.ui.screens.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.common.BaseFragment
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.security_pin_fragment.*

open class BaseSecurityFragment: BaseFragment() {
    protected var pin = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.security_pin_fragment, container, false)
    }

    protected open fun registerListeners() {
        button0.setOnClickListener { onKeyPadClick(0) }
        button1.setOnClickListener { onKeyPadClick(1) }
        button2.setOnClickListener { onKeyPadClick(2) }
        button3.setOnClickListener { onKeyPadClick(3) }
        button4.setOnClickListener { onKeyPadClick(4) }
        button5.setOnClickListener { onKeyPadClick(5) }
        button6.setOnClickListener { onKeyPadClick(6) }
        button7.setOnClickListener { onKeyPadClick(7) }
        button8.setOnClickListener { onKeyPadClick(8) }
        button9.setOnClickListener { onKeyPadClick(9) }
        buttonDelete.setOnClickListener { onDeleteClick() }
        help_icon.setOnClickListener { viewHelp() }
    }

    protected open fun onPinComplete(pin: String) {}

    private fun onKeyPadClick(num: Int) {
        if (pin.length == 4) { return }

        pin += num
        val pins = listOf(pin1, pin2, pin3, pin4)
        val totalPinsToBeMarked = 4 - pin.length
        val pinsToBeMarked = pins.dropLast(totalPinsToBeMarked)
        pinsToBeMarked.forEach {
            it.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.colorWhite)
            )
        }

        if (pin.length == 4) { onPinComplete(pin) }
    }

    private fun onDeleteClick() {
        when (pin.length) {
            4 -> unmarkPin(pin4)
            3 -> unmarkPin(pin3)
            2 -> unmarkPin(pin2)
            1 -> unmarkPin(pin1)
        }
        pin = pin.dropLast(1)
    }

    private fun unmarkPin(view: MaterialCardView) {
        view.setCardBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.color_purple_858893)
        )
    }

    protected open fun unMarkAllPin() {
        val pins = listOf(pin1, pin2, pin3, pin4)
        pins.forEach { unmarkPin(it) }
        pin = ""
    }

    private fun viewHelp() {
        val bottomSheet = HelpBottomSheetFragment()
        bottomSheet.show(childFragmentManager, HelpBottomSheetFragment.TAG)
    }
}