package com.cartravels.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Util class to manage showing dialogs.
 *
 * @author Steven Byle
 */
object DialogUtils {
    @JvmOverloads
    fun showDialogFragment(fragmentManager: FragmentManager, dialogFragment: DialogFragment,
                           fragmentTag: String?, onlyIfNotDuplicate: Boolean = true): String? { // If only showing non duplicates dialogs, make sure the fragment isn't already in the manager
        val doesFragmentExist = fragmentManager.findFragmentByTag(fragmentTag) != null
        if (!(onlyIfNotDuplicate && doesFragmentExist)) {
            dialogFragment.show(fragmentManager, fragmentTag)
        }
        return fragmentTag
    }

    fun showDialogFragment(fragmentManager: FragmentManager, dialogFragment: DialogFragment,
                           onlyIfNotDuplicate: Boolean): String? {
        return showDialogFragment(fragmentManager, dialogFragment, generateFragmentTag(dialogFragment), onlyIfNotDuplicate)
    }

    @JvmStatic
    fun showDialogFragment(fragmentManager: FragmentManager, dialogFragment: DialogFragment) {
        showDialogFragment(fragmentManager, dialogFragment, generateFragmentTag(dialogFragment))
    }

    private fun generateFragmentTag(fragment: Fragment): String {
        return fragment.javaClass.name
    }
}