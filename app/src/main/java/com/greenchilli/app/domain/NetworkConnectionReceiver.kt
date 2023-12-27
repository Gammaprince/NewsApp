package com.greenchilli.app.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.greenchilli.app.ui.activity.MainActivity
import kotlinx.coroutines.*


class NetworkConnectionReceiver : BroadcastReceiver() {
    var isShowed = false
    var internetDialog : AlertDialog? = null
    var noInternetDialog: AlertDialog? = null
    private fun showNoInternetDialog(context: Context) {
        internetDialog?.dismiss()
        val builder = AlertDialog.Builder(context)
        builder.setTitle("No internet connection \uD83D\uDE1F")
        builder.setMessage("Please connect to the internet to use this app.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        noInternetDialog = builder.create()
        noInternetDialog?.show()
    }

    private fun connected(context: Context) {
        noInternetDialog?.dismiss()
        val builder = AlertDialog.Builder(context)
        builder.setTitle("We're now connected to Internet")
        builder.setMessage("Feel free to search anything \uD83D\uDE0B")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        internetDialog = builder.create()
        internetDialog?.show()

    }
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                if(!isShowed) isShowed = true
                else {
                    connected(context)
                }
            } else {
                if(!isShowed) {
                    isShowed = true
                    showNoInternetDialog(context)
                }
                else{
                    showNoInternetDialog(context)
                }
            }
        }
    }
}