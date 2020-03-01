package com.cartravels_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cartravels_new.screens.activities.BusinessRegistrationsActivity
import com.cartravels_new.screens.activities.RegistrationActivity
import java.lang.Exception

class CategorieSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorie_selection)
    }

    fun goToRegistration(view: View) {
        try {
            val intent = Intent(this, BusinessRegistrationsActivity::class.java)
            startActivity(intent)
        }catch (e:Exception){
            e.message
        }
    }
}
