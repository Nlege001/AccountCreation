package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayIndividualUserInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_individual_user_input)

        val inputProvider_email : TextView = findViewById(R.id.inputProvider_email)
        val inputProfName : TextView = findViewById(R.id.input_profName)
        val inputCRN : TextView = findViewById(R.id.input_CRN)
        val inputSemester : TextView = findViewById(R.id.input_semester)
        val inputCourseRating : TextView = findViewById(R.id.input_course_rating)
        val inputCourseDiffuculty : TextView = findViewById(R.id.input_difficulty)
        val inputCourseGrade : TextView = findViewById(R.id.input_grade)
        val inputComments : TextView = findViewById(R.id.input_comments)


        val bundle : Bundle ?= intent.extras

        val profName = bundle?.get("profName")
        val courseNumber = bundle?.get("courseNumber")
        val semester = bundle?.get("semester")
        val difficulty = bundle?.get("difficulty")
        val courseRating = bundle?.get("courseRating")
        val grade = bundle?.get("grade")
        val comments = bundle?.get("comments")
        val email = bundle?.get("email")


        inputProvider_email.text = email.toString()
        inputProfName.text = profName.toString()
        inputCRN.text = courseNumber.toString()
        inputSemester.text = semester.toString()
        inputCourseRating.text = courseRating.toString()
        inputCourseDiffuculty.text = difficulty.toString()
        inputCourseGrade.text = grade.toString()
        inputComments.text = comments.toString()






    }
}