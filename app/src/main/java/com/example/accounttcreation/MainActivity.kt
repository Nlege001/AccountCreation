package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email)id")

        val emailText = findViewById<TextView>(R.id.MainEmail)
        val passwordText = findViewById<TextView>(R.id.MainPassword)
        val mainButton = findViewById<Button>(R.id.logoutButton)




        emailText.text = "User ID :: $userId"
        passwordText.text = "Email ID :: $emailId"

        mainButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LogInActivity::class.java))
            finish()
        }



    }

}