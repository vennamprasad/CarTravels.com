package com.cartravels_new.screens.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cartravels_new.R
import java.lang.Exception

class CategoriesSelectionActivity : AppCompatActivity() {

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
