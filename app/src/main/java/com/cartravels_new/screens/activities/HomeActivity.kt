package com.cartravels_new.screens.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.cartravels_new.R
import com.cartravels_new.screens.fragments.HomeFragment
import com.cartravels_new.utils.Tools
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_drawer_header_home.view.*

class HomeActivity : AppCompatActivity() {

	companion object {
		const val TAG: String = "HomeActivity_debug"
	}

	private lateinit var actionBar: ActionBar
	private lateinit var headerView: View
	// Fragments
	private lateinit var homeFragment: HomeFragment


	override fun onCreate(savedInstanceState: Bundle?) {

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		initToolbar()
		initTheme()
		initComponent()
		initNavigationDrawer()

		// Set default fragment
		displayFragment(homeFragment)
		navView.setCheckedItem(R.id.nav_ride_now)
	}

	override fun onResume() {
		super.onResume()
	}


	override fun onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START))
			drawerLayout.closeDrawers()
		else
			super.onBackPressed()
	}

	private fun initTheme() {

	}

	private fun initToolbar() {
		toolbar.popupTheme = R.style.ThemeOverlay_AppCompat_Dark_ActionBar
		setSupportActionBar(toolbar)
		actionBar = supportActionBar!!
		actionBar.title = getString(R.string.app_name)
		actionBar.elevation = 0.0F
		actionBar.setDisplayHomeAsUpEnabled(true)
		actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
		Tools.setSystemBarColor(this, R.color.grey_90)
		Tools.changeNavigationIconColor(toolbar, resources.getColor(R.color.red_500))
	}

	private fun initComponent() {

		homeFragment = HomeFragment()

	}

	private fun initNavigationDrawer() {
		val toggle = object : ActionBarDrawerToggle(
				this,
				drawerLayout,
				toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		) {
		}
		drawerLayout.addDrawerListener(toggle)
		toggle.drawerArrowDrawable.color = resources.getColor(R.color.red_500)
		toggle.syncState()

		navView.setNavigationItemSelectedListener { menuItem ->
			//            mToast.showToast(applicationContext, "${menuItem.title} Selected", Toast.LENGTH_SHORT)
			drawerLayout.closeDrawers()

			when (menuItem.itemId) {
				R.id.nav_sign_out -> {
					sendToAuth()
				}
				R.id.nav_ride_now -> displayFragment(homeFragment)
			}

			return@setNavigationItemSelectedListener true
		}

		headerView = navView.getHeaderView(0)
	}



	private fun sendToAuth() {
		startActivity(Intent(this, LoginActivity::class.java))
		finish()
	}

	private fun displayFragment(fragment: Fragment?) {

		Handler().postDelayed({
			if (fragment != null) {
				val transaction = supportFragmentManager.beginTransaction()
				transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
				transaction.replace(R.id.fragment, fragment)
				if (fragment == homeFragment)
					transaction.setPrimaryNavigationFragment(fragment)
				transaction.commit()
			}
		}, 220)
	}
}
