package com.cartravels_new.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.cartravels_new.R

object CustomDialog {
	fun showWarningDialog(ctx:Context):Dialog {
		val dialog=Dialog(ctx)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setContentView(R.layout.dialog_warning)
		dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		dialog.setCancelable(false)
		return dialog
	}
}