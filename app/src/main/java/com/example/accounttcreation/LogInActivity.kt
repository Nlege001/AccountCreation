package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        val button_register = findViewById<TextView>(R.id.RegisterButton2)
        var button = findViewById<Button>(R.id.button)
        var email = findViewById<EditText>(R.id.LogInUserName)
        var password = findViewById<EditText>(R.id.LogInPassword)
        var resetBtn = findViewById<TextView>(R.id.ResetPasswordButton)


        resetBtn.setOnClickListener {
            startActivity(Intent(this@LogInActivity, ResetPassword::class.java))
        }




        button_register.setOnClickListener {
            startActivity(Intent(this@LogInActivity, RegisterActivity::class.java))
        }

        button.setOnClickListener {
            when{
                TextUtils.isEmpty(email.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@LogInActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(password.text.toString().trim{it <=' '}) ->{
                    Toast.makeText(
                        this@LogInActivity,
                        "Please provide password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val ProvidedEmail : String = email.text.toString().trim{it <= ' '}
                    val ProvidedPassword : String = password.text.toString().trim{it <= ' '}


                    // we create an instance and register a user
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(ProvidedEmail, ProvidedPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser : FirebaseUser = task.result!!.user!!
                                var userState = FirebaseAuth.getInstance().currentUser!!.isEmailVerified

                                if(userState){

                                Toast.makeText(
                                    this@LogInActivity,
                                    "You have Logged In Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()


                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", ProvidedEmail)
                                startActivity(intent)
                                finish()
                            } else if(!userState){
                                // registration wasn't successful
                                Toast.makeText(
                                    this@LogInActivity,
                                    "Please verify your email",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }else{
                                // registration wasn't successful
                                Toast.makeText(
                                    this@LogInActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()



                }


            }




        }
    }
}}}