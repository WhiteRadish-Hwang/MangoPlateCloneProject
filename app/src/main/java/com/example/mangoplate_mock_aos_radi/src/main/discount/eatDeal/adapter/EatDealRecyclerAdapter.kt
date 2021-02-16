package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealRecyclerData

class EatDealRecyclerAdapter(val context: Context?, val itemList: ArrayList<EatDealRecyclerData>): RecyclerView.Adapter<EatDealRecyclerAdapter.EatDealViewHolder>() {
    interface MyEatDealItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyEatDealItemClickListener

    fun setMyItemClickListener(itemClickListener: MyEatDealItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class EatDealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val eatDealImg: ImageView = itemView.findViewById(R.id.eat_deal_img)
        val eatDealTextName: TextView = itemView.findViewById(R.id.eat_deal_text_name)
        val eatdealTextOneLine: TextView = itemView.findViewById(R.id.eat_deal_text_one_line)
        val eatDealTextCanTakeOut: TextView = itemView.findViewById(R.id.eat_deal_text_can_take_out)
        val eatDealTextNewAndHot: TextView = itemView.findViewById(R.id.eat_deal_text_new_and_hot)
        val eatDealTextDiscountRate: TextView = itemView.findViewById(R.id.eat_deal_text_discount_rate)
        val eatDealTextDiscountPrice: TextView = itemView.findViewById(R.id.eat_deal_text_discounted_price)
        val eatDealTextOriginalPrice: TextView = itemView.findViewById(R.id.eat_deal_text_original_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EatDealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_eat_deal_recycler_items, parent, false)
        return EatDealViewHolder(view)
    }


    override fun onBindViewHolder(holder: EatDealViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.eatDealImg).load(items.firstImageUrl).placeholder(R.drawable.home_vp_img1).into(holder.eatDealImg)
        holder.eatDealTextName.text = items.eatDealName
        holder.eatdealTextOneLine.text = items.eatDealOneLine
        holder.eatDealTextCanTakeOut.text = items.eatDealPickUpText
        holder.eatDealTextNewAndHot.text = "NEW"
        holder.eatDealTextDiscountRate.text = items.eatDealDiscount.toString()
        holder.eatDealTextOriginalPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.eatDealTextOriginalPrice.text = items.eatDealBeforePrice
        holder.eatDealTextDiscountPrice.text = items.eatDealAfterPrice
    }

    override fun getItemCount(): Int = itemList.size

    fun clearItemList() {
        itemList.clear()
        notifyDataSetChanged()
    }
}