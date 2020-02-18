package com.cartravels.screens.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.cartravels.R
import com.cartravels.utils.LogUtils
import com.cartravels.utils.LogUtils.getSavedInstanceStateNullMessage
import timber.log.Timber

/**
 * Fragment showing user interface elements that can be themed using XML in layout files.
 *
 * @author Steven Byle
 */
class MaterialThemeInXmlFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v(LogUtils.METHOD_ONLY)
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
        val fragmentView = inflater.inflate(R.layout.fragment_material_theme_in_xml, container, false)
        // Reference views
        val currentThemeSpinner = fragmentView.findViewById<View>(R.id.fragment_material_theme_spinner_current_theme) as Spinner
        val greenThemeSpinner = fragmentView.findViewById<View>(R.id.fragment_material_theme_spinner_green_theme) as Spinner
        val blueThemeSpinner = fragmentView.findViewById<View>(R.id.fragment_material_theme_spinner_blue_theme) as Spinner
        // Set and bind data to views
        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
                container!!.context,
                R.array.spinner_items_array,
                android.R.layout.simple_spinner_item)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currentThemeSpinner.adapter = spinnerArrayAdapter
        greenThemeSpinner.adapter = spinnerArrayAdapter
        blueThemeSpinner.adapter = spinnerArrayAdapter
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

    companion object {
        fun newInstance(): MaterialThemeInXmlFragment {
            Timber.v(LogUtils.METHOD_ONLY)
            // Create a new fragment instance
            val fragment = MaterialThemeInXmlFragment()
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