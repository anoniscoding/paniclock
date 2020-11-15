package com.anoniscoding.paniclock.ui.screens.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anoniscoding.paniclock.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.help_bottom_sheet_fragment.*

class HelpBottomSheetFragment: BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.help_bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        okay_button.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG: String = HelpBottomSheetFragment::class.java.simpleName
    }
}