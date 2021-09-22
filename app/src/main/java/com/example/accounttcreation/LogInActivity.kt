package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        val button_register = findViewById<Button>(R.id.RegisterButton2)

        button_register.setOnClickListener {
            startActivity(Intent(this@LogInActivity, RegisterActivity::class.java))
        }
    }
}