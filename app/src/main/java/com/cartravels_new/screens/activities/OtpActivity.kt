package com.cartravels_new.screens.activities

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.cartravels_new.R
import com.cartravels_new.utils.ConnectivityReceiver
import com.cartravels_new.utils.Constant
import com.cartravels_new.utils.Tools
import com.cartravels_new.utils.Tools.getEmail
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.linpack.dinger.database.UserSignInData
import dilipsuthar.saathi.utils.mToast
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.dialog_otp_verify.*
import java.util.*

class OtpActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private lateinit var dialog: Dialog
    private lateinit var progressDialog: ProgressDialog
    private var snackBar: Snackbar? = null
    private var isOtpSent = false
    var isDataConnected = false
    private var callbackManager: CallbackManager? = null
    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        auth = FirebaseAuth.getInstance()
        initComponent()
        getALlPermission()
        initGoogleAuth()
    }

    private fun initGoogleAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun startGoogleAuth() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN)
    }

    private fun initComponent() {
        editTextEMail.setText(getEmail(this))

        editTextMobNumber.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.toString().length == 10) Tools.enableViews(next)
                    else Tools.disableViews(next)
                }

            }
        }
        next.setOnClickListener {
            startPhoneAuth()
        }

        login_btn_google.setOnClickListener {
            startGoogleAuth()
        }
        login_btn_facebook.setOnClickListener() {
            startFacebookAuth()
        }

    }

    private fun startFacebookAuth() {
        // Login
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d(TAG, "facebook:onSuccess:$loginResult")
                        handleFacebookAccessToken(loginResult.accessToken)
                    }

                    override fun onCancel() {
                        Log.d(TAG, "facebook:onCancel")
                        // [START_EXCLUDE]
                        updateUI(null)
                        // [END_EXCLUDE]
                    }

                    override fun onError(error: FacebookException) {
                        Log.d(TAG, "facebook:onError", error)
                        // [START_EXCLUDE]
                        updateUI(null)
                        // [END_EXCLUDE]
                    }
                })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")
        // [START_EXCLUDE silent]
        Tools.visibleViews(progressBar)
        // [END_EXCLUDE]

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // [START_EXCLUDE]
                    Tools.disableViews(progressBar)
                    // [END_EXCLUDE]
                }
    }

    private fun updateUI(user: FirebaseUser?) {
        Tools.disableViews(progressBar)
        if (user != null) {

        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun showOTPDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_otp_verify)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        startTimer(30)

        dialog.buttonConfirm.setOnClickListener {
            if (!dialog.editTextOTP.text.toString().isEmpty()) {
                Tools.visibleViews(dialog.progressBarConfirm)
                Tools.disableViews(dialog.buttonConfirm, dialog.editTextOTP)
                val otp = dialog.editTextOTP.text.toString()
                //confirm check
                if (otp.equals("123456")) {
                    dialog.dismiss()
                    goToHomeScreen()
                } else {
                    // The verification code entered was invalid
                    dialog.editTextOTP.error = "Invalid Code"
                    Tools.inVisibleViews(dialog.progressBarConfirm, type = 1)
                    Tools.enableViews(dialog.buttonConfirm, dialog.editTextOTP)
                }

            } else {
                dialog.editTextOTP.error = "Length must be 6"
            }

        }
        dialog.textResendCode.setOnClickListener {
            mToast.showToast(this, "Resending", Toast.LENGTH_SHORT)
            resendVerificationCode(editTextMobNumber.text.toString())
        }
    }

    private fun resendVerificationCode(number: String) {
        startTimer(30)
        getPhoneNumber(number)
        //req otp
        isOtpSent = true
    }


    private fun startTimer(second: Long) {
        object : CountDownTimer(second * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Tools.visibleViews(dialog.textTimer, dialog.progressBarTimer)
                Tools.disableViews(dialog.textResendCode)
                dialog.textTimer.text = (millisUntilFinished / 1000).toString()
            }


            override fun onFinish() {
                dialog.textResendCode.setTextColor(resources.getColor(R.color.material_red_400))
                Tools.inVisibleViews(dialog.textTimer, dialog.progressBarTimer, type = 0)
                Tools.enableViews(dialog.textResendCode)
            }

        }.start()
    }


    private fun requestVerificationCode(number: String) {
        getPhoneNumber(number);
        //start text request
    }

    private fun getPhoneNumber(number: String): String {
        val countryCode = textCountryCode.text.toString()
        return "$countryCode$number"
    }

    private fun startPhoneAuth() {
        val phoneNumber: String = editTextMobNumber.text.toString()

        if (validatePhoneNumber(phoneNumber)) {
            Tools.inVisibleViews(progressBar, type = 0)
            Tools.enableViews(editTextMobNumber, next)
            showOTPDialog()
            Tools.visibleViews(progressBar)
            Tools.disableViews(next, editTextMobNumber)

            requestVerificationCode(phoneNumber)

            // Start timer when request for OTP
            object : CountDownTimer(20 * 1000, 1000) {
                override fun onFinish() {
                    if (!isOtpSent) {
                        mToast.showToast(applicationContext, "OTP not send.", Toast.LENGTH_SHORT)
                        Tools.inVisibleViews(progressBar, type = 0)
                        Tools.enableViews(editTextMobNumber, next)
                    }
                }

                override fun onTick(millisUntilFinished: Long) {

                }
            }.start()
        }
    }

    private fun validatePhoneNumber(num: String): Boolean {
        if (TextUtils.isEmpty(num)) {
            editTextMobNumber.error = "Please enter number"
            return false
        } else if (num.length < 10) {
            editTextMobNumber.error = "Enter valid number"
            return false
        }

        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)

                signInWithGoogleAuth(account!!)
                showLoadingDialog("Authenticating...")

            } catch (e: ApiException) {

                Log.w(TAG, "onActivityResult: Google sign in failed", e)

            }
        }
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun showLoadingDialog(msg: String) {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(msg)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun signInWithGoogleAuth(account: GoogleSignInAccount) {
        Log.d(TAG, "signInWithGoogleAuth: ${account.id!!}")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {

                val user = task.result?.user

                //Store Google user data into database for later use
                val userData = UserSignInData.setGoogleUserData(user)

                val newUser: Boolean? = task.result!!.additionalUserInfo?.isNewUser
                newUser?.let {
                    if (newUser) goToProfileScreen(userData)
                    else {
                        goToHomeScreen()
                    }
                }

            } else {

                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithGoogleAuth: signInWithCredential:failure", task.exception)
                mToast.showToast(applicationContext, "Authentication failed", Toast.LENGTH_SHORT)
                progressDialog.cancel()

            }

        }

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isDataConnected = isConnected
        showNetworkMessage(isConnected)
    }

    private fun getALlPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCOUNT_MANAGER,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.ACCESS_WIFI_STATE)
        ActivityCompat.requestPermissions(this, permissions, MULT_PERMISSION_RC)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar = Snackbar.make(linear_layout, "You are offline", Snackbar.LENGTH_LONG)
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            Tools.setSnackBarBgColor(applicationContext, snackBar!!, R.color.material_red_500)
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    companion object {
        const val TAG: String = "OtpActivity"
        const val REQUEST_CODE_SIGN_IN: Int = 2
        const val MULT_PERMISSION_RC: Int = 1
    }

    private fun goToProfileScreen(userData: UserSignInData) {
        val bundle = Bundle()
        bundle.putString(Constant.USER_NAME, userData.getName)
        bundle.putString(Constant.USER_EMAIL, userData.getEmail)
        bundle.putString(Constant.USER_MOB_NUMBER, userData.getPhoneNum)
        bundle.putString(Constant.USER_PROFILE_URL, userData.getPhotoUrl)

        val i = Intent(this, RegistrationTypeActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
        finish()

    }

    private fun goToHomeScreen() {
        startActivity(Intent(this, RegistrationTypeActivity::class.java))
        finish()
    }
}
