package com.example.accounttcreation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_search_list.view.*

class SearchListAdapter(var context : Context, val items :ArrayList<SearchViewByNameDataClass>): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_list, parent, false)
        return SearchListViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {

        val item = items.get(position)
        holder.name.text = item.Name
        holder.title.text = item.Title
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SearchListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.listView_profName)
        val title : TextView = itemView.findViewById(R.id.listView_title)
    }





}