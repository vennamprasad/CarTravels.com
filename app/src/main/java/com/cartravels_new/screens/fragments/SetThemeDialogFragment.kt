package com.cartravels_new.screens.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cartravels_new.R
import com.cartravels_new.models.MaterialTheme
import com.cartravels_new.screens.activities.ThemeDemoActivity
import com.cartravels_new.utils.LogUtils
import com.cartravels_new.utils.LogUtils.getSavedInstanceStateNullMessage
import timber.log.Timber
import java.util.*

/**
 * Dialog fragment used to select a new theme. Upon selection, this starts a new home activity and
 * clears the task stack.
 *
 * @author Steven Byle
 */
class SetThemeDialogFragment : DialogFragment(), DialogInterface.OnClickListener {
    private var mCurrentTheme: MaterialTheme? = null
    private var mCurrentSelectedThemeIndex = 0
    private var mSingleChoiceOnClickListener: SingleChoiceOnClickListener? = null
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
        mCurrentTheme = if (args == null) {
            null
        } else {
            args.getSerializable(KEY_ARG_CURRENT_THEME) as MaterialTheme?
        }
        mSingleChoiceOnClickListener = SingleChoiceOnClickListener()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
        val themeNameList: MutableList<String> = ArrayList()
        for (materialTheme in MaterialTheme.themeList!!) {
            themeNameList.add(getString(materialTheme.nameResId))
        }
        mCurrentSelectedThemeIndex = MaterialTheme.themeList!!.indexOf(mCurrentTheme)
        val themeNameArray = themeNameList.toTypedArray()
        val parentActivity: Activity? = activity
        val alertDialogBuilder = AlertDialog.Builder(parentActivity!!)
                .setTitle(R.string.set_theme_dialog_title)
                .setNegativeButton(R.string.set_theme_dialog_button_negative, this)
                .setSingleChoiceItems(themeNameArray, mCurrentSelectedThemeIndex, mSingleChoiceOnClickListener)
        val dialog: Dialog = alertDialogBuilder.create()
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Timber.d(LogUtils.METHOD_ONLY)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Timber.d(LogUtils.METHOD_ONLY)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
    }

    override fun onStart() {
        super.onStart()
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = super.onCreateView(inflater, container, savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
    }

    override fun onResume() {
        super.onResume()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onPause() {
        super.onPause()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        Timber.d(LogUtils.METHOD_ONLY)
        when (which) {
            DialogInterface.BUTTON_NEGATIVE -> {
            }
        }
    }

    private inner class SingleChoiceOnClickListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) { // Upon selection, figure out which theme was selected
            mCurrentSelectedThemeIndex = which
            val newTheme = MaterialTheme.themeList?.get(mCurrentSelectedThemeIndex)
            // If the theme is new, set it and start a new activity
            if (mCurrentTheme != newTheme) {
                val parentActivity: Activity? = activity
                val intent = ThemeDemoActivity.newInstanceIntent(parentActivity, newTheme)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                parentActivity!!.startActivity(intent)
            }
        }
    }

    companion object {
        private const val KEY_ARG_CURRENT_THEME = "KEY_ARG_CURRENT_THEME"
        @JvmStatic
        fun newInstance(currentTheme: MaterialTheme?): SetThemeDialogFragment {
            Timber.v(LogUtils.METHOD_ONLY)
            val fragment = SetThemeDialogFragment()
            var args = fragment.arguments
            if (args == null) {
                args = Bundle()
            }
            args.putSerializable(KEY_ARG_CURRENT_THEME, currentTheme)
            fragment.arguments = args
            return fragment
        }
    }
}