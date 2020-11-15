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
import com.anoniscoding.paniclock.MainActivity
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.common.NavigationCommand
import com.anoniscoding.paniclock.ui.screens.extensions.show
import com.anoniscoding.paniclock.ui.screens.extensions.showSnackbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.security_pin_fragment.*

@AndroidEntryPoint
class PanicPinFragment : BaseSecurityFragment() {
    private val _viewModel: SecurityPinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerListeners()
    }

    private fun init() {
        help_icon.show()
        title.text = "CREATE A PANIC PIN"
        description.text = "Use this PIN to unlock your \nphone when in danger"
    }

    override fun registerListeners() {
        super.registerListeners()
        save_button.setOnClickListener { onSaveClick() }
    }

    private fun onSaveClick() {
        if (pin.length < 4) return
        val user = _viewModel.getUserDetail()
        if (user.safePin == pin) {
            showSnackbar("Panic Pin Cannot Be The Same As Safe Pin", Snackbar.LENGTH_SHORT)
            return
        }

        _viewModel.savePin(SecurityPinViewModel.PINTYPE.PANIC_PIN, pin)
        showSnackbar("Panic Pin Created", Snackbar.LENGTH_SHORT)
        navigate(NavigationCommand.To(actionPanicPinFragmentToHomeFragment()))
    }

    private fun actionPanicPinFragmentToHomeFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_panicFragment_to_HomeFragment)
    }
}