package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*
import org.w3c.dom.Text

class IndividualCommentDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_comment_display)

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





        individual_profName.text = profName.toString()
        individual_CRN.text = courseNumber.toString()
        individual_semester.text = semester.toString()
        individual_rating.text = courseRating.toString()
        individual_difficulty.text = difficulty.toString()
        individual_grade.text = grade.toString()
        individual_comment.text = comments.toString()
        individual_email.text = email.toString()
        if(profilePicURL.toString().isNotEmpty()){
            Glide.with(this).load(profilePicURL).into(individual_profilePic)
        }






    }
}