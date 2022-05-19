package com.dharmesh.bizzbullindiaproject.act

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dharmesh.bizzbullindiaproject.DashboardActivity
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.common.AppConstants
import com.dharmesh.bizzbullindiaproject.common.Preferences
import com.dharmesh.bizzbullindiaproject.databinding.SigninBinding
import com.dharmesh.bizzbullindiaproject.fact.LoginViewModelFactory
import com.dharmesh.bizzbullindiaproject.vm.LoginViewModel
import com.google.android.material.textfield.TextInputEditText


class LoginActivity : AppCompatActivity() {
    private lateinit var dialog: Dialog
    private lateinit var cpwd: TextInputEditText
    private lateinit var pwd: TextInputEditText
    private lateinit var username: AppCompatEditText
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: SigninBinding
    val TAG = "LoginActivity"


    // [START resend_verification]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = binding.edtuserid
        pwd = binding.edtpassword
        SigninTextSpnnable()

        val btnsignin = binding.btnlogin
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
        btnsignin.setOnClickListener {
            if (loginViewModel.validate(
                    username.text.toString(),
                    pwd.text.toString()
                )
            ) {
                signin()
            }

        }
        binding.txtSignin.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finishAffinity()
        }
        loginViewModel.loginresult.observe(this) {
            if (it.msg.equals("Login successful", true)) {
                AppConstants(this).setPrefValue(Preferences.USER_ID, username.text.toString())
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            }
        }
        loginViewModel.showToast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

    }

    fun SigninTextSpnnable() {
        val spannable = SpannableString("Please Sign-up here.")
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
            7, 14,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtSignin.text = spannable
    }

    private fun signin() {
        loginViewModel.signin(
            username.text.toString(),
            pwd.text.toString()
        )
    }
}