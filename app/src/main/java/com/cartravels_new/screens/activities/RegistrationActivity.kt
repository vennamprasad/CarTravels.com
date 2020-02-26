package com.cartravels_new.screens.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartravels_new.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        next.setOnClickListener {
            val intent = Intent(RegistrationActivity@ this, ImageCaptureActivity::class.java)
            startActivity(intent)
        }
    }
}
