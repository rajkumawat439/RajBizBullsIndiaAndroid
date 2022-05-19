package com.dharmesh.bizzbullindiaproject.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.dharmesh.bizzbullindiaproject.R
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentActivity
import android.widget.TextView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.dharmesh.bizzbullindiaproject.adapter.SliderViewHolder
import com.bumptech.glide.Glide
import com.dharmesh.bizzbullindiaproject.common.AppUtils
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.*
import java.util.ArrayList

class ChildAdapter(var context: Context, child_List: List<Children>) :
    RecyclerView.Adapter<ChildAdapter.viewHolder>() {
    var childlist: List<Children> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_child, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val f = childlist[position]
        holder.textinput_counter.text = (position + 1).toString()
        holder.txtname.text = f.name
        holder.txtsex.text = f.sex
        holder.txtage.text = f.age.toString() + " years old"
        holder.txtinsitutename.text = f.institutename
        holder.txtboard.text = f.univercity
    }

    override fun getItemCount(): Int {
        return childlist.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textinput_counter: AppCompatTextView
        var txtname: AppCompatTextView
        var txtsex: AppCompatTextView
        var txtage: AppCompatTextView
        var txtinsitutename: AppCompatTextView
        var txtboard: AppCompatTextView

        init {
            textinput_counter = itemView.findViewById(R.id.textinput_counter)
            txtname = itemView.findViewById(R.id.txtname)
            txtsex = itemView.findViewById(R.id.txtsex)
            txtage = itemView.findViewById(R.id.txtage)
            txtinsitutename = itemView.findViewById(R.id.txtinsitutename)
            txtboard = itemView.findViewById(R.id.txtboard)
        }
    }

    init {
        childlist = child_List
    }
}