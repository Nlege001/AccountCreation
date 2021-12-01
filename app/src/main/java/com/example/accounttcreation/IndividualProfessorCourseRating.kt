package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_individual_professor_course_rating.*
import java.util.*
import kotlin.collections.ArrayList

class IndividualProfessorCourseRating : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<IndividualCourseDataClass>
    private lateinit var tempArrayList: ArrayList<IndividualCourseDataClass>
    private lateinit var myAdapter: IndividualCommentAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var bundle: Bundle
    private lateinit var proffNameProvided: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_professor_course_rating)



        bundle = intent.extras!!

        proffNameProvided = bundle.get("provide_name").toString()
        display_search_proff_name.text = proffNameProvided


        recyclerView = findViewById(R.id.individual_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userArrayList = arrayListOf()
        tempArrayList = arrayListOf()

        myAdapter = IndividualCommentAdapter(tempArrayList)
        recyclerView.adapter = myAdapter
        EventChangeListener()

        myAdapter.setOnItemClickListener(object : IndividualCommentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@IndividualProfessorCourseRating, IndividualCommentDisplay::class.java)
                intent.putExtra("profName", tempArrayList[position].proffessor)
                intent.putExtra("courseNumber",tempArrayList[position].courseNumber)
                intent.putExtra("semester", tempArrayList[position].semester)
                intent.putExtra("difficulty", tempArrayList[position].difficlty)
                intent.putExtra("courseRating", tempArrayList[position].courseRating)
                intent.putExtra("grade", tempArrayList[position].grade)
                intent.putExtra("comments", tempArrayList[position].comments)
                intent.putExtra("email", tempArrayList[position].email)
                intent.putExtra("profilePicURL", tempArrayList[position].downloadURLPATH)
                startActivity(intent)
            }
        })

    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Input").orderBy("proffessor", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Toast.makeText(this@IndividualProfessorCourseRating, "Data wasn't fetched", Toast.LENGTH_SHORT).show()
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED)
                            userArrayList.add(dc.document.toObject(IndividualCourseDataClass::class.java))
                    }
                    userArrayList.forEach {
                        if (it.proffessor?.lowercase(Locale.getDefault())?.contains(proffNameProvided.lowercase()) == true) {
                            tempArrayList.add(it)
                        }else if(it.proffessor?.lowercase(Locale.getDefault())?.contains(proffNameProvided.lowercase()) == false){
                            Toast.makeText(this@IndividualProfessorCourseRating, "No assessments present at this time", Toast.LENGTH_SHORT).show()
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
            })


    }
}




