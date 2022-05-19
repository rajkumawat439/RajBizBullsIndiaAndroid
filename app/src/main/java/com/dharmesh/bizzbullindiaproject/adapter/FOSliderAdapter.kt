package com.dharmesh.bizzbullindiaproject.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dharmesh.bizzbullindiaproject.databinding.ItemImageSliderBinding
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter


class FOSliderAdapter : SliderViewAdapter<SliderViewHolder> {
    var binding: ItemImageSliderBinding? = null
    private var context: Context? = null
    private var mSliderItems: MutableList<SliderItem> = ArrayList()

    constructor(context: Context?)
    constructor(context: Context?, sliderItemList: MutableList<SliderItem>) {
        this.context = context
        mSliderItems = sliderItemList
    }

    fun renewItems(sliderItems: MutableList<SliderItem>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderItem) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        binding = ItemImageSliderBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        val view: View = binding!!.root
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        val sliderItem = mSliderItems[position]
        binding!!.slideinfo.text = sliderItem.description
        binding!!.slideinfo.textSize = 16f
        binding!!.slideinfo.setTextColor(Color.WHITE)
        //        Glide.with(binding.getRoot())
//                .load(sliderItem.getImageUrl())
//                .fitCenter()
//                .into(binding.slideview);
        //Resource
        Glide.with(binding!!.root)
            .load(sliderItem.imageres)
            .fitCenter()
            .into(binding!!.slideview)
        binding!!.root.setOnClickListener { }
    }

    //slider view count could be dynamic size
    override fun getCount(): Int {
        return mSliderItems.size
    }

}