package com.cartravels_new.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cartravels_new.models.ChipModel
import com.cartravels_new.listners.ItemClickListener
import com.cartravels_new.R

class ChipAdapter() : RecyclerView.Adapter<ChipAdapter.ViewHolder>() {


    var context: Context? = null
    var userlist: ArrayList<ChipModel>? = null
    var itemClickListener: ItemClickListener? = null

    constructor(context: Context,
                userlist: ArrayList<ChipModel>,
                itemClick: ItemClickListener) : this() {
        this.context = context
        this.userlist = userlist
        this.itemClickListener = itemClick
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layouts, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userlist!![position], itemClickListener, userlist!!)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userlist!!.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(user: ChipModel, itemClickListener: ItemClickListener?, userlist: ArrayList<ChipModel>) {
            val textViewName = itemView.findViewById(R.id.type_view) as TextView
            textViewName.text = user.carType
            textViewName.setOnClickListener {
                itemClickListener?.ItemSelectedLisneter(userlist.get(adapterPosition))
            }
        }

    }
}