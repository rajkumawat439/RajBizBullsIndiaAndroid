package com.dharmesh.bizzbullindiaproject.act

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dharmesh.bizzbullindiaproject.databinding.ActivitySplashBinding
import com.dharmesh.bizzbullindiaproject.vm.SplashViewModel


class SplashActivity : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000L)

    }
}