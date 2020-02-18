package com.cartravels.screens.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.cartravels.R
import com.cartravels.adapters.HomePagerAdapter
import com.cartravels.models.MaterialTheme
import com.cartravels.screens.fragments.SetThemeDialogFragment.Companion.newInstance
import com.cartravels.utils.DialogUtils.showDialogFragment
import com.cartravels.utils.LogUtils
import com.cartravels.utils.LogUtils.getSavedInstanceStateNullMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

/**
 * Home activity which holds the app bar (toolbar + tab layout) and a view pager with the themed
 * fragments.
 *
 * @author Steven Byle
 */
class ThemeDemoActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentTheme: MaterialTheme? = null
    override fun onCreate(savedInstanceState: Bundle?) { // Get passed in parameters
        val args = intent.extras
        // If no parameters were passed in, default them
        mCurrentTheme = if (args == null) {
            null
        } else {
            args.getSerializable(KEY_ARG_CURRENT_THEME) as MaterialTheme?
        }
        // If not set, default the theme
        if (mCurrentTheme == null) {
            mCurrentTheme = MaterialTheme.THEME_RED
        }
        // Theme must be set before calling super or setContentView
        setTheme(mCurrentTheme!!.themeResId)
        // Handle super calls after setting the theme
        super.onCreate(savedInstanceState)
        Timber.v(getSavedInstanceStateNullMessage(savedInstanceState))
        // Set the content view to a layout and reference views
        setContentView(R.layout.activity_theme_demo)
        val toolbar = findViewById<View>(R.id.activity_home_toolbar) as Toolbar
        val viewPager = findViewById<View>(R.id.activity_home_pager) as ViewPager
        val tabLayout = findViewById<View>(R.id.activity_home_tab_layout) as TabLayout
        // If this is the first creation, default state variables
        if (savedInstanceState == null) {
        } else {
        }
        // Set and bind data to views
        setSupportActionBar(toolbar)
        val viewPagerAdapter = HomePagerAdapter(this, supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        val fab = findViewById<View>(R.id.activity_home_fab) as FloatingActionButton
        fab.setOnClickListener(this)
    }

    public override fun onStart() {
        super.onStart()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    public override fun onStop() {
        super.onStop()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    public override fun onDestroy() {
        super.onDestroy()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.v(LogUtils.METHOD_ONLY)
    }

    public override fun onPause() {
        super.onPause()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    public override fun onResume() {
        super.onResume()
        Timber.v(LogUtils.METHOD_ONLY)
    }

    override fun onClick(v: View) {
        Timber.d(LogUtils.METHOD_ONLY)
        if (v.id == R.id.activity_home_fab) {
            val dialogFragment: DialogFragment = newInstance(mCurrentTheme)
            showDialogFragment(supportFragmentManager, dialogFragment)
        } else {
            Timber.w("Unknown view clicked")
        }
    }

    companion object {
        private const val KEY_ARG_CURRENT_THEME = "KEY_ARG_CURRENT_THEME"
        fun newInstanceIntent(context: Context?, currentTheme: MaterialTheme?): Intent {
            Timber.v(LogUtils.METHOD_ONLY)
            // Create an intent that will start the activity
            val intent = Intent(context, ThemeDemoActivity::class.java)
            // Get arguments passed in, if any
            var args = intent.extras
            if (args == null) {
                args = Bundle()
            }
            // Add parameters to the argument bundle
            args.putSerializable(KEY_ARG_CURRENT_THEME, currentTheme)
            intent.putExtras(args)
            return intent
        }
    }
}