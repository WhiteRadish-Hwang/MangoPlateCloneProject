package com.example.mangoplate_mock_aos_radi.src.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        Log.d(TAG, "position1: $position")
        position = isSelectedCheck(position)
        Log.d(TAG, "position2: $position")

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
            if (view == v) {
                Log.d(TAG, "setTextDesign: view $view v $v")
                view.setBackgroundResource(R.drawable.home_sort_select_text_border)
                view.setTextColor(R.color.cliked_color)
            } else {
                v.setBackgroundResource(R.drawable.home_sort_select_text_border_unclicked)
                v.setTextColor(R.color.uncliked_color)
            }
        }
    }

    fun isSelectedCheck(position: Int): Int {
        when (position) {
            0 -> {
                inputCheckData(position)
                return position
            }
            1 -> {
                inputCheckData(position)
                return position
            }
            2 -> {
                inputCheckData(position)
                return position
            }
            else -> {
                inputCheckData(position)
                return position
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