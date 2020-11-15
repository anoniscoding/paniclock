package com.anoniscoding.paniclock.ui.screens.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.anoniscoding.paniclock.BuildConfig
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.common.BaseFragment
import com.anoniscoding.paniclock.ui.screens.common.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.splash_fragment.*


@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val _viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        handleAppUpgrade()
        registerObservers()
        animateLogo()
        _viewModel.startTimeout()
    }

    private fun registerObservers() {
        _viewModel.isMoveToNextScreen.observe(
            viewLifecycleOwner,
            Observer { moveAwayFromSplashScreen() })
    }

    private fun moveAwayFromSplashScreen() {
        if (_viewModel.isUserOnboarded()) {
            navigate(NavigationCommand.To(actionSplashFragmentToHomeFragment()))
        } else {
            navigate(NavigationCommand.To(actionSplashFragmentToOnboardingFragment()))
        }
    }

    private fun animateLogo() {
        val bounceAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.bounce_animation)
        app_logo.startAnimation(bounceAnimation)
    }

    private fun handleAppUpgrade() {
        val cache = requireContext().getSharedPreferences(
            APP_CACHE,
            Context.MODE_PRIVATE
        )
        val versionCode = cache.all[APP_VERSION_CODE] ?: 0
        if (versionCode != BuildConfig.VERSION_CODE) {
            cache.edit().clear().commit()
            cache.edit().putInt(APP_VERSION_CODE, BuildConfig.VERSION_CODE).apply()
        }
    }

    private fun actionSplashFragmentToOnboardingFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_splashFragment_to_onboardingFragment)
    }

    private fun actionSplashFragmentToHomeFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_splashFragment_to_HomeFragment)
    }

    companion object {
        const val APP_VERSION_CODE = "app_version_code"
        const val APP_CACHE = BuildConfig.APPLICATION_ID
    }

}