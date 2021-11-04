package com.example.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import javax.annotation.Nonnull

class SBAdapter {
    var list: ArrayList<DataClass>? = null
    fun AdapterClass(list: ArrayList<DataClass>?) {
        this.list = list
    }

    fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.items_db_recycler, viewGroup, false)
        return ViewHolder(view)
    }

    fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.email.text = list!![i].Email
        viewHolder.faculty.text = list!![i].Faculty
        viewHolder.name.text = list!![i].Name
        viewHolder.title.text = list!![i].Title
    }

    fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(@Nonnull ListView: View) :
        RecyclerView.ViewHolder(ListView) {
        var email: TextView
        var faculty: TextView
        var name: TextView
        var title: TextView

        init {
            email = ListView.findViewById(R.id.EmailDB)
            faculty = ListView.findViewById(R.id.FacultyDB)
            name = ListView.findViewById(R.id.NameDB)
            title = ListView.findViewById(R.id.TitleDB)
        }
    }
}