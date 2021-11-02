package com.example.accounttcreation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.model.AdapterClass
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*


class SearchBar : AppCompatActivity() {
    var ref: DatabaseReference? = null
    var userList: ArrayList<DataClass>? = null
    var recyclerView: RecyclerView? = null
    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bar)
        ref = FirebaseDatabase.getInstance().reference.child("Academic Advising")
        recyclerView = findViewById(R.id.recylcerView)
        searchView = findViewById(R.id.searchView)
    }

    override fun onStart() {
        super.onStart()
        if (ref != null) {
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userList = ArrayList<DataClass>()
                        for (ds in dataSnapshot.children) {
                            ds.getValue(DataClass::class.java)?.let { userList!!.add(it) }
                        }
                        val adapterClass = AdapterClass(userList!!)
                        recyclerView!!.adapter = adapterClass
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@SearchBar, databaseError.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
        if (searchView != null) {
            searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    search(s)
                    return true
                }
            })
        }
    }

    private fun search(str: String) {
        val myList = ArrayList<DataClass>()
        for (`object` in userList!!) {
            if (`object`.Name!!.lowercase(Locale.getDefault()).contains(str.lowercase(Locale.getDefault()))) {
                myList.add(`object`)
            }
        }
        val adapterClass = AdapterClass(myList)
        recyclerView!!.adapter = adapterClass
    }
}
