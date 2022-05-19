package com.dharmesh.bizzbullindiaproject.ui.fragment.customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.act.SignupActivity
import com.dharmesh.bizzbullindiaproject.databinding.FragmentStatusBinding

class CustomerFOStatusFragment : Fragment(), View.OnClickListener {
    var binding: FragmentStatusBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatusBinding.inflate(
            layoutInflater
        )
        binding!!.layoutkycincomplete.setOnClickListener(this)
        binding!!.btnregister.setOnClickListener(this)
        binding!!.btnaddlocationidentity.setOnClickListener(this)
        binding!!.btnaddsetup.setOnClickListener(this)
        binding!!.btnaddagreement.setOnClickListener(this)
        binding!!.btnaddlicense.setOnClickListener(this)
        binding!!.txtviewcontactdetails.setOnClickListener(this)
        binding!!.scrollfostatus.visibility = View.VISIBLE
        binding!!.layoutinfostatus.visibility = View.GONE
        return binding!!.root
    }

    override fun onClick(view: View) {
        if (view === binding!!.layoutkycincomplete) {
            binding!!.layoutkycincomplete.visibility = View.GONE
            binding!!.layoutkyccomplete.visibility = View.VISIBLE
            binding!!.laoutregisterincomplete.visibility = View.VISIBLE
            binding!!.imgkycstatus.setImageResource(R.drawable.ic_done)
        }
        if (view === binding!!.btnregister) {
//            Intent i = new Intent(getActivity(), AddPaymentOrderDetailActivity.class);
//            i.putExtra("foco","fo");
//            startActivityForResult(i,506);
            showfinancial_elibility()
        }
        if (view === binding!!.btnaddlocationidentity) {
            binding!!.layoutlocationidentityincomplete.visibility = View.GONE
            binding!!.layoutlocationidentitycomplete.visibility = View.VISIBLE
            binding!!.layoutagreementincomplete.visibility = View.VISIBLE
            binding!!.imglocationidentity.setImageResource(R.drawable.ic_done)
            //            Intent i = new Intent(getActivity(), LocateOfficeAddress.class);
//            i.putExtra("temp","fo");
//            startActivity(i);
        }
        if (view === binding!!.btnaddagreement) {
            binding!!.layoutagreementincomplete.visibility = View.GONE
            binding!!.layoutagreementcomplete.visibility = View.VISIBLE
            binding!!.layoutsetupincomplete.visibility = View.VISIBLE
            binding!!.imgagreementstatus.setImageResource(R.drawable.ic_done)
        }
        if (view === binding!!.btnaddsetup) {
            binding!!.layoutsetupincomplete.visibility = View.GONE
            binding!!.layoutsetupcomplete.visibility = View.VISIBLE
            binding!!.layoutlicenseincomplete.visibility = View.VISIBLE
            binding!!.imgsetupstatus.setImageResource(R.drawable.ic_done)
            //            Intent i = new Intent(getActivity(), SetupFinancialActivity.class);
//            startActivity(i);
        }
        if (view === binding!!.btnaddlicense) {
            binding!!.layoutlicenseincomplete.visibility = View.GONE
            binding!!.layoutlicensecomplete.visibility = View.VISIBLE
            binding!!.imglicensestatus.setImageResource(R.drawable.ic_done)
            binding!!.txtinfomsg.visibility = View.VISIBLE
            binding!!.txtviewcontactdetails.visibility = View.VISIBLE
        }
        if (view === binding!!.txtviewcontactdetails) {
            requireActivity().finish()
            val i = Intent(activity, SignupActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }

    fun showfinancial_elibility() {
        binding!!.laoutregisterincomplete.visibility = View.GONE
        binding!!.laoutregistercomplete.visibility = View.VISIBLE
        binding!!.layoutlocationidentityincomplete.visibility = View.VISIBLE
        binding!!.imgregisterstatus.setImageResource(R.drawable.ic_done)
    }
}