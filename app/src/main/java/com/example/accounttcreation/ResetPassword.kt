package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ResetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        var sendLinkBtn = findViewById<Button>(R.id.ResetLinkButton)
        val resetEmail = findViewById<EditText>(R.id.ResetEmail)

        sendLinkBtn.setOnClickListener {
            val email : String = resetEmail.text.toString().trim{it <= ' '}

            if(email.isEmpty()){
                Toast.makeText(
                    this@ResetPassword,
                    "Please enter email address",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this@ResetPassword,
                                "Email sent successfully to reset your password",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        }else{
                            Toast.makeText(
                                this@ResetPassword,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
            }


        }


    }
}