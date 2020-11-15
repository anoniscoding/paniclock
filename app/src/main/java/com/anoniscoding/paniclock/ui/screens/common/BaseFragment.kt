package com.anoniscoding.paniclock.ui.screens.common

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseFragment() : Fragment() {

    protected fun navigate(command: NavigationCommand): Any {
        return when (command) {
            is NavigationCommand.To -> {
                try {
                    this.findNavController().navigate(
                        command.directions,
                        FragmentNavigatorExtras()
                    )
                } catch (e: Exception) {
                    // User tried tapping 2 links at once!
                }
            }
            is NavigationCommand.Back -> findNavController().navigateUp()
        }
    }

    protected fun navigate(action: Int, bundle: Bundle?) {
        findNavController().navigate(action, bundle)
    }

    protected fun disableBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            println("disables back press")
        }
    }

    protected fun hideSoftKeyboard(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    @Throws(IOException::class)
    protected fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "IMG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}

/**
 * A simple sealed class to handle more properly
 * navigation from a [ViewModel]
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
}