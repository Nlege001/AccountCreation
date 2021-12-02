package com.example.accounttcreation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_input.*

class UserInput : AppCompatActivity() {
    private lateinit var uid : String
    private lateinit var url : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)


        ratingBar.rating = 2.5f
        ratingBar.stepSize = 0.5f
        textViewRating.text = 2.5f.toString()
        ratingBar.setOnRatingBarChangeListener{ratingBar, rating, fromUser ->
            textViewRating.text = rating.toString()

        }



        val bundle : Bundle ?= intent.extras
        val name = bundle?.get("provide_name")
        if(bundle != null){
            ProfessorName.setText(name.toString())
        }else{
            ProfessorName.text.clear()

        }





        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storage = FirebaseStorage.getInstance().reference.child("pics/$uid")
        storage.downloadUrl.addOnSuccessListener { result ->
            url = result.toString()

        }



        savebutton_input.setOnClickListener {
            val proffessor = ProfessorName.text.toString()
            val courseNumber = CourseNumber.text.toString()
            val semester = Semster.text.toString()
            val difficulty = Difficulty.text.toString()
            val courseRating = textViewRating.text.toString()
            val grade = Grade.text.toString()
            val comments = Comments.text.toString()
            val email : String = FirebaseAuth.getInstance().currentUser?.email.toString()
            val downloadURLPath : String = url


            saveFireStore(proffessor, courseNumber, semester, difficulty, courseRating, grade, comments, downloadURLPath, email)

            startActivity(Intent(this, MainActivity::class.java))




        }
    }

    fun saveFireStore(proffessor: String, courseNumber: String, semester: String, difficulty:String, courseRating:String, grade:String, comments:String, downloadURLPATH: String, email:String){
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