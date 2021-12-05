package com.example.accounttcreation

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_individual_comment_display.*

class DisplayIndividualUserInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_individual_user_input)

        listenToLikesAndDislikes()

        val inputProvider_email : TextView = findViewById(R.id.inputProvider_email)
        val inputProfName : TextView = findViewById(R.id.input_profName)
        val inputCRN : TextView = findViewById(R.id.input_CRN)
        val inputSemester : TextView = findViewById(R.id.input_semester)
        val inputCourseRating : TextView = findViewById(R.id.input_course_rating)
        val inputCourseDiffuculty : TextView = findViewById(R.id.input_difficulty)
        val inputCourseGrade : TextView = findViewById(R.id.input_grade)
        val inputComments : TextView = findViewById(R.id.input_comments)
        val inputProfilePic : ImageView = findViewById(R.id.show_inputProvider_picture)


        val bundle : Bundle ?= intent.extras

        val profName = bundle?.get("profName")
        val courseNumber = bundle?.get("courseNumber")
        val semester = bundle?.get("semester")
        val difficulty = bundle?.get("difficulty")
        val courseRating = bundle?.get("courseRating")
        val grade = bundle?.get("grade")
        val comments = bundle?.get("comments")
        val email = bundle?.get("email")
        val profilePicURL = bundle?.get("profilePic")
        val likeCount  = bundle?.get("likeCount")
        val dislikeCount = bundle?.get("dislikeCount")
        val docId = bundle?.get("docId")

        var likeCountButton = likeCount as Int
        var dislikeCountButton = dislikeCount as Int
        val documentId = docId as String



        inputProvider_email.text = email.toString()
        inputProfName.text = profName.toString()
        inputCRN.text = courseNumber.toString()
        inputSemester.text = semester.toString()
        inputCourseRating.text = courseRating.toString()
        inputCourseDiffuculty.text = difficulty.toString()
        inputCourseGrade.text = grade.toString()
        inputComments.text = comments.toString()
        like_count.text = likeCountButton.toString()
        dislike_count.text = dislikeCountButton.toString()
        if(profilePicURL.toString().isNotEmpty()){
            Glide.with(this).load(profilePicURL).into(inputProfilePic)
        }

        var clickLikeAmount = 0
        var clickDisLikeAmount = 0
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Input").get()

        like_button.setOnClickListener{
            clickLikeAmount += 1
            if (clickLikeAmount % 2 != 0) {
                like_button.setImageResource(R.drawable.ic_baseline_thumb_up_25)
                dislike_button.isClickable = false
                likeCountButton += 1
                query.addOnSuccessListener {
                    for(document in it){
                        db.collection("Input").document(documentId).update("likeCount", likeCountButton)
                    }
                }
                like_count.text = likeCountButton.toString()

            }
            else if (clickLikeAmount % 2 == 0){
                like_button.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                dislike_button.isClickable = true
                likeCountButton -= 1
                query.addOnSuccessListener {
                    for(document in it){
                        db.collection("Input").document(documentId).update("likeCount", likeCountButton)
                    }
                }
                like_count.text = likeCountButton.toString()
            }
        }


        dislike_button.setOnClickListener {
            clickDisLikeAmount += 1
            if (clickDisLikeAmount % 2 != 0) {
                dislike_button.setImageResource(R.drawable.ic_baseline_thumb_down_25)
                like_button.isClickable = false
                dislikeCountButton += 1
                query.addOnSuccessListener {
                    for(document in it){
                        db.collection("Input").document(documentId).update("dislikeCount", dislikeCountButton)
                    }
                }
                dislike_count.text = dislikeCountButton.toString()

            }
            else if (clickDisLikeAmount % 2 == 0){
                dislike_button.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                like_button.isClickable = true
                dislikeCountButton -= 1
                query.addOnSuccessListener {
                    for(document in it){
                        db.collection("Input").document(documentId).update("dislikeCount", dislikeCountButton)
                    }
                }
                dislike_count.text = dislikeCountButton.toString()
            }
        }






    }

    private fun listenToLikesAndDislikes(){
        FirebaseFirestore.getInstance().collection("Input").addSnapshotListener { snapshot, error ->
            if(snapshot != null){
                val Inputs = ArrayList<DisplayDataClass>()
                val documents = snapshot.documents
                documents.forEach{
                    val individualInput = it.toObject(DisplayDataClass::class.java)
                    if(individualInput != null){
                        FirebaseFirestore.getInstance().collection("Input").document(it.id).update("docId", it.id)
                    }
                }
            }
        }
    }
}
