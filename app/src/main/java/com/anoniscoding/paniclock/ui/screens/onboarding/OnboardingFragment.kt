package com.anoniscoding.paniclock.ui.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.common.BaseFragment
import com.anoniscoding.paniclock.ui.screens.common.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.onboarding_fragment.*

@AndroidEntryPoint
class OnboardingFragment : BaseFragment() {
    private val _viewModel: OnboardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.onboarding_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }

    private fun registerListeners() {
        create_safe_pin_button.setOnClickListener { onCreateSafePinClick() }
    }

    private fun onCreateSafePinClick() {
        _viewModel.markUserAsOnboarded()
        navigate(NavigationCommand.To(actionOnboardingFragmentToSecurityPinFragment()))
    }

    private fun actionOnboardingFragmentToSecurityPinFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_onboardingFragment_to_SafePinFragment)
    }

}