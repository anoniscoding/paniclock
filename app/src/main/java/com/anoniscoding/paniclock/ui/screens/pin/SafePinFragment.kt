package com.anoniscoding.paniclock.ui.screens.pin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.common.NavigationCommand
import com.anoniscoding.paniclock.ui.screens.extensions.showSnackbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.security_pin_fragment.*

@AndroidEntryPoint
class SafePinFragment : BaseSecurityFragment() {
    private val _viewModel: SecurityPinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }

    override fun registerListeners() {
        super.registerListeners()
        save_button.setOnClickListener { onSaveClick() }
    }

    private fun onSaveClick() {
        if (pin.length < 4) return
        _viewModel.savePin(SecurityPinViewModel.PINTYPE.SAFE_PIN, pin)
        showSnackbar("Safe Pin Created", Snackbar.LENGTH_SHORT)
        navigate(NavigationCommand.To(actionSafeFragmentToPanicPinFragment()))
    }

    private fun actionSafeFragmentToPanicPinFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_safeFragment_to_PanicPinFragment)
    }
}