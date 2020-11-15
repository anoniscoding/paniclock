package com.anoniscoding.paniclock.ui.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.anoniscoding.paniclock.MainActivity
import com.anoniscoding.paniclock.R
import com.anoniscoding.paniclock.ui.screens.common.BaseFragment
import com.anoniscoding.paniclock.ui.screens.common.NavigationCommand
import com.anoniscoding.paniclock.ui.screens.pin.LockPinFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.nav_item_view.view.*

class HomeFragment : BaseFragment(), HomeAdapter.OnNavClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).requestAllPermissions()
        initAdapter()
    }

    private fun initAdapter() {
        home_list.layoutManager = LinearLayoutManager(requireContext())
        home_list.adapter = HomeAdapter(this)
    }

    override fun onNavItemClick (position: Int) {
        when (position) {
            HomeAdapter.RECORDING_POSTION -> {
                navigate(NavigationCommand.To(actionHomeFragmentToRecordingsFragment()))
            }
            HomeAdapter.CHANGE_LOCK_POSITION -> {
                navigateToLockPinFragment()
            }
        }
    }

    private fun actionHomeFragmentToRecordingsFragment(): NavDirections {
        return ActionOnlyNavDirections(R.id.action_homeFragment_to_RecordingsFragment)
    }

    private fun navigateToLockPinFragment() {
        val bundle =
            Bundle().apply { putBoolean(LockPinFragment.IS_FOR_UNLOCKING_PHONE, false) }
        navigate(R.id.action_homeFragment_to_LockPinFragment, bundle)
    }

    override fun onDestroyView() {
        home_list.adapter = null
        super.onDestroyView()
    }
}