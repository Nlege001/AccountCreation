package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_individual_comment_display.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.w3c.dom.Text

class IndividualCommentDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_comment_display)

        listenToLikesAndDislikes()

        val individual_profName = findViewById<TextView>(R.id.individual_input_profName)
        val individual_CRN = findViewById<TextView>(R.id.individual_input_CRN)
        val individual_semester = findViewById<TextView>(R.id.individual_input_semester)
        val individual_rating = findViewById<TextView>(R.id.individual_input_course_rating)
        val individual_difficulty = findViewById<TextView>(R.id.individual_input_difficulty)
        val individual_grade = findViewById<TextView>(R.id.individual_input_grade)
        val individual_comment = findViewById<TextView>(R.id.individual_input_comments)
        val individual_email = findViewById<TextView>(R.id.individual_inputProvider_email)
        val individual_profilePic = findViewById<ImageView>(R.id.individual_show_inputProvider_picture)


        val bundle: Bundle ?= intent.extras

        val profName = bundle?.get("profName")
        val courseNumber = bundle?.get("courseNumber")
        val semester = bundle?.get("semester")
        val difficulty = bundle?.get("difficulty")
        val courseRating = bundle?.get("courseRating")
        val grade = bundle?.get("grade")
        val comments = bundle?.get("comments")
        val email = bundle?.get("email")
        val profilePicURL = bundle?.get("profilePicURL")
        val likeCount  = bundle?.get("likeCount")
        val dislikeCount = bundle?.get("dislikeCount")
        val docId = bundle?.get("docId")
        val courseName = bundle?.get("courseName")

        var likeCountButton = likeCount as Int
        var dislikeCountButton = dislikeCount as Int
        val documentId = docId as String





        individual_profName.text = profName.toString()
        individual_CRN.text = courseNumber.toString()
        individual_semester.text = semester.toString()
        individual_rating.text = courseRating.toString()
        individual_difficulty.text = difficulty.toString()
        individual_grade.text = grade.toString()
        individual_comment.text = comments.toString()
        individual_email.text = email.toString()
        like_count.text = likeCountButton.toString()
        dislike_count.text = dislikeCountButton.toString()
        input_courseName_individual.text = courseName.toString()
        if(profilePicURL.toString().isNotEmpty()){
            Glide.with(this).load(profilePicURL).into(individual_profilePic)
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