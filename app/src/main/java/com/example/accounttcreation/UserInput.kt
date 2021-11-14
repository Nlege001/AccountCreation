package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_input.*

class UserInput : AppCompatActivity() {
    private lateinit var uid : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)

        val bundle : Bundle ?= intent.extras
        val name = bundle?.get("provide_name")
        if(bundle != null){
            ProfessorName.setText(name.toString())
        }else{
            ProfessorName.text.clear()

        }





        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        savebutton_input.setOnClickListener {
            val proffessor = ProfessorName.text.toString()
            val courseNumber = CourseNumber.text.toString()
            val semester = Semster.text.toString()
            val difficulty = Difficulty.text.toString()
            val courseRating = CourseRating.text.toString()
            val grade = Grade.text.toString()
            val comments = Comments.text.toString()
            val email : String = FirebaseAuth.getInstance().currentUser?.email.toString()
            val downloadURLPath : String = FirebaseStorage.getInstance().reference.child("pics/$uid").path


            saveFireStore(proffessor, courseNumber, semester, difficulty, courseRating, grade, comments, downloadURLPath, email)

            startActivity(Intent(this, MainActivity::class.java))




        }
    }

    fun saveFireStore(proffessor: String, courseNumber : String, semester: String, difficulty:String, courseRating:String, grade:String, comments:String, downloadURLPATH:String , email:String){
        val db = FirebaseFirestore.getInstance()
        val Input : MutableMap<String,Any> = HashMap()
        Input["proffessor"] = proffessor
        Input["courseNumber"] = courseNumber
        Input["semester"] = semester
        Input["difficlty"] = difficulty
        Input["courseRating"] = courseRating
        Input["grade"] = grade
        Input["comments"] =comments
        Input["downloadURLPATH"] = downloadURLPATH
        Input["email"] = email

        db.collection("Input")
            .add(Input)
            .addOnSuccessListener {
                Toast.makeText(this, "data was saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "data wasn't saved", Toast.LENGTH_SHORT).show()
            }


    }

}