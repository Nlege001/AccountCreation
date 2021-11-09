package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.image_view
import kotlinx.android.synthetic.main.list_item_display.*
import java.util.*
import kotlin.collections.ArrayList

class DisplayUserInput : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList : ArrayList<DisplayDataClass>
    private lateinit var tempArrayList: ArrayList<DisplayDataClass>
    private lateinit var myAdapter : DisplayAdapterClass
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_input)





        recyclerView = findViewById(R.id.recyclerView_display)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        tempArrayList = arrayListOf()



        myAdapter = DisplayAdapterClass(tempArrayList)

        recyclerView.adapter = myAdapter

        EvenChangeListener()
        


        myAdapter.setOnItemClickListener(object : DisplayAdapterClass.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@DisplayUserInput, "you clicked on item $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@DisplayUserInput, DisplayIndividualUserInput::class.java )
                intent.putExtra("profName", tempArrayList[position].proffessor)
                intent.putExtra("courseNumber",tempArrayList[position].courseNumber)
                intent.putExtra("semester", tempArrayList[position].semester)
                intent.putExtra("difficulty", tempArrayList[position].difficlty)
                intent.putExtra("courseRating", tempArrayList[position].courseRating)
                intent.putExtra("grade", tempArrayList[position].grade)
                intent.putExtra("comments", tempArrayList[position].comments)
                intent.putExtra("email", tempArrayList[position].email)
                startActivity(intent)

            }

        })








    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_recycler_view, menu)
        val item = menu?.findItem(R.id.search_recycler)
        val searchViewRecycler = item?.actionView as SearchView
        searchViewRecycler.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){

                    userArrayList.forEach{
                        if(it.proffessor?.lowercase(Locale.getDefault())?.contains(searchText) == true){
                            tempArrayList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()


                }else{
                    tempArrayList.clear()
                    tempArrayList.addAll(userArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()

                }
                return false
            }

        })


        return super.onCreateOptionsMenu(menu)
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
                        tempArrayList.addAll(userArrayList)
                        myAdapter.notifyDataSetChanged()


                    }

                } )

    }







}