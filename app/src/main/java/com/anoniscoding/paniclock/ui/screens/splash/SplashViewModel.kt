package com.anoniscoding.paniclock.ui.screens.splash

import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anoniscoding.paniclock.domain.interactors.GetUserDetailsUseCase
import com.anoniscoding.paniclock.ui.screens.common.Resource

class SplashViewModel @ViewModelInject constructor(
    private val _useCase: GetUserDetailsUseCase
): ViewModel() {

    private val _isMoveToNextScreen = MutableLiveData<Resource<Boolean>>()
    val isMoveToNextScreen: LiveData<Resource<Boolean>> = _isMoveToNextScreen

    fun startTimeout() {
        Handler().postDelayed({
            _isMoveToNextScreen.value = Resource.success(true)
        }, TIMEOUT)
    }

    fun isUserOnboarded(): Boolean {
        return _useCase.buildUseCaseSynchronous().isOnboarded
    }

    companion object {
        const val TIMEOUT: Long = 3000
    }
}