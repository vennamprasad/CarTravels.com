package com.cartravels_new.screens.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cartravels_new.R
import com.cartravels_new.RetrofitClient
import com.cartravels_new.screens.activities.RegistrationActivity
import com.cartravels_new.screens.activities.RegistrationTypeActivity
import com.cartravels_new.utils.Tools.isValidEmail
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnClickListener {


	private lateinit var message: String
	private lateinit var errorres: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		sign_in.setOnClickListener(this)

		sign_up.setOnClickListener {
			val intent = Intent(this, RegistrationActivity::class.java)
			startActivity(intent)
		}

		forgot_password.setOnClickListener {
			val intent = Intent(this, ForgotPassword::class.java)
			startActivity(intent)
		}
	}

	override fun onClick(p0: View?) {
		when (p0?.id) {
			R.id.sign_in -> {
				val email = login_email.text.toString().trim()
				val password = login_password.text.toString().trim()

				if (email.isEmpty()) {
					login_email.error = "Email required"
					login_email.requestFocus()
					return
				}
				if (!isValidEmail(email)) {
					login_email.error = "valid Email required"
					login_email.requestFocus()
					return
				}

				if (password.isEmpty()) {
					login_password.error = "Password required"
					login_password.requestFocus()
					return
				}
				if (password.length < 8) {
					login_password.error = "Password length must be greater than 8 characters"
					login_password.requestFocus()
					return
				}

				onLoginRequest(email, password)


			}
		}
	}


	fun onLoginRequest(email: String, password: String) {
		RetrofitClient.instance.loginUser(email, password)
				.enqueue(object : Callback<ResponseBody> {
					override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
						Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
					}

					override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
						val jsonObject = JSONObject(response.body()!!.string())
						errorres = jsonObject.optString("error")
						message = jsonObject.optString("message")
						when {
							errorres.equals("true") -> {
								//Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
							}
							else -> {
								Toast.makeText(applicationContext, "Login Successfully", Toast.LENGTH_LONG).show()
								val intent = Intent(this@LoginActivity, HomeActivity::class.java)
								startActivity(intent)
								login_email.setText("")
								login_password.setText("")
							}
						}
					}

				})
	}

}
