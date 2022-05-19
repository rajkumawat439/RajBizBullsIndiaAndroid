package com.dharmesh.bizzbullindiaproject.ui.customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dharmesh.bizzbullindiaproject.databinding.ActivityProjectinfoBinding

class ProjectInfoActivity : AppCompatActivity(), View.OnClickListener {
    var binding: ActivityProjectinfoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectinfoBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.tvRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view === binding!!.tvRegister) {
            finish()
            val i = Intent(this@ProjectInfoActivity, FranchiseeRegistrationActivity::class.java)
            startActivity(i)
        }
    }
}