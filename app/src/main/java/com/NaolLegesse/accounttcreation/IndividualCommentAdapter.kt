package com.NaolLegesse.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
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
        holder.semester.text = input.semester
        holder.email.text = input.email
        holder.likeCount.text = input.likeCount.toString()
        holder.dislikeCount.text = input.dislikeCount.toString()
        holder.ratingBar.rating = input.courseRating?.toFloat()!!
        holder.displayRatingBar.text = input.courseRating
        holder.courseName.text = input.courseName

        val downloadURLPATH = input.downloadURLPATH
        Glide.with(holder.itemView.context).load(downloadURLPATH).into(holder.profilePic)



    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyviewHolder(itemview: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemview){

        val professor : TextView = itemview.findViewById(R.id.card_profName_card)
        val courseName : TextView = itemview.findViewById(R.id.card_crn_card_display)
        val semester : TextView = itemview.findViewById(R.id.semester_card_display)
        val email : TextView = itemview.findViewById(R.id.card_email_profile_text)
        val profilePic : ImageView = itemview.findViewById(R.id.card_image_view_profile)
        val likeCount : TextView = itemview.findViewById(R.id.card_like_count)
        val dislikeCount : TextView = itemview.findViewById(R.id.card_dislike_count)
        val ratingBar : RatingBar = itemview.findViewById(R.id.course_rating_bar)
        val displayRatingBar : TextView = itemview.findViewById(R.id.ratingbar_display)


        init {
            itemview.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}