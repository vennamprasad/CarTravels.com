package com.cartravels_new.screens.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cartravels_new.LoginActivity
import com.cartravels_new.R
import com.cartravels_new.RetrofitClient
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    private lateinit var message: String
    private lateinit var errorres: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        next.setOnClickListener {
            //            val intent = Intent(RegistrationActivity@ this, ImageCaptureActivity::class.java)
//            startActivity(intent)


            RetrofitClient.instance.createUser(name.text.toString(), email.text.toString(), mobile.text.toString(), password.text.toString(), confirmpassword.text.toString())
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
                                Toast.makeText(applicationContext, "Registration Successfully", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        }

                    })
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
