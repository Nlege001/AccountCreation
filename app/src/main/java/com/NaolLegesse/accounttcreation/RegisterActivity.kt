package com.NaolLegesse.accounttcreation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var button = findViewById<Button>(R.id.SignUpButton)
        var email = findViewById<EditText>(R.id.RegisterEmail)
        var password = findViewById<EditText>(R.id.RegisterPassword)
        val button2 = findViewById<TextView>(R.id.login2)

        button2.setOnClickListener {
            onBackPressed()
        }
        button.setOnClickListener {
            when{
                TextUtils.isEmpty(email.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(password.text.toString().trim{it <=' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please provide password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val ProvidedEmail : String = email.text.toString().trim{it <= ' '}
                    val ProvidedPassword : String = password.text.toString().trim{it <= ' '}


                    // we create an instance and register a user
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(ProvidedEmail, ProvidedPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser : FirebaseUser = task.result!!.user!!
                                val user = FirebaseAuth.getInstance().currentUser

                                user?.sendEmailVerification()
                                    ?.addOnCompleteListener { task->
                                        if (task.isSuccessful){
                                            Toast.makeText(
                                                this@RegisterActivity,
                                                "Verification Email sent, please verify email",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            // default profile picture
                                            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.profile_icon)
                                            val baos = ByteArrayOutputStream()
                                            val storageRef = FirebaseStorage.getInstance().reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                                            val image = baos.toByteArray()
                                            storageRef.putBytes(image)
                                        }else{
                                            Toast.makeText(
                                                this@RegisterActivity,
                                                task.exception!!.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                val intent = Intent(this@RegisterActivity, LogInActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", ProvidedEmail)
                                startActivity(intent)
                                finish()
                            } else{
                                // registration wasn't successful
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

        }

    }

}