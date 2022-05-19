package com.dharmesh.bizzbullindiaproject

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.dharmesh.bizzbullindiaproject.common.AppConstants
import com.dharmesh.bizzbullindiaproject.common.AppUtils.toast
import com.dharmesh.bizzbullindiaproject.common.Preferences
import com.dharmesh.bizzbullindiaproject.databinding.ActivityDashboardBinding
import com.dharmesh.bizzbullindiaproject.ui.fragment.customer.CustomerFOStatusFragment
import com.dharmesh.bizzbullindiaproject.ui.fragment.customer.HomeCustomerFragment
import com.google.android.material.progressindicator.LinearProgressIndicator

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    var binding: ActivityDashboardBinding? = null
    var txtLogout: ConstraintLayout? = null
    var appConstants: AppConstants? = null
    var count = 0
    var homeCustomerFragment: HomeCustomerFragment? = null
    var customerFOStatusFragment: CustomerFOStatusFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    fun init() {
        appConstants = AppConstants(this)
        binding!!.bottomNavigationView.background = null
        binding!!.bottomNavigationView.setOnItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.navigation_fohome -> supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, homeCustomerFragment!!).commit()
                R.id.navigation_forevenue -> supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, customerFOStatusFragment!!).commit()
            }
            true
        }
        binding!!.navfo.bringToFront()
        val hView = binding!!.navfo.getHeaderView(0)
        val lm = hView.findViewById<LinearProgressIndicator>(R.id.progressBarprofileupdate)
        lm.progress = 25
        val nav_user = hView.findViewById<AppCompatTextView>(R.id.txtfoname)
        val nav_mobile = hView.findViewById<AppCompatTextView>(R.id.txtfomobile)
        val nav_email = hView.findViewById<AppCompatTextView>(R.id.txtfoemail)
        txtLogout = hView.findViewById(R.id.btnmenulogout)
        nav_user.text =
            appConstants!!.getPrefValue(Preferences.USER_FIRSTNAME) + " " + appConstants!!.getPrefValue(
                Preferences.USER_LASTNAME
            )
        nav_mobile.text = appConstants!!.getPrefValue(Preferences.MOBILE_NUMBER)
        nav_email.text = appConstants!!.getPrefValue(Preferences.EMAIL)
        txtLogout?.setOnClickListener(this)
        binding!!.layoutdraweropen.setOnClickListener(this)
        homeCustomerFragment = HomeCustomerFragment()
        customerFOStatusFragment = CustomerFOStatusFragment()
        binding!!.bottomNavigationView.selectedItemId = R.id.navigation_fohome

//        loadFragment(homeCustomerFragment);
    }

    override fun onClick(view: View) {
        if (view === txtLogout) {
            finish()
            appConstants!!.forcelogout()
        }
        if (view === binding!!.layoutdraweropen) {
            binding!!.drawerfomainlayout.openDrawer(Gravity.LEFT)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (count == 1) {
            super.onBackPressed()
        } else {
            toast(this, "Press once again to exit!")
        }
        count++
    }

    //    @Override
    //    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    //
    //    }
    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit()
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "Dashboard"
    }
}