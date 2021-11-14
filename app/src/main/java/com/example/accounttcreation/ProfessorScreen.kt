package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfessorScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_screen)

        val professorName = findViewById<TextView>(R.id.screen_professor_name)
        val title = findViewById<TextView>(R.id.screen_title_placeHolder)
        val faculty = findViewById<TextView>(R.id.screen_department_placeHolder)
        val email = findViewById<TextView>(R.id.screen_email_placeHolder)


        val bundle : Bundle ?= intent.extras

        val profName = bundle?.get("profName")
        val titleName = bundle?.get("title")
        val facultyName = bundle?.get("faculty")
        val emailText = bundle?.get("email")

        professorName.text = profName.toString()
        title.text = titleName.toString()
        faculty.text = facultyName.toString()
        email.text = emailText.toString()


    }
}