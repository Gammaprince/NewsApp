package com.greenchilli.app.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greenchilli.app.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(binding.root)
        binding.haveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
            finish()
        }
    }
}