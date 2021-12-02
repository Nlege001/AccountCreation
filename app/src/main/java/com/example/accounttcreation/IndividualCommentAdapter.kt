package com.example.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class IndividualCommentAdapter(private val userList : ArrayList<IndividualCourseDataClass>) : RecyclerView.Adapter<IndividualCommentAdapter.MyviewHolder>() {
    private lateinit var mListener : onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualCommentAdapter.MyviewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.cardview_indiviudal_course_rating, parent, false)
        return MyviewHolder(itemview, mListener)
    }

    override fun onBindViewHolder(holder: IndividualCommentAdapter.MyviewHolder, position: Int) {
        val input : IndividualCourseDataClass = userList[position]
        holder.professor.text = input.proffessor
        holder.courseNumber.text = input.courseNumber
        holder.semester.text = input.semester
        holder.difficulty.text = input.difficlty
        holder.courseRating.text = input.courseRating
        holder.grade.text = input.grade
        holder.comments.text = input.comments
        holder.email.text = input.email

        val downloadURLPATH = input.downloadURLPATH
        Glide.with(holder.itemView.context).load(downloadURLPATH).into(holder.profilePic)



    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyviewHolder(itemview: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemview){

        val professor : TextView = itemview.findViewById(R.id.card_profName_card)
        val courseNumber : TextView = itemview.findViewById(R.id.card_crn_card)
        val semester : TextView = itemview.findViewById(R.id.card_semester_card)
        val difficulty : TextView = itemview.findViewById(R.id.card_difficulty_card)
        val courseRating : TextView = itemview.findViewById(R.id.card_cRating_card)
        val grade : TextView = itemview.findViewById(R.id.card_garde_card)
        val comments : TextView = itemview.findViewById(R.id.card_comments_card)
        val email : TextView = itemview.findViewById(R.id.card_email_profile_text)
        val profilePic : ImageView = itemview.findViewById(R.id.card_image_view_profile)


        init {
            itemview.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}