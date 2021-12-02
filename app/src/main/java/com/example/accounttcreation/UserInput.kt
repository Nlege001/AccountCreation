package com.example.accounttcreation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_input.*

class UserInput : AppCompatActivity() {
    private lateinit var uid : String
    private lateinit var url : String
    private lateinit var semesterSelected : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)


        ratingBar.rating = 2.5f
        ratingBar.stepSize = 0.5f
        textViewRating.text = 2.5f.toString()
        ratingBar.setOnRatingBarChangeListener{ratingBar, rating, fromUser ->
            textViewRating.text = rating.toString()

        }

        val semesters = listOf<String>("Fall","Spring","Summer","Winter")
        val arrayAdapter = ArrayAdapter(this, R.layout.semester_dropdown_selection, semesters)
        val autoCompletedSemesterDropDown= findViewById<AutoCompleteTextView>(R.id.semesterInputDropDown)
        autoCompletedSemesterDropDown.setAdapter(arrayAdapter)
        var itemSelectedSemester = ""
        autoCompletedSemesterDropDown.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id->
            itemSelectedSemester = parent.getItemAtPosition(position).toString()
            semesterSelected = itemSelectedSemester
            autoCompletedSemesterDropDown.dismissDropDown()
        }

        val year = listOf<String>("2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030")
        val yearArrayAdapter = ArrayAdapter(this,R.layout.year_dropdown_selection, year)
        val autoCompleteYearDropDown = findViewById<AutoCompleteTextView>(R.id.yearInputDropDown)
        autoCompleteYearDropDown.setAdapter(yearArrayAdapter)
        var itemSelectedYear = ""
        autoCompleteYearDropDown.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            itemSelectedYear = parent.getItemAtPosition(position).toString()
            semesterSelected += " $itemSelectedYear"
            autoCompleteYearDropDown.dismissDropDown()
        }

        val difficulty = listOf<String>("Easy", "Normal", "Above Average", "Hard")
        val difficultyArrayAdapter = ArrayAdapter(this, R.layout.difficulty_dropdown, difficulty)
        val autoCompleteDifficulty = findViewById<AutoCompleteTextView>(R.id.difficultyInputDropDown)
        autoCompleteDifficulty.setAdapter(difficultyArrayAdapter)
        var itemSelectedDifficulty = ""
        autoCompleteDifficulty.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            itemSelectedDifficulty = parent.getItemAtPosition(position).toString()
        }

        val grade = listOf<String>("A","A-","B+","B","B-","C+","C","C-","D+","D","D-","F","Incomplete")
        val gradeArrayAdapter = ArrayAdapter(this, R.layout.grade_dropdown, grade)
        val autoCompleteGradeDropDown = findViewById<AutoCompleteTextView>(R.id.gradeInputDropDown)
        autoCompleteGradeDropDown.setAdapter(gradeArrayAdapter)
        var itemSelectedGrade = ""
        autoCompleteGradeDropDown.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            itemSelectedGrade = parent.getItemAtPosition(position).toString()
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
            when {
                TextUtils.isEmpty(ProfessorName.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserInput,
                        "Please Provide Professor Name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(CourseNumber.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserInput,
                        "Please Provide course registration number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                textViewRating.text.toString().isEmpty() ->{
                    Toast.makeText(
                        this@UserInput,
                        "Please rate the course out of 5, other students will find it helpful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                itemSelectedSemester.isEmpty() -> {
                    Toast.makeText(
                        this@UserInput,
                        "Please Provide the semester the course was taken",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                itemSelectedYear.isEmpty() ->{
                    Toast.makeText(
                        this@UserInput,
                        "Please Provide the year the course was taken",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                itemSelectedDifficulty.isEmpty() ->{
                    Toast.makeText(
                        this@UserInput,
                        "Please Provide the difficulty of the course, other students will find it helpful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(Comments.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@UserInput,
                        "Please provide additional comments about the course, other students will find it helpful",
                        Toast.LENGTH_SHORT
                    ).show()
                }



                else -> {


                    val proffessor = ProfessorName.text.toString()
                    val courseNumber = CourseNumber.text.toString()
                    val semester = semesterSelected
                    val difficulty = itemSelectedDifficulty
                    val courseRating = textViewRating.text.toString()
                    val grade = itemSelectedGrade
                    val comments = Comments.text.toString()
                    val email: String = FirebaseAuth.getInstance().currentUser?.email.toString()
                    val downloadURLPath: String = url


                    saveFireStore(proffessor, courseNumber, semester, difficulty, courseRating, grade, comments, downloadURLPath, email)

                    startActivity(Intent(this, MainActivity::class.java))
                }
            }




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