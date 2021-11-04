package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.image_view
import kotlinx.android.synthetic.main.list_item_display.*

class DisplayUserInput : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList : ArrayList<DisplayDataClass>
    private lateinit var myAdapter : DisplayAdapterClass
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_input)





        recyclerView = findViewById(R.id.recyclerView_display)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()


        myAdapter = DisplayAdapterClass(userArrayList)

        recyclerView.adapter = myAdapter

        EvenChangeListener()

        myAdapter.setOnItemClickListener(object : DisplayAdapterClass.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@DisplayUserInput, "you clicked on item $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@DisplayUserInput, DisplayIndividualUserInput::class.java )
                intent.putExtra("profName", userArrayList[position].proffessor)
                intent.putExtra("courseNumber", userArrayList[position].courseNumber)
                intent.putExtra("semester", userArrayList[position].semester)
                intent.putExtra("difficulty", userArrayList[position].difficlty)
                intent.putExtra("courseRating", userArrayList[position].courseRating)
                intent.putExtra("grade", userArrayList[position].grade)
                intent.putExtra("comments", userArrayList[position].comments)
                intent.putExtra("email", userArrayList[position].email)
                startActivity(intent)

            }

        })








    }

    private fun EvenChangeListener(){

        db = FirebaseFirestore.getInstance()
        db.collection("Input").orderBy("proffessor", Query.Direction.ASCENDING).
                addSnapshotListener(object : EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null){
                            Toast.makeText(this@DisplayUserInput, "Data wasn't fetched", Toast.LENGTH_SHORT).show()
                        }

                        for (dc : DocumentChange in value?.documentChanges!!){
                            if(dc.type == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.document.toObject(DisplayDataClass::class.java))
                            }
                        }

                        myAdapter.notifyDataSetChanged()


                    }

                } )

    }







}