package com.example.accounttcreation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_search_list.view.*

class SearchListAdapter(var searchList : List<SearchViewByNameDataClass>): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {
    /*class SearchListViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun bind(searchModel: SearchViewByNameDataClass){
            itemView.listView_profName.text = searchModel.Name
            itemView.listView_title.text = searchModel.Title
        }
    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListAdapter.SearchListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_list, parent, false)
        return SearchListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchListAdapter.SearchListViewHolder, position: Int) {
        //holder.bind
        val currentItem = searchList[position]
        holder.name.text = currentItem.Name
        holder.title.text = currentItem.Title
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    class SearchListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.listView_profName)
        val title : TextView = itemView.findViewById(R.id.listView_title)
    }

}