package com.dharmesh.bizzbullindiaproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.dharmesh.bizzbullindiaproject.R

class FoodAndBeverageAdapter(activity: FragmentActivity) :
    RecyclerView.Adapter<FoodAndBeverageAdapter.ViewHolder>() {
    var context: Context
    var iconsName = arrayOf(
        "Beauty & Health",
        "Food & Beverage",
        "Education",
        "Retail and Household",
        "Automotive & Spares"
    ) // ,"Fashion & Textile","Sports, Fitness & Entertainment", "Agri & Organic Products"
    var icons = intArrayOf(
        R.drawable.uncle_kitchen,
        R.drawable.icy_doves,
        R.drawable.freaky_bakes,
        R.drawable.shahi_shagun,
        R.drawable.treat_more
    )
    var clickListener: ClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = iconsName[position]
        holder.imageView.setImageResource(icons[position])
        holder.itemView.setOnClickListener { v: View? -> clickListener!!.itemClicked(v, position) }
    }

    override fun getItemCount(): Int {
        return iconsName.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView

        init {
            imageView = itemView.findViewById(R.id.iv_top_small_card)
            textView = itemView.findViewById(R.id.tv_top_small_card)
        }
    }

    init {
        context = activity
    }
}