package com.NaolLegesse.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class ProfessorScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_screen)

        val provideCourseAssessBtn = findViewById<Button>(R.id.provide_course_assessment)
        val seeCourseAssessBtn = findViewById<Button>(R.id.see_assessment)





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

        provideCourseAssessBtn.setOnClickListener {
            val intent = Intent(this, UserInput::class.java)
            intent.putExtra("provide_name", profName.toString())
            startActivity(intent)
        }

        seeCourseAssessBtn.setOnClickListener {
            val intent = Intent(this, IndividualProfessorCourseRating::class.java)
            intent.putExtra("provide_name", profName.toString())
            startActivity(intent)
        }





    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.input_actionBar -> {
                startActivity(Intent(this, UserInput::class.java))
                return true
            }
            R.id.input_search ->{
                startActivity(Intent(this, DisplayUserInput::class.java))
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }
}