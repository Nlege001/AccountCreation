package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password);

        var button = findViewById<Button>(R.id.SignUpButton)
        var email = findViewById<EditText>(R.id.RegisterEmail)
        var password = findViewById<EditText>(R.id.RegisterPassword)
        val ResetPasswordBTN = findViewById<Button>(R.id.forgotPassword)
    }

    forgotPasswordBTN.setOnClickListener {
        when{
            TextUtils.isEmpty(email.text.toString().trim{it <= ' '}) -> {
                Toast.makeText(
                    this@ForgotPassword,
                    "Please enter email",
                    Toast.LENGTH_SHORT
                ).show()
            }
            TextUtils.isEmpty(password.text.toString().trim{it <=' '}) ->{
                Toast.makeText(
                    this@ForgotPassword,
                    "Please provide password",
                    Toast.LENGTH_SHORT
                ).show()
            }
}