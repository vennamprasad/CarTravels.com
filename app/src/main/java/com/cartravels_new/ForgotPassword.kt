package com.cartravels_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cartravels_new.screens.activities.RegistrationTypeActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ForgotPassword : AppCompatActivity() {

    private lateinit var success: String
    private lateinit var errorres: String
    private var status: String? = ""
    private lateinit var message: String
    private lateinit var uniid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        try {

            forgot_password_click.setOnClickListener {

                RetrofitClient.instance.forgotPassword(email.text.toString())
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

            reset_password_click.setOnClickListener {
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


            chnage_password_click.setOnClickListener {
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

        } catch (e: Exception) {
            e.message
        }
    }
}
