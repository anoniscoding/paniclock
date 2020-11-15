package com.anoniscoding.paniclock.ui.screens.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.anoniscoding.paniclock.domain.interactors.GetUserDetailsUseCase
import com.anoniscoding.paniclock.domain.interactors.UpdateUserDetailsUseCase

class OnboardingViewModel @ViewModelInject constructor(
    private val _getUserDetailsUseCase: GetUserDetailsUseCase,
    private val _updateUserDetailsUseCase: UpdateUserDetailsUseCase
): ViewModel() {

    fun markUserAsOnboarded() {
        val user = _getUserDetailsUseCase.buildUseCaseSynchronous().apply { isOnboarded = true }
        _updateUserDetailsUseCase.buildUseCaseSynchronous(UpdateUserDetailsUseCase.Params.forUpdateUserDetails(user))
    }
}