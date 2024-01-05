package com.greenchilli.app.ui.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.*
import com.facebook.login.Login
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greenchilli.app.BuildConfig
import com.greenchilli.app.R
import com.greenchilli.app.databinding.ActivityLoginBinding
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.model.UserInfoResponse
import org.json.JSONObject
import java.security.MessageDigest
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager : CallbackManager
    private val webClientId = "110599695437-biq9tbcuf79np5ucknrhrqncja41kj00.apps.googleusercontent.com"
    private val client2 = "110599695437-mv2c6pn2h3acd7rksvt4j46pqf42m6hk.apps.googleusercontent.com"
    private lateinit var googleSignInClient: GoogleSignInClient
    val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        updateUI(null)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

// This code to generate key Hash to upload in facebook keyhash
//        try {
//            val info = getPackageManager().getPackageInfo(
//                "com.greenchilli.app",
//                PackageManager.GET_SIGNATURES);
//            for (signature in info.signatures) run {
//                val md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("FaceKeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        }
//        catch (e : Exception){
//            Log.d("FaceKeyHash","Got error ${e.message}")
//        }



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
        binding.haveAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, ChooseLocationActivity::class.java))
        }
        FacebookSdk.sdkInitialize(applicationContext)
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
         callbackManager = CallbackManager.Factory.create()



    }
    fun updateUI(user : FirebaseUser?){
        if(user != null){
            val info = FirebaseAuth.getInstance().currentUser
            message("Signed in with google")
            startActivity(Intent(this,MainActivity::class.java))
            finish();
        }
    }
    private fun message(s : String){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    private val ref = FirebaseHelper.getFirebaseRef("UserInfo/${FirebaseAuth.getInstance().uid.toString()}")
    fun setInfo(name : String , address : String , phone : String , email : String) {
        ref.setValue(UserInfoResponse(name,address,phone,email));
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
                            val data = FirebaseAuth.getInstance().currentUser
                            setInfo(data?.displayName.toString(),"",data?.phoneNumber.toString(),data?.email.toString())
                            updateUI(FirebaseAuth.getInstance().currentUser)
//                            Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()
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