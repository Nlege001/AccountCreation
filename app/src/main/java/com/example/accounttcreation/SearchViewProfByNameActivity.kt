package com.example.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_search_view_prof_by_name.*

class SearchViewProfByNameActivity : AppCompatActivity() {

    lateinit var searchText : EditText
    lateinit var recyclerView : RecyclerView
    lateinit var profName : TextView
    lateinit var department : TextView
    lateinit var firebaseRecyclerAdapter : FirebaseRecyclerAdapter<SearchViewByNameDataClass, UserViewHolder>

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view_prof_by_name)

        backbutton_searchview.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        searchText = findViewById(R.id.ProfName_SearchView)
        recyclerView = findViewById(R.id.ListView)



        databaseReference = FirebaseDatabase.getInstance().getReference("Academic Advising")
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)



        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputSearchText = searchText.text.toString().trim()
                loadFirebaseData(inputSearchText)


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


    }


    private fun loadFirebaseData(inputSearchText : String) {

        if (inputSearchText.isEmpty()) {
            firebaseRecyclerAdapter.cleanup()
            recyclerView.adapter = firebaseRecyclerAdapter


        } else {

            val firebaseSearchQuery = databaseReference.orderByChild("Name").startAt(inputSearchText).endAt(inputSearchText + "\uf8ff")

            firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<SearchViewByNameDataClass, UserViewHolder>(
                    SearchViewByNameDataClass::class.java,
                    R.layout.layout_search_list,
                    UserViewHolder::class.java,
                    //databaseReference.orderByChild("Name").startAt(inputSearchText).endAt(inputSearchText + "\uf8ff")
                    firebaseSearchQuery

                ) {
                    override fun populateViewHolder(viewHolder: UserViewHolder?, model: SearchViewByNameDataClass?, position: Int) {

                        viewHolder?.view?.findViewById<TextView>(R.id.listView_profName)?.text = model?.Name
                        viewHolder?.view?.findViewById<TextView>(R.id.listView_department)?.text = model?.Faculty


                    }
                }
            recyclerView.adapter = firebaseRecyclerAdapter


        }
    }


    class UserViewHolder(var view : View) : RecyclerView.ViewHolder(view){}
}