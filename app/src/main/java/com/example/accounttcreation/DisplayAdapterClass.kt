package com.example.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.nav_profile_header.*

class DisplayAdapterClass(private val userList : ArrayList<DisplayDataClass>) : RecyclerView.Adapter<DisplayAdapterClass.MyviewHolder>() {
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayAdapterClass.MyviewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.list_item_display, parent, false)
        return MyviewHolder(itemview, mListener)
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
        holder.email.text = input.email

        val downloadURLPATH = input[8]
        Glide.with(holder.itemView.context).load(downloadURLPATH).into(holder.profilePic)








    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyviewHolder(itemview:View, listener: onItemClickListener): RecyclerView.ViewHolder(itemview){

        val professor : TextView = itemview.findViewById(R.id.profName_card)
        val courseNumber : TextView = itemview.findViewById(R.id.crn_card)
        val semester : TextView = itemview.findViewById(R.id.semester_card)
        val difficulty : TextView = itemview.findViewById(R.id.difficulty_card)
        val courseRating : TextView = itemview.findViewById(R.id.cRating_card)
        val grade : TextView = itemview.findViewById(R.id.garde_card)
        val comments : TextView = itemview.findViewById(R.id.comments_card)
        val email : TextView = itemview.findViewById(R.id.email_profile_text)
        val profilePic : ImageView = itemview.findViewById(R.id.image_view_profile)


        init {
            itemview.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }


}