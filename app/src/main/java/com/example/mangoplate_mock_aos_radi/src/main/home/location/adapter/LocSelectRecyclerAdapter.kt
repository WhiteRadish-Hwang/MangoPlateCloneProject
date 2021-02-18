package com.example.mangoplate_mock_aos_radi.src.main.home.location.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment.Companion.isLocCheck
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment.Companion.locList
import com.example.mangoplate_mock_aos_radi.src.main.home.location.model.LocSelectRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.location.NewsGangbukFragment.Companion.newsLocList
import kotlin.reflect.typeOf

class LocSelectRecyclerAdapter(val context: Context?, var itemList: ArrayList<LocSelectRecyclerItems>): RecyclerView.Adapter<LocSelectRecyclerAdapter.ItemViewHolder>() {
    interface MyLocItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyLocItemClickListener

    fun setMyLocItemClickListener(itemClickListener: MyLocItemClickListener){
        mItemClickListener = itemClickListener
    }


    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val locSelectTextSearchWord: TextView = itemView.findViewById(R.id.loc_select_text_location)
        val locSelectImgCheck: ImageView = itemView.findViewById(R.id.loc_select_img_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_loc_select_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: LocSelectRecyclerItems = itemList[position]
        holder.locSelectTextSearchWord.text = items.location

        when (items.isSelected) {
            true -> {
                holder.locSelectImgCheck.visibility = View.VISIBLE
                holder.locSelectTextSearchWord.setBackgroundResource(R.drawable.loc_select_text_border)
                holder.locSelectTextSearchWord.setTextColor(ContextCompat.getColor(context!!,R.color.cliked_color))
            }
            false -> {
                holder.locSelectImgCheck.visibility = View.INVISIBLE
                holder.locSelectTextSearchWord.setBackgroundResource(R.drawable.loc_select_text_border_unclicked)
                holder.locSelectTextSearchWord.setTextColor(ContextCompat.getColor(context!!,R.color.uncliked_color))
            }
        }

        holder.itemView.setOnClickListener {
            items.isSelected = !items.isSelected
            Log.d(TAG, "items.isSelected: ${items.isSelected}")
            when (items.isSelected) {
                true -> {
                    holder.locSelectImgCheck.visibility = View.VISIBLE
                    holder.locSelectTextSearchWord.setBackgroundResource(R.drawable.loc_select_text_border)
                    holder.locSelectTextSearchWord.setTextColor(ContextCompat.getColor(context,R.color.cliked_color))
                    if (items.isHomeLoc) locList.add(items.location)
                    else newsLocList.add(items.location)
                }
                false -> {
                    holder.locSelectImgCheck.visibility = View.INVISIBLE
                    holder.locSelectTextSearchWord.setBackgroundResource(R.drawable.loc_select_text_border_unclicked)
                    holder.locSelectTextSearchWord.setTextColor(ContextCompat.getColor(context,R.color.uncliked_color))
                    if (items.isHomeLoc) locList.remove(items.location)
                    else newsLocList.remove(items.location)
                }
            }
            if (items.isHomeLoc) Log.d(TAG, "locList: $locList")
            else Log.d(TAG, "newsLocList: $newsLocList")

        }
    }

}