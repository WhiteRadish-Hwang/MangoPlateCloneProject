package com.example.mangoplate_mock_aos_radi.src.main.news.location.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.news.location.model.NewsLocSelectRecyclerItems

class NewsLocSelectRecyclerAdapter(val context: Context?, var itemList: ArrayList<NewsLocSelectRecyclerItems>): RecyclerView.Adapter<NewsLocSelectRecyclerAdapter.ItemViewHolder>() {
    interface MyNewsLocItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyNewsLocItemClickListener

    fun setMyNewsLocItemClickListener(itemClickListener: MyNewsLocItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val locSelectTextSearchWord: TextView = itemView.findViewById(R.id.loc_select_text_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_loc_select_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: NewsLocSelectRecyclerItems = itemList[position]
        holder.locSelectTextSearchWord.text = items.location
    }

}