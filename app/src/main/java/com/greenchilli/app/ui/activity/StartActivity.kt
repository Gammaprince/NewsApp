package com.greenchilli.app.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greenchilli.app.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        if(Firebase.auth.currentUser != null){
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }
        else Toast.makeText(this,"this user is null",Toast.LENGTH_SHORT).show()
        binding.next.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }
    }
}