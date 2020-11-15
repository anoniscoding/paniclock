package com.anoniscoding.paniclock.ui.screens.pin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.anoniscoding.paniclock.domain.interactors.GetUserDetailsUseCase
import com.anoniscoding.paniclock.domain.interactors.UpdateUserDetailsUseCase

class SecurityPinViewModel @ViewModelInject constructor(
    private val _getUserDetailsUseCase: GetUserDetailsUseCase,
    private val _updateUserDetailsUseCase: UpdateUserDetailsUseCase
): ViewModel() {

    fun savePin(type: PINTYPE, pin: String) {
        val user = getUserDetail()
        if (type == PINTYPE.SAFE_PIN) user.safePin = pin
        else user.panicPin = pin

        _updateUserDetailsUseCase.buildUseCaseSynchronous(UpdateUserDetailsUseCase.Params.forUpdateUserDetails(user))
    }

    fun getUserDetail() = _getUserDetailsUseCase.buildUseCaseSynchronous()

    enum class PINTYPE {
        SAFE_PIN, PANIC_PIN
    }
}