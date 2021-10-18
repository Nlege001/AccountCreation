package com.example.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DisplayAdapterClass(private val userList : ArrayList<DisplayDataClass>) : RecyclerView.Adapter<DisplayAdapterClass.MyviewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayAdapterClass.MyviewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.list_item_display, parent, false)
        return MyviewHolder(itemview)
    }

    override fun onBindViewHolder(holder: DisplayAdapterClass.MyviewHolder, position: Int) {

        val input : DisplayDataClass = userList[position]
        holder.professor.text = input.proffessor
        holder.courseNumber.text = input.courseNumber
        holder.semester.text = input.semester
        holder.difficulty.text = input.difficlty
        holder.courseRating.text = input.courseRating
        holder.grade.text = input.grade
        holder.comments.text = input.comments


    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyviewHolder(itemview:View): RecyclerView.ViewHolder(itemview){

        val professor : TextView = itemview.findViewById(R.id.profName_card)
        val courseNumber : TextView = itemview.findViewById(R.id.crn_card)
        val semester : TextView = itemview.findViewById(R.id.semester_card)
        val difficulty : TextView = itemview.findViewById(R.id.difficulty_card)
        val courseRating : TextView = itemview.findViewById(R.id.cRating_card)
        val grade : TextView = itemview.findViewById(R.id.garde_card)
        val comments : TextView = itemview.findViewById(R.id.comments_card)
    }
}