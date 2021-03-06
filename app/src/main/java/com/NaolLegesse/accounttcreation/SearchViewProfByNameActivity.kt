package com.NaolLegesse.accounttcreation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import kotlinx.android.synthetic.main.activity_search_view_prof_by_name.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList
import android.widget.EditText




//https://www.youtube.com/watch?v=2z0HlIY7M9s
class SearchViewProfByNameActivity : AppCompatActivity() {

    lateinit var searchText : EditText
    lateinit var recyclerView : RecyclerView
    lateinit var profName : TextView
    lateinit var department : TextView


    lateinit var firebaseFirestore: CollectionReference
    private var searchList: List<SearchViewByNameDataClass> = ArrayList()
    //private val searchListAdapter = SearchListAdapter(searchList)
    lateinit var fileName: String
    private val facultyList : ArrayList<SearchViewByNameDataClass> = ArrayList()
    private val tempFacultyList : ArrayList<SearchViewByNameDataClass> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view_prof_by_name)

        Toast.makeText(
            this@SearchViewProfByNameActivity,
            "Start by selecting a department",
            Toast.LENGTH_LONG
        ).show()



        searchText = findViewById(R.id.ProfName_SearchView)

        backbutton_searchview.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        seeAssessment.setOnClickListener {
            startActivity(Intent(this, DisplayUserInput::class.java))
        }

        provideAssessment.setOnClickListener {
            startActivity(Intent(this, UserInput::class.java))
        }


        val departments = listOf<String>("Academic Advising", "Academic Affairs", "Accounting", "Admissions", "Adventure and Expeditionary Studies", "Africana Studies", "Anthropology",
                "Art", "Art Museum", "Athletics and Recreation" , "Biological Sciences", "Canadian Studies" , "Career Development Center" , "Center for Neurobehavioral Health","Chemistry and Biochemistry" , "Communication Sciences and Disorders",
                "Communication Studies" , "Computer Information Systems" , "Computer Science" , "Counseling and Human Services" , "Criminal Justice" , "ESL Program", "Earth and Environmental Science" , "Economics and Finance" , "English" , "Exercise and Nutrition Sciences"
                , "Gender and Women's Studies" , "Geography" , "History" , "Human Development and Fam Relations" , "Institute for Ethics in Public Life" , "Interdisciplinary & Area Studies" , "Journalism and Public Relations" , "Lake Champlain Research Institution" ,
                "Latin American Studies" , "Learning Center" , "Library and Information Tech Services","Management, Info Sys and Analytics" , "Marketing & Entrepreneurship" , "Mathematics" , "Modern Languages and Cultures" , "Music" , "Nursing" , "Philosophy" , "Physics" ,
                "Political Science" , "Psychology" , "Sociology" , "Supply Chain Management & International Business" , "Teacher Education, Grad MSEd Program" , "Teacher Education, Undergraduate" , "Technology Enhanced Learning" , "Telecommunications" , "Theatre")


        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_items_department, departments)
        val autoCompleteDepartmentDropDown = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autoCompleteDepartmentDropDown.setAdapter(arrayAdapter)
        var itemSelected = "" //TODO :: TAKE THE PATH
        searchText.isEnabled = false
        var focusVal = 0
        autoCompleteDepartmentDropDown.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            itemSelected = parent.getItemAtPosition(position).toString()
            fileName = "$itemSelected.json"
            try{
                val obj = JSONObject(getJSONFromAssets(fileName)!!)
                val facultyArray = obj.getJSONArray("$itemSelected")

                for (i in 0 until facultyArray.length()){
                    val facultyItems = facultyArray.getJSONObject(i)
                    val name = facultyItems.getString("Name")
                    val title = facultyItems.getString("Title")
                    val email = facultyItems.getString("Email")
                    val faculty = facultyItems.getString("Faculty")

                    val facultyDetails = SearchViewByNameDataClass(name, faculty, email, title)
                    facultyList.add(facultyDetails)
                    focusVal = 1

                }
            }catch (e:JSONException){
                e.printStackTrace()
            }


            tempFacultyList.addAll(facultyList)
            if (focusVal == 1){
                searchText.isEnabled = true
            }

            if(focusVal == 0){
                searchText.setOnClickListener {
                    Toast.makeText(
                        this@SearchViewProfByNameActivity,
                        "Start by selecting a department",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            ListView.layoutManager = LinearLayoutManager(this)

            val clickAdapter = SearchListAdapter(this, tempFacultyList)


            //val facultyAdapter = SearchListAdapter(this, tempFacultyList)
            ListView.adapter = clickAdapter
            clickAdapter.setOnItemClickListener(object : SearchListAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@SearchViewProfByNameActivity, ProfessorScreen::class.java )
                    intent.putExtra("profName", tempFacultyList[position].Name)
                    intent.putExtra("faculty", tempFacultyList[position].Facutly)
                    intent.putExtra("email", tempFacultyList[position].Email)
                    intent.putExtra("title", tempFacultyList[position].Title)
                    startActivity(intent)

                }

            })


        }

        searchText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchValue : String = searchText.text.toString().lowercase()
                if(searchValue.isNotEmpty()){
                    tempFacultyList.clear()
                    facultyList.forEach{
                        if(it.Name.lowercase(Locale.getDefault())?.contains(searchValue)){
                            tempFacultyList.add(it)
                        }
                    }
                    ListView.adapter!!.notifyDataSetChanged()
                }
                else{
                    tempFacultyList.clear()
                    tempFacultyList.addAll(facultyList)
                    ListView.adapter!!.notifyDataSetChanged()

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        clearButton.setOnClickListener{
            ProfName_SearchView.text.clear()
            autoCompleteDepartmentDropDown.text.clear()
            itemSelected = ""
            tempFacultyList.clear()
            searchText.isEnabled = false
            focusVal = 0
            ListView.adapter!!.notifyDataSetChanged()
            Toast.makeText(
                this@SearchViewProfByNameActivity,
                "Select a department",
                Toast.LENGTH_LONG
            ).show()

        }





    }
    private fun getJSONFromAssets(fileName : String): String?{
        var json : String ?= null
        var charset : Charset = Charsets.UTF_8
        try{
            val i = assets.open(fileName)
            val size = i.available()
            val buffer = ByteArray(size)
            i.read(buffer)
            i.close()
            json = String(buffer,charset)
        }catch (ex : IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }



}