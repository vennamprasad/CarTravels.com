package com.cartravels_new.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cartravels_new.CarTravels
import com.cartravels_new.R


fun hideSystemUI(context: AppCompatActivity) {
    val decorView = context.window.decorView
    decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
}

fun showSystemUI(context: AppCompatActivity) {
    val decorView = context.window.decorView
    decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

fun isValidPhoneNumber(phoneNumber: CharSequence): Boolean {
    return if (!TextUtils.isEmpty(phoneNumber)) {
        Patterns.PHONE.matcher(phoneNumber).matches()
    } else false
}

@SuppressLint("SetJavaScriptEnabled")
fun setupWebView(webOverview: WebView, progressBar1: ProgressBar, file_name: String) {
    webOverview.settings.javaScriptEnabled = true
    webOverview.settings.domStorageEnabled = true
    webOverview.overScrollMode = WebView.OVER_SCROLL_NEVER
    webOverview.loadUrl(file_name)
    val webViewClient: WebViewClient = object : WebViewClient() {
        @SuppressLint("ObsoleteSdkInt")
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view?.loadUrl(request?.url.toString())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar1.visibility = View.GONE
            webOverview.visibility = View.VISIBLE
        }
    }
    webOverview.webViewClient = webViewClient
}

fun isNetworkOk(): Boolean {
    val manager = CarTravels.instance?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkInfos = manager.allNetworkInfo
    for (info in networkInfos) {
        if (info.state == NetworkInfo.State.CONNECTED) {
            return true
        }
    }
    return false
}

fun setAnimation(viewToAnimate: View, position: Int, lastPosition: Int, context: Context) {
    val las: Int = lastPosition
    // If the bound view wasn't previously displayed on screen, it's animated
    if (position > las) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        animation.duration = 1000
        viewToAnimate.startAnimation(animation)
    } else if (position < las) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        viewToAnimate.startAnimation(animation)
    }
}

fun launchDismissDlg(context: AppCompatActivity, title: String, message: String, positive: String, negative: String, forceclose: Int) {
    // build alert dialog
    val dialogBuilder = AlertDialog.Builder(context)
    with(dialogBuilder) {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positive) { dialog, id ->
            if (forceclose == 0) context.finish() else if (forceclose == 1) {
                context.finishAffinity()
            } else {
                dialog.cancel()
            }
        }
        setNegativeButton(negative) { dialog, id -> dialog.dismiss() }
        val alertDialog = dialogBuilder.create()

        alertDialog.show()
        alertDialog.window?.setBackgroundDrawable(context.getDrawable(R.drawable.dialog_bg))
    }
}