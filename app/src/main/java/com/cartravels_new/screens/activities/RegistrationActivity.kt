package com.cartravels_new.screens.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cartravels_new.R
import com.cartravels_new.RetrofitClient
import com.cartravels_new.utils.Tools.isValidEmail
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {


	private lateinit var message: String
	private lateinit var errorres: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_registration)

		next.setOnClickListener(this)
		login.setOnClickListener {
			val intent = Intent(this, LoginActivity::class.java)
			startActivity(intent)
		}

	}

	override fun onClick(p0: View?) {
		when (p0?.id) {
			R.id.next -> {
				val name = rg_name.text.toString().trim()
				val email = rg_email.text.toString().trim()
				val mobile = rg_mobile.text.toString().trim()
				val password = rg_password.text.toString().trim()
				val confirmPassword = confirm_password.text.toString().trim()

				if (name.isEmpty()) {
					rg_name.error = "Name required"
					rg_name.requestFocus()
					return
				}

				if (email.isEmpty()) {
					rg_email.error = "Email required"
					rg_email.requestFocus()
					return
				}
				if (!isValidEmail(email)) {
					rg_email.error = "valid Email required"
					rg_email.requestFocus()
					return
				}
				if (mobile.isEmpty()) {
					rg_mobile.error = "Mobile Number required"
					rg_mobile.requestFocus()
					return
				}
				if (mobile.length != 10) {
					rg_mobile.error = "Mobile Number must be 10 digits "
					rg_mobile.requestFocus()
					return
				}
				if (mobile.startsWith("1") || mobile.startsWith("2") || mobile.startsWith("3")
						|| mobile.startsWith("4") || mobile.startsWith("5")) {
					rg_mobile.error = "Valid Mobile Number required"
					rg_mobile.requestFocus()
					return
				}

				if (password.isEmpty()) {
					rg_password.error = "Password required"
					rg_password.requestFocus()
					return
				}
				if (password.length < 8) {
					rg_password.error = "Password length must be greater than 8 characters"
					rg_password.requestFocus()
					return
				}

				if (confirmPassword.isEmpty()) {
					confirm_password.error = "Confirm Password required"
					confirm_password.requestFocus()
					return
				}
				if (confirmPassword.length < 8) {
					confirm_password.error = "Confirm Password length must be greater than 8 characters"
					confirm_password.requestFocus()
					return
				}
				if (!password.equals(confirmPassword)) {
					confirm_password.error = "Password & Confirm Password must be same "
					confirm_password.requestFocus()
					return
				}



				RetrofitClient.instance.createUser(name, email, mobile, password, confirmPassword)
						.enqueue(object : Callback<ResponseBody> {
							override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
								Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
							}

							override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
								val jsonObject = JSONObject(response.body()!!.string())
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
		}
	}
}
