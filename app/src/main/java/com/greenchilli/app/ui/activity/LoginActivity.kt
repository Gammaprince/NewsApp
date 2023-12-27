package com.greenchilli.app.ui.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greenchilli.app.R
import com.greenchilli.app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val webClientId = "110599695437-biq9tbcuf79np5ucknrhrqncja41kj00.apps.googleusercontent.com"
    private val client2 = "110599695437-mv2c6pn2h3acd7rksvt4j46pqf42m6hk.apps.googleusercontent.com"
    private lateinit var googleSignInClient: GoogleSignInClient
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        supportActionBar?.hide()
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
            client2
        ).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.googleSignIn.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
//        binding.haveAccount.setOnClickListener {
//            startActivity(Intent(this, SignupActivity::class.java))
//            finish()
//        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, ChooseLocationActivity::class.java))
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Google Sign-In failed"+result.resultCode, Toast.LENGTH_SHORT).show()
                }
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this, "Sign-in canceled  "+result.resultCode, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Handle other result codes if needed
                Toast.makeText(this, "Sign-in failed with result code ${result.resultCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }


}