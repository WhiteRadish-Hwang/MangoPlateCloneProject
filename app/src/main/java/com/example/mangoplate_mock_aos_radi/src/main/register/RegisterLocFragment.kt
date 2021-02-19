package com.example.mangoplate_mock_aos_radi.src.main.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentRegisterLocSelectBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.n.api.internal.NativeMapLocationManager.setCurrentLocationTrackingMode
import kotlin.properties.Delegates

class RegisterLocFragment: BaseFragment<FragmentRegisterLocSelectBinding>(FragmentRegisterLocSelectBinding::bind, R.layout.fragment_register_loc_select) {

    val mapView = MapView(activity)

//    var mCurrentLat by Delegates.notNull<Double>()
//    var mCurrentLng by Delegates.notNull<Double>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapViewContainer = binding.regusterLocLayoutMap as ViewGroup
        mapViewContainer.addView(mapView)

        binding.registerLocImgArrow.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

//
//        var
//
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(restaurantLatitudeArg!!.toDouble(), restaurantLongiudeArg!!.toDouble()), true)
//
//        val marker = MapPOIItem()
//        val mapPoint = MapPoint.mapPointWithGeoCoord(restaurantLatitudeArg!!.toDouble(), restaurantLongiudeArg!!.toDouble())
//        mapView.setMapCenterPoint(mapPoint, true)
//        marker.itemName = "쉐프마인드"
//        marker.tag = 0
//        marker.mapPoint = mapPoint
//        marker.markerType = MapPOIItem.MarkerType.BluePin
//        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
//        mapView.addPOIItem(marker)

    }


}