package com.cartravels_new.screens.fragments

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cartravels_new.R
import com.cartravels_new.models.MaterialTheme
import com.cartravels_new.screens.fragments.MaterialThemeDialogFragment.Companion.newInstance
import com.cartravels_new.utils.DialogUtils.showDialogFragment
import com.cartravels_new.utils.LogUtils
import com.cartravels_new.utils.LogUtils.getSavedInstanceStateNullMessage
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

/**
 * Fragment showing user interface elements that can be themed using code at runtime.
 *
 * @author Steven Byle
 */
class MaterialThemeInCodeFragment : Fragment(), View.OnClickListener {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
        // Get passed in parameters
        val args = arguments
        // If no parameters were passed in, default them
        if (args == null) {
        } else {
        }
        // If this is the first creation, default state variables
        if (savedInstanceState == null) {
        } else {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
        // Inflate the fragment layout into the container
        val fragmentView = inflater.inflate(R.layout.fragment_material_theme_in_code, container, false)
        // Reference views
        val currentThemeDialogButton = fragmentView.findViewById<View>(R.id.fragment_material_theme_button_dialog_current_theme) as Button
        val greenThemeDialogButton = fragmentView.findViewById<View>(R.id.fragment_material_theme_button_dialog_green_theme) as Button
        val blueThemeDialogButton = fragmentView.findViewById<View>(R.id.fragment_material_theme_button_dialog_blue_theme) as Button
        val currentThemeSnackbarButton = fragmentView.findViewById<View>(R.id.fragment_material_theme_button_snackbar_current_theme) as Button
        val greenThemeSnackbarButton = fragmentView.findViewById<View>(R.id.fragment_material_theme_button_snackbar_green_theme) as Button
        val blueThemeSnackbarButton = fragmentView.findViewById<View>(R.id.fragment_material_theme_button_snackbar_blue_theme) as Button
        // Set and bind data to views
        currentThemeDialogButton.setOnClickListener(this)
        greenThemeDialogButton.setOnClickListener(this)
        blueThemeDialogButton.setOnClickListener(this)
        currentThemeSnackbarButton.setOnClickListener(this)
        greenThemeSnackbarButton.setOnClickListener(this)
        blueThemeSnackbarButton.setOnClickListener(this)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
    }

    override fun onStart() {
        super.onStart()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onResume() {
        super.onResume()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onPause() {
        super.onPause()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onStop() {
        super.onStop()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onClick(v: View) {
        Timber.d(LogUtils.METHOD_ONLY)
        when (v.id) {
            R.id.fragment_material_theme_button_dialog_current_theme -> {
                val dialogFragment = newInstance(v.context,
                        R.string.material_theme_current_theme,
                        R.string.material_theme_current_theme,
                        null)
                showDialogFragment(childFragmentManager, dialogFragment)
            }
            R.id.fragment_material_theme_button_dialog_green_theme -> {
                val dialogFragment = newInstance(v.context,
                        R.string.material_theme_green_theme,
                        R.string.material_theme_green_theme,
                        MaterialTheme.THEME_ALERT_DIALOG_GREEN)
                showDialogFragment(childFragmentManager, dialogFragment)
            }
            R.id.fragment_material_theme_button_dialog_blue_theme -> {
                val dialogFragment = newInstance(v.context,
                        R.string.material_theme_blue_theme,
                        R.string.material_theme_blue_theme,
                        MaterialTheme.THEME_ALERT_DIALOG_BLUE)
                showDialogFragment(childFragmentManager, dialogFragment)
            }
            R.id.fragment_material_theme_button_snackbar_current_theme -> Snackbar.make(v, R.string.material_theme_current_theme, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_action, this)
                    .show()
            R.id.fragment_material_theme_button_snackbar_green_theme -> {
                val greenTheme = ContextThemeWrapper(v.context, R.style.AppTheme_Green).theme
                val a = greenTheme.obtainStyledAttributes(intArrayOf(R.attr.colorAccent))
                val accentColor = a.getColor(0, 0)
                a.recycle()
                Snackbar.make(v, R.string.material_theme_green_theme, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbar_action, this)
                        .setActionTextColor(accentColor)
                        .show()
            }
            R.id.fragment_material_theme_button_snackbar_blue_theme -> {
                val blueTheme = ContextThemeWrapper(v.context, R.style.AppTheme_Blue).theme
                val a = blueTheme.obtainStyledAttributes(intArrayOf(R.attr.colorAccent))
                val accentColor = a.getColor(0, 0)
                a.recycle()
                Snackbar.make(v, R.string.material_theme_blue_theme, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbar_action, this)
                        .setActionTextColor(accentColor)
                        .show()
            }
            else -> Timber.w("Unknown view clicked")
        }
    }

    companion object {
        fun newInstance(): MaterialThemeInCodeFragment {
            Timber.v(LogUtils.METHOD_ONLY)
            // Create a new fragment instance
            val fragment = MaterialThemeInCodeFragment()
            // Get arguments passed in, if any
            var args = fragment.arguments
            if (args == null) {
                args = Bundle()
            }
            // Add parameters to the argument bundle
            fragment.arguments = args
            return fragment
        }
    }
}