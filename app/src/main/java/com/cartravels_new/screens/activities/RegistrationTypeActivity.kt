package com.cartravels_new.screens.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cartravels_new.R
import kotlinx.android.synthetic.main.activity_registration_type.*

class RegistrationTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_type)
        normal.setOnClickListener {
            startActivity(Intent(this, UserRegistrationActivity::class.java))
        }
        business.setOnClickListener {
            startActivity(Intent(this, CategoriesSelectionActivity::class.java))
        }
    }
}
