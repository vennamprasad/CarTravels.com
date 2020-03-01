package com.cartravels_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cartravels_new.screens.activities.RegistrationActivity
import com.cartravels_new.screens.activities.RegistrationTypeActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.email
import kotlinx.android.synthetic.main.activity_registration.password
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var message: String
    private lateinit var errorres: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign_in.setOnClickListener {
            RetrofitClient.instance.loginUser(email.text.toString(), password.text.toString())
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            val jsonObject = JSONObject(response.body()?.string())
                            errorres = jsonObject.optString("error")
                            message = jsonObject.optString("message")
                            if (errorres.equals("true")) {
                                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext, "Login Successfully", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@LoginActivity, RegistrationTypeActivity::class.java)
                                startActivity(intent)
                                email.setText("")
                                password.setText("")
                            }
                        }

                    })

        }

        sign_up.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        forgot_password.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }
}
