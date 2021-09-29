package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class RecycleViewDB : AppCompatActivity() {
    private  lateinit var databaseRef : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArray : ArrayList<DataClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view_db)


        userRecyclerView = findViewById(R.id.RecylcerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArray = arrayListOf<DataClass>()
        getUserData()

    }

    private fun getUserData(){
        databaseRef = FirebaseDatabase.getInstance().getReference("Academic Advising")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(usersnapshot in snapshot.children){

                        val user = usersnapshot.getValue(DataClass::class.java)
                        userArray.add(user!!)
                    }
                    println(userArray)

                    userRecyclerView.adapter = AdapterClass(userArray)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("error")

            }
        })
    }
}