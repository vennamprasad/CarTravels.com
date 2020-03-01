package com.cartravels_new.utils

import android.Manifest.permission.*
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern


object Tools {
	var PERMISSIONS: Array<String>? = arrayOf(READ_CONTACTS, WRITE_CONTACTS, READ_CALENDAR, WRITE_CALENDAR, GET_ACCOUNTS)

	fun hasPermissions(context: Context?): Boolean {
		if (context != null && PERMISSIONS != null) {
			for (permission in PERMISSIONS!!) {
				if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
					return false
				}
			}
		}
		return true
	}


	fun getEmail(context: Context?): String? {
		val emailPattern: Pattern = Patterns.EMAIL_ADDRESS // API level 8+
		val accounts = AccountManager.get(context).accounts
		for (account in accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				return account.name
			}
		}
		return ""
	}
	fun setSystemBarColor(act:Activity,@ColorRes color:Int) {
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
			val window=act.window
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
			window.statusBarColor=act.resources.getColor(color)
		}
	}
	
	fun changeNavigationIconColor(toolbar:Toolbar,@ColorInt color:Int) {
		val drawable=toolbar.navigationIcon
		drawable?.mutate()
		drawable?.setColorFilter(color,PorterDuff.Mode.SRC_ATOP)
	}
	
	fun enableViews(vararg views:View) {
		for(v in views) {
			v.isEnabled=true
		}
	}
	
	fun disableViews(vararg views:View) {
		for(v in views) {
			v.isEnabled=false
		}
	}
	
	fun visibleViews(vararg views:View) {
		for(v in views) {
			v.visibility=View.VISIBLE
		}
	}
	
	fun inVisibleViews(vararg views:View,type:Int) {
		
		if(type==0) {
			for(v in views) {
				v.visibility=View.INVISIBLE
			}
		} else {
			for(v in views) {
				v.visibility=View.GONE
			}
		}
	}
	
	fun setSnackBarBgColor(ctx:Context,snackbar:Snackbar,color:Int) {
		val sbView=snackbar.view
		sbView.setBackgroundColor(ctx.resources.getColor(color))
	}

	fun isValidEmail(target: CharSequence): Boolean {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
	}
	private fun isValidPhoneNumber(phone: String): Boolean {

		return if (phone.trim { it <= ' ' } != "" && phone.length > 10) {
			Patterns.PHONE.matcher(phone).matches()
		} else false

	}
}
