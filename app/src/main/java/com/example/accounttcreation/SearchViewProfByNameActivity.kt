package com.example.accounttcreation

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search_view_prof_by_name.*
import java.util.*
import kotlin.collections.ArrayList

//https://www.youtube.com/watch?v=2z0HlIY7M9s
class SearchViewProfByNameActivity : AppCompatActivity() {

    lateinit var searchText : EditText
    lateinit var recyclerView : RecyclerView
    lateinit var profName : TextView
    lateinit var department : TextView


    lateinit var firebaseFirestore: CollectionReference
    private var searchList: List<SearchViewByNameDataClass> = ArrayList()
    private val searchListAdapter = SearchListAdapter(searchList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view_prof_by_name)



        searchText = findViewById(R.id.ProfName_SearchView)

        backbutton_searchview.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }



        val departments = resources.getStringArray(R.array.department)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_items_department, departments)
        val autoCompleteDepartmentDropDown = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autoCompleteDepartmentDropDown.setAdapter(arrayAdapter)
        var itemSelected = ""
        autoCompleteDepartmentDropDown.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            itemSelected = parent.getItemAtPosition(position).toString()
            firebaseFirestore = FirebaseFirestore.getInstance().collection(itemSelected)
        }

        ListView.setHasFixedSize(true)
        ListView.layoutManager = LinearLayoutManager(this)

        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchValue : String = searchText.text.toString()
                searchInFireStore(searchValue)


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })







    }

    private fun searchInFireStore(searchValue : String){
        firebaseFirestore.orderBy("Name").startAt(searchValue).endAt("$searchValue\uf8ff").get().addOnCompleteListener {
            if(it.isSuccessful){
                if(it.result!!.isEmpty) {
                    searchList = it.result!!.toObjects(SearchViewByNameDataClass::class.java)
                    searchListAdapter.searchList = searchList
                    searchListAdapter.notifyDataSetChanged()
                    ListView.adapter = searchListAdapter

                }
            }else{
                Log.d(TAG, "Error: ${it.exception!!.message}")
            }
        }
    }



}