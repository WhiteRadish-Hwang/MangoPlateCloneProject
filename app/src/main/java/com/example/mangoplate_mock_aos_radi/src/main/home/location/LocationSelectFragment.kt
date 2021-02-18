package com.example.mangoplate_mock_aos_radi.src.main.home.location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.LOC_LIST
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentLocSelectBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment.Companion.isLocCheck
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment.Companion.locList
import com.example.mangoplate_mock_aos_radi.src.main.myPage.MyPageFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class LocationSelectFragment : BottomSheetDialogFragment() {
//    val itemClick: (Int) -> Unit
    var _binding: FragmentLocSelectBinding? = null
    val binding get() = _binding!!
    var position = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLocSelectBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.locSortVp.adapter = LocSelectTabPagerAdapter(this)

        setTabLayoutMediator()

        binding.locSelectBtnApply.setOnClickListener {
            dialog?.dismiss()
        }

        binding.locSelectBtnApply.setOnClickListener {
            isLocCheck = false
            dialog?.dismiss()
            SharedPreferenced.putArrayStringItem(LOC_LIST, locList)
            (activity as MainActivity).replaceFragment(HomeFragment())
        }


    }

    private inner class LocSelectTabPagerAdapter(fragment: LocationSelectFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 29

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> {
//                    binding.locLayoutEdit.visibility = View.VISIBLE
                    RecentLocFragment()
                }
                1 -> {
//                    binding.locLayoutEdit.visibility = View.INVISIBLE
                    AboutMyPlaceFragment()
                }
                else -> {
//                    binding.locLayoutEdit.visibility = View.INVISIBLE
                    GangbukFragment()
                }
            }
        }

    }

    fun setTabLayoutMediator() {
        TabLayoutMediator(binding.locLayoutTab, binding.locSortVp) {tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.location_select_list_recent)
                }
                1 -> {
                    tab.setText(R.string.location_select_list_about_me)
                }
                2 -> {
                    tab.setText(R.string.location_select_list_gangnam)
                }
                3 -> {
                    tab.setText(R.string.location_select_list_gangbuk)
                }
                4 -> {
                    tab.setText(R.string.location_select_list_gyeongggi)
                }
                5 -> {
                    tab.setText(R.string.location_select_list_incheon)
                }
                6 -> {
                    tab.setText(R.string.location_select_list_daegu)
                }
                7 -> {
                    tab.setText(R.string.location_select_list_busan)
                }
                8 -> {
                    tab.setText(R.string.location_select_list_jeju)
                }
                9 -> {
                    tab.setText(R.string.location_select_list_daejeon)
                }
                10 -> {
                    tab.setText(R.string.location_select_list_gwangju)
                }
                11 -> {
                    tab.setText(R.string.location_select_list_gangwon)
                }
                12 -> {
                    tab.setText(R.string.location_select_list_south_gs)
                }
                13 -> {
                    tab.setText(R.string.location_select_list_north_gs)
                }
                14 -> {
                    tab.setText(R.string.location_select_list_south_jr)
                }
                15 -> {
                    tab.setText(R.string.location_select_list_north_jr)
                }
                16 -> {
                    tab.setText(R.string.location_select_list_south_cc)
                }
                17 -> {
                    tab.setText(R.string.location_select_list_north_cc)
                }
                18 -> {
                    tab.setText(R.string.location_select_list_ulsan)
                }
                19 -> {
                    tab.setText(R.string.location_select_list_sejong)
                }
                20 -> {
                    tab.setText(R.string.location_select_list_japan)
                }
                21 -> {
                    tab.setText(R.string.location_select_list_china)
                }
                22 -> {
                    tab.setText(R.string.location_select_list_asia)
                }
                23 -> {
                    tab.setText(R.string.location_select_list_europe)
                }
                24 -> {
                    tab.setText(R.string.location_select_list_usa)
                }
                25 -> {
                    tab.setText(R.string.location_select_list_canada)
                }
                26 -> {
                    tab.setText(R.string.location_select_list_central_south_usa)
                }
                27 -> {
                    tab.setText(R.string.location_select_list_oceania)
                }
                28 -> {
                    tab.setText(R.string.location_select_list_etc)
                }
            }
        }.attach()
    }
}