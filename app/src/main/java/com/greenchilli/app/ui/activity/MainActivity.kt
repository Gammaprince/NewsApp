package com.greenchilli.app.ui.activity

import EncryptionUtil
import NetworkUtility
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.greenchilli.app.R
import com.greenchilli.app.databinding.ActivityMainBinding
import com.greenchilli.app.domain.NetworkConnectionReceiver
import com.greenchilli.app.ui.fragment.NotificationFragment

class MainActivity : AppCompatActivity() {

    private val networkConnectionReceiver = NetworkConnectionReceiver()

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragmentContainerView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        registerReceiver(networkConnectionReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        bottomNav.setupWithNavController(navController)
        binding.notificationButton.setOnClickListener {
            val notification = NotificationFragment()
            notification.show(supportFragmentManager,"S")
        }
        val secretKey = EncryptionUtil.generateSecretKey()
        val encryptedData = EncryptionUtil.encryptData("Hello my name is Prince",secretKey)
        Toast.makeText(applicationContext,encryptedData.encryptedText+" ok", Toast.LENGTH_LONG).show()
        Log.d("Encryption",encryptedData.encryptedText)
        val decryptedData = EncryptionUtil.decryptData(encryptedData,secretKey)
        Log.d("Encryption",decryptedData.toString())
    }
}