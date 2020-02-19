package com.cartravels_new.screens.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cartravels_new.R
import com.cartravels_new.models.MaterialTheme
import com.cartravels_new.utils.LogUtils
import com.cartravels_new.utils.LogUtils.getSavedInstanceStateNullMessage
import timber.log.Timber

/**
 * Simple dialog fragment that creates an alert dialog that can be themed.
 *
 * @author Steven Byle
 */
class MaterialThemeDialogFragment : DialogFragment(), DialogInterface.OnClickListener {
    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mAlertDialogTheme: MaterialTheme? = null
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
            mTitle = null
            mMessage = null
            mAlertDialogTheme = null
        } else {
            mTitle = args.getString(KEY_ARG_TITLE)
            mMessage = args.getString(KEY_ARG_MESSAGE)
            mAlertDialogTheme = args.getSerializable(KEY_ARG_ALERT_DIALOG_THEME) as MaterialTheme?
        }
        // If this is the first creation, default state variables
        if (savedInstanceState == null) {
        } else {
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
        // Create the alert dialog using the proper theme
        val parentActivity: Activity? = activity
        val alertDialogBuilder: AlertDialog.Builder
        alertDialogBuilder = if (mAlertDialogTheme != null) {
            AlertDialog.Builder(parentActivity!!, mAlertDialogTheme!!.themeResId)
        } else {
            AlertDialog.Builder(parentActivity!!)
        }
        alertDialogBuilder.setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(R.string.material_theme_dialog_button_positive, this)
                .setNegativeButton(R.string.material_theme_dialog_button_negative, this)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(true)
        return alertDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = super.onCreateView(inflater, container, savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Timber.d(LogUtils.METHOD_ONLY)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Timber.d(LogUtils.METHOD_ONLY)
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        Timber.d(LogUtils.METHOD_ONLY)
    }

    companion object {
        private const val KEY_ARG_TITLE = "KEY_ARG_TITLE"
        private const val KEY_ARG_MESSAGE = "KEY_ARG_MESSAGE"
        private const val KEY_ARG_ALERT_DIALOG_THEME = "KEY_ARG_ALERT_DIALOG_THEME"
        fun newInstance(title: String?, message: String?,
                        alertDialogTheme: MaterialTheme?): MaterialThemeDialogFragment {
            Timber.v(LogUtils.METHOD_ONLY)
            val fragment = MaterialThemeDialogFragment()
            var args = fragment.arguments
            if (args == null) {
                args = Bundle()
            }
            args.putString(KEY_ARG_TITLE, title)
            args.putString(KEY_ARG_MESSAGE, message)
            args.putSerializable(KEY_ARG_ALERT_DIALOG_THEME, alertDialogTheme)
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newInstance(context: Context, @StringRes titleResId: Int,
                        @StringRes messageResId: Int, alertDialogTheme: MaterialTheme?): MaterialThemeDialogFragment {
            return newInstance(
                    context.getString(titleResId),
                    context.getString(messageResId),
                    alertDialogTheme)
        }
    }
}