package com.anoniscoding.paniclock.ui.screens.pin

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.models.User
import com.anoniscoding.paniclock.ui.screens.common.NavigationCommand
import com.anoniscoding.paniclock.ui.screens.extensions.hide
import com.anoniscoding.paniclock.ui.screens.extensions.showSnackbar
import com.anoniscoding.paniclock.ui.screens.service.BackgroundVideoRecorder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.security_pin_fragment.*

@AndroidEntryPoint
class LockPinFragment : BaseSecurityFragment() {

    private val _viewModel: SecurityPinViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.hide()
        save_button.hide()
        description.text = "Enter Passcode"
        registerListeners()
    }

    override fun onPinComplete(pin: String) {
        val user = _viewModel.getUserDetail()
        val isForUnlockingPhone = arguments?.getBoolean(IS_FOR_UNLOCKING_PHONE) ?: true

        if (isForUnlockingPhone) {
            handeUnlockPhoneProcess(pin, user)
        } else {
            handleChangeLockProcess(pin, user)
        }
    }

    private fun handleChangeLockProcess(
        pin: String,
        user: User
    ) {
        if (pin == user.safePin || pin == user.panicPin) {
            navigate(NavigationCommand.To(actionLockPinFragmentToSafePinFragment()))
        } else {
            handleError()
        }
    }

    private fun handeUnlockPhoneProcess(
        pin: String,
        user: User
    ) {
        when (pin) {
            user.safePin -> closeApp()
            user.panicPin -> handlePanicSituation()
            else -> handleError()
        }
    }

    private fun actionLockPinFragmentToSafePinFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_lockFragment_to_SafePinFragment)
    }

    private fun closeApp() {
        this.requireActivity().finishAffinity()
    }

    private fun handlePanicSituation() {
        requireActivity().startService(Intent(requireContext(), BackgroundVideoRecorder::class.java))
        this.requireActivity().finishAffinity()
    }

    private fun handleError() {
        unMarkAllPin()
        showSnackbar("Invalid Password, Try Again", Snackbar.LENGTH_LONG)
    }

    companion object {
        const val IS_FOR_UNLOCKING_PHONE = "IS_FOR_UNLOCKING_PHONE"
    }
}