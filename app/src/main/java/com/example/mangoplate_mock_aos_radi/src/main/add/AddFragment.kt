package com.example.mangoplate_mock_aos_radi.src.main.add

import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AnimationUtils
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentAddBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity.Companion.isFgInAddOpen
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity.Companion.isOpen
import com.example.mangoplate_mock_aos_radi.src.main.register.RegisterRestaurantFragment
import kotlin.concurrent.timer

class AddFragment: BaseFragment<FragmentAddBinding>(FragmentAddBinding::bind, R.layout.fragment_add){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isOpen) binding.addFbtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_rotate_add_fbtn))

        val animation_eat_deal = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_eat_deal)
        val animation_visited = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_visited)
        val animation_review = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_review)
        val animation_register = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_register)

        binding.addLayoutEatDeal.startAnimation(animation_eat_deal)
        binding.addLayoutVisited.startAnimation(animation_visited)
        binding.addLayoutReview.startAnimation(animation_review)
        binding.addLayoutRegister.startAnimation(animation_register)

        binding.addLayoutRegister.setOnClickListener {
            isOpen = false
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).addFragment(RegisterRestaurantFragment())
        }


        var duration = 2
        binding.addFbtn.setOnClickListener {
            if (!isOpen) {
                isOpen = !isOpen
            } else {
                isOpen = !isOpen
                binding.addFbtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_rotate_add_fbtn_after))

                val handler = Handler {
                    when (it.what) {
                        0 -> {
                            (activity as MainActivity).onBackPressed()
                            true
                        }
                        else -> {true}
                    }
                }
                timer(period = 100) {
                    duration--
                    if (duration == 0) handler.obtainMessage(0).sendToTarget()
                }

            }
        }

    }
}