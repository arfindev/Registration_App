package com.example.registration_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_signup_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val   REQUEST_CODE_SIGN_IN = 0

class googlesignup : AppCompatActivity() {
    lateinit var firebase : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebase = FirebaseAuth.getInstance()

        btnGoogle.setOnClickListener{
            val options =GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient))
                .requestEmail()
                .build()
            val getClient = GoogleSignIn.getClient(this,options)
            getClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    private fun googleFirebaseSignIn(account: GoogleSignInAccount){
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firebase.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@googlesignup,"Successfully Registered",Toast.LENGTH_LONG).also{}
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@googlesignup,e.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intetnData = GoogleSignIn.getSignedInAccountFromIntent(data).result
        intetnData?.let {
            googleFirebaseSignIn(it)
        }
    }



}