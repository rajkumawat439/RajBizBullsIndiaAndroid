package com.dharmesh.bizzbullindiaproject.adapter

import android.content.Context
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Academic
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.dharmesh.bizzbullindiaproject.R
import androidx.appcompat.widget.AppCompatTextView
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Family
import androidx.fragment.app.FragmentActivity
import android.widget.TextView
import com.smarteist.autoimageslider.SliderViewAdapter
import com.dharmesh.bizzbullindiaproject.adapter.SliderViewHolder
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.SliderItem
import com.bumptech.glide.Glide
import com.dharmesh.bizzbullindiaproject.common.AppUtils
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Personalref
import java.util.ArrayList

class AcademicAdapter(var context: Context, academic_List: List<Academic>) :
    RecyclerView.Adapter<AcademicAdapter.viewHolder>() {
    var academicList: List<Academic> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_academic, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val aceAcademic = academicList[position]
        holder.textinput_counter.text = (position + 1).toString()
        holder.txtqulaitification.text = aceAcademic.qualification
        holder.txtyearofpassing.text = aceAcademic.year
        holder.txtpercentageexam.text = aceAcademic.percentage.toString() + "%"
        holder.txtinsitutename.text = aceAcademic.institutename
    }

    override fun getItemCount(): Int {
        return academicList.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textinput_counter: AppCompatTextView
        var txtqulaitification: AppCompatTextView
        var txtyearofpassing: AppCompatTextView
        var txtpercentageexam: AppCompatTextView
        var txtinsitutename: AppCompatTextView

        init {
            textinput_counter = itemView.findViewById(R.id.textinput_counter)
            txtqulaitification = itemView.findViewById(R.id.txtqulaitification)
            txtyearofpassing = itemView.findViewById(R.id.txtyearofpassing)
            txtpercentageexam = itemView.findViewById(R.id.txtpercentageexam)
            txtinsitutename = itemView.findViewById(R.id.txtinsitutename)
        }
    }

    init {
        academicList = academic_List
    }
}