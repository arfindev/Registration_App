package com.example.registration_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var firebase : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebase = FirebaseAuth.getInstance()

    buttonregister.setOnClickListener {
        loginUser()
    }

    }

    private fun loginUser(){
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.isBlank()|| password.isBlank()){
            return
        }
        firebase.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,signupUser::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Login Error",Toast.LENGTH_LONG).show()
                }
            }

    }

}