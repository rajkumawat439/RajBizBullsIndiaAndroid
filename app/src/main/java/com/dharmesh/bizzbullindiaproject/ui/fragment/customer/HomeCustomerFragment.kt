package com.dharmesh.bizzbullindiaproject.ui.fragment.customer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.adapter.*
import com.dharmesh.bizzbullindiaproject.databinding.FragmentHomeBinding
import com.dharmesh.bizzbullindiaproject.model.adaptermodel.SliderItem
import com.dharmesh.bizzbullindiaproject.ui.customer.ProjectInfoActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeCustomerFragment : Fragment() {
    var binding: FragmentHomeBinding? = null
    var foSliderAdapter: FOSliderAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(
            layoutInflater
        )
        sliderUpdate()
        loadServices()
        loadRetailView()
        loadFranchiseView()
        loadFoodView()
        binding!!.tvRegistration.setOnClickListener { v: View? ->
            val i = Intent(activity, ProjectInfoActivity::class.java)
            startActivity(i)
        }
        return binding!!.root
    }

    private fun loadServices() {
        binding!!.rvServices.setHasFixedSize(true)
        binding!!.rvServices.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val servicesAdapter = ServicesAdapter(activity!!)
        binding!!.rvServices.adapter = servicesAdapter
    }

    private fun loadRetailView() {
        binding!!.rvRental.setHasFixedSize(true)
        binding!!.rvRental.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val retailAndHouseholdAdapter = RetailAndHouseholdAdapter(activity!!)
        binding!!.rvRental.adapter = retailAndHouseholdAdapter
    }

    private fun loadFranchiseView() {
        binding!!.rvFranchise.setHasFixedSize(true)
        binding!!.rvFranchise.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val franchiseeAdapter = FranchiseeAdapter(activity!!)
        binding!!.rvFranchise.adapter = franchiseeAdapter
    }

    private fun loadFoodView() {
        binding!!.recyclerleads.setHasFixedSize(true)
        binding!!.recyclerleads.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val foodAndBeverageAdapter = FoodAndBeverageAdapter(activity!!)
        binding!!.recyclerleads.adapter = foodAndBeverageAdapter
        foodAndBeverageAdapter.clickListener = object : ClickListener {
            override fun itemClicked(view: View?, position: Int) {
//                Intent i = new Intent(getActivity(), ProjectInfoActivity.class);
//                startActivityForResult(i,99);
            }
        }
    }

    fun sliderUpdate() {
        // for Demo Purpose
        val sliderItemList: MutableList<SliderItem> = ArrayList()
        sliderItemList.add(
            SliderItem(
                "fd",
                "https://images.pexels.com/photos/9868884/pexels-photo-9868884.jpeg?auto=compress&amp;cs=tinysrgb&amp;dpr=3&amp;h=750&amp;w=1260",
                R.drawable.slider
            )
        )
        sliderItemList.add(
            SliderItem(
                "fd",
                "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                R.drawable.slider
            )
        )
        sliderItemList.add(
            SliderItem(
                "fd",
                "https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                R.drawable.slider
            )
        )
        foSliderAdapter = FOSliderAdapter(activity, sliderItemList)
        binding!!.imgsliderfo.setSliderAdapter(foSliderAdapter!!)
        binding!!.imgsliderfo.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding!!.imgsliderfo.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding!!.imgsliderfo.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        binding!!.imgsliderfo.indicatorSelectedColor = Color.WHITE
        binding!!.imgsliderfo.indicatorUnselectedColor = Color.GRAY
        binding!!.imgsliderfo.scrollTimeInSec = 3
        binding!!.imgsliderfo.isAutoCycle = true
        binding!!.imgsliderfo.startAutoCycle()
    }
}