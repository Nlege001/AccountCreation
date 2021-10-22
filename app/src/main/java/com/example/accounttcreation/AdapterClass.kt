package com.example.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(private val userList : ArrayList<DataClass>)  : RecyclerView.Adapter<AdapterClass.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items_db_recycler, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.Email.text = currentItem.Email
        holder.Faculty.text = currentItem.Faculty
        holder.Name.text = currentItem.Name
        holder.Title.text = currentItem.Title
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val Email : TextView  = itemView.findViewById(R.id.EmailDB)
        val Faculty : TextView = itemView.findViewById(R.id.FacultyDB)
        val Name : TextView = itemView.findViewById(R.id.NameDB)
        val Title : TextView= itemView.findViewById(R.id.TitleDB)


    }
}