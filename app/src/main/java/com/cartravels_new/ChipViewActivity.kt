package com.cartravels_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_chip_view.*

class ChipViewActivity : AppCompatActivity(), ItemClickListener {

    override fun ItemSelectedLisneter(type: ChipModel) {
        val chip = Chip(this)
        chip.setText(type.carType)
        chip.isChipIconVisible = true
        chip.isCloseIconVisible = true
        chip.isCheckable = false
        chip.isClickable = false
        chip.setOnCloseIconClickListener(View.OnClickListener {
            chipGroup.removeView(chip)
        })
        chipGroup.addView(chip)
        chipGroup.visibility = View.VISIBLE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chip_view)


        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recyclerview) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<ChipModel>()

        //adding some dummy data to the list
        users.add(ChipModel("Car1"))
        users.add(ChipModel("Car2"))
        users.add(ChipModel("Car3"))
        users.add(ChipModel("Car4"))

        //creating our adapter
        val adapter = ChipAdapter(this@ChipViewActivity, users, this)

        recyclerView.adapter = adapter

        et_type.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var type = p0.toString()
                val types = ArrayList<ChipModel>()
                for (chip in users) {
                    if (chip.carType.contains(et_type.text.toString())) {
                        types.add(chip)
                    }
                }
                val adapter = ChipAdapter(this@ChipViewActivity, types, this@ChipViewActivity)

                recyclerView.adapter = adapter
            }
        })
    }
}

private fun String.contains(etType: EditText?): Boolean {
    return true
}




