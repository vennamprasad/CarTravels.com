package com.cartravels_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cartravels_new.screens.activities.RegistrationTypeActivity
import com.cartravels_new.utils.Tools
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ForgotPassword : AppCompatActivity(), View.OnClickListener {


    private lateinit var success: String
    private lateinit var errorres: String
    private var status: String? = ""
    private lateinit var message: String
    private lateinit var uniid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        try {

            forgot_password_click.setOnClickListener(this)
            reset_password_click.setOnClickListener(this)
            chnage_password_click.setOnClickListener(this)

        } catch (e: Exception) {
            e.message
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.forgot_password_click -> {
                val email = forgot_email.text.toString().trim()

                if (email.isEmpty()) {
                    forgot_email.error = "Email required"
                    forgot_email.requestFocus()
                    return
                }
                if (!Tools.isValidEmail(email)) {
                    forgot_email.error = "valid Email required"
                    forgot_email.requestFocus()
                    return
                }

                RetrofitClient.instance.forgotPassword(email)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            }

                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                Toast.makeText(applicationContext, response.body().toString(), Toast.LENGTH_LONG).show()
                                var jsonObject = JSONObject(response.body()?.string())
                                uniid = jsonObject.optString("uniid")
                                message = jsonObject.optString("message")
                                errorres = jsonObject.optString("error")
                                if (errorres.equals("true")) {
                                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                                } else {
                                    email_otp.visibility = View.VISIBLE
                                    forgot_password_click.visibility = View.GONE
                                    reset_password_click.visibility = View.VISIBLE
                                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

                                }
//                            val intent = Intent(this@ForgotPassword, LoginActivity::class.java)
//                            startActivity(intent)
                            }

                        })
            }
            R.id.reset_password_click -> {
                val otp = email_otp.text.toString().trim()
                if (otp.isEmpty()) {
                    email_otp.error = "OTP Number required"
                    email_otp.requestFocus()
                    return
                }
                if (otp.length != 6) {
                    email_otp.error = "OTP Number must be 6 digits "
                    email_otp.requestFocus()
                    return
                }
                RetrofitClient.instance.resetPassword(uniid, email_otp.text.toString())
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            }

                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                val jsonObject = JSONObject(response.body()?.string())
                                status = jsonObject.optString("status")
                                message = jsonObject.optString("message")
                                if (status.equals("0")) {
                                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                                } else {
                                    new_password.visibility = View.VISIBLE
                                    confirm_password.visibility = View.VISIBLE
                                    reset_password_click.visibility = View.GONE
                                    chnage_password_click.visibility = View.VISIBLE
                                }
                            }

                        })
            }
            R.id.chnage_password_click -> {
                val newpassword = new_password.text.toString().trim()
                val confrompassword = confirm_password.text.toString().trim()
                if (newpassword.isEmpty()) {
                    new_password.error = "Password required"
                    new_password.requestFocus()
                    return
                }
                if (newpassword.length < 8) {
                    new_password.error = "Password length must be greater than 8 characters"
                    new_password.requestFocus()
                    return
                }

                if (confrompassword.isEmpty()) {
                    confirm_password.error = "Confirm Password required"
                    confirm_password.requestFocus()
                    return
                }
                if (confrompassword.length < 8) {
                    confirm_password.error = "Confirm Password length must be greater than 8 characters"
                    confirm_password.requestFocus()
                    return
                }
                if (!newpassword.equals(confrompassword)) {
                    confirm_password.error = "Password & Confirm Password must be same "
                    confirm_password.requestFocus()
                    return
                }

                RetrofitClient.instance.changePassword(uniid, email_otp.text.toString(), new_password.text.toString(), confirm_password.text.toString())
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            }

                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                val jsonObject = JSONObject(response.body()?.string())
                                errorres = jsonObject.optString("error")
                                var jsonmessage = jsonObject.getJSONObject("message")
                                success = jsonmessage.optString("success")
                                Toast.makeText(applicationContext, success, Toast.LENGTH_LONG).show()

                                if (errorres.equals("false")) {
                                    val intent = Intent(this@ForgotPassword, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                        })
            }
        }

    }
}
