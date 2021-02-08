package com.example.mangoplate_mock_aos_radi.src.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeSortSelectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@SuppressLint("ResourceAsColor")
class HomeSortSelectFragment(val itemClick: (Int) -> Unit): BottomSheetDialogFragment() {
    var _binding: FragmentHomeSortSelectBinding? = null
    val binding get() = _binding!!
    val checkList = arrayListOf<Boolean>(isSelectedPoint, isSelectedRecommend, isSelectedReview, isSelectedDistance)

    companion object {
        var isSelectedPoint: Boolean = true
        var isSelectedRecommend: Boolean = false
        var isSelectedReview: Boolean = false
        var isSelectedDistance: Boolean = false
        var position: Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeSortSelectBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputCheckData(position)

        when (position) {
            0 -> {
                setTextDesign(binding.homeSortSelectTextPoint)
            }
            1 -> {
                setTextDesign(binding.homeSortSelectTextRecommend)
            }
            2 -> {
                setTextDesign(binding.homeSortSelectTextReview)
            }
            3 -> {
                setTextDesign(binding.homeSortSelectTextDistance)
            }
        }


        binding.homeSortSelectImgArrow.setOnClickListener {
            dialog?.dismiss()
        }

        binding.homeSortSelectTextPoint.setOnClickListener {
            position = 0
            itemClick(position)
            dialog?.dismiss()
        }
        binding.homeSortSelectTextRecommend.setOnClickListener {
            position = 1
            itemClick(position)
            dialog?.dismiss()
        }
        binding.homeSortSelectTextReview.setOnClickListener {
            position = 2
            itemClick(position)
            dialog?.dismiss()
        }
        binding.homeSortSelectTextDistance.setOnClickListener {
            position = 3
            itemClick(position)
            dialog?.dismiss()
        }


    }

    fun setTextDesign(view: TextView){
        val homeSortSelectTextPoint = binding.homeSortSelectTextPoint
        val homeSortSelectTextRecommend = binding.homeSortSelectTextRecommend
        val homeSortSelectTextReview = binding.homeSortSelectTextReview
        val homeSortSelectTextDistance = binding.homeSortSelectTextDistance
        val sortTextViewArray = arrayListOf<TextView>(
            homeSortSelectTextPoint,
            homeSortSelectTextRecommend,
            homeSortSelectTextReview,
            homeSortSelectTextDistance
        )

        for (v in sortTextViewArray) {
            Log.d(TAG, "setTextDesign: ${view == v}")
            if (view == v) {
                v.setBackgroundResource(R.drawable.home_sort_select_text_border)
                v.setTextColor(ContextCompat.getColor(context!!, R.color.cliked_color))
            } else {
                v.setBackgroundResource(R.drawable.home_sort_select_text_border_unclicked)
                v.setTextColor(ContextCompat.getColor(context!!, R.color.uncliked_color))
            }
        }
    }

    fun inputCheckData(position: Int) {
        for (i in 0 until checkList.size) {
            if (i == position) checkList[i] = true
            else checkList[i] = false
        }
    }
}