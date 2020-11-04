package com.ijk.weathermap.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ijk.weathermap.R
import com.ijk.weathermap.base.BaseFragment
import com.ijk.weathermap.models.getModel
import com.ijk.weathermap.ui.place.PlaceWeatherFragment
import com.ijk.weathermap.utils.extensions.getBitmapDescriptor
import com.ijk.weathermap.utils.extensions.isHidden
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*

@AndroidEntryPoint
class MapFragment : BaseFragment(), OnMapReadyCallback {

    override fun obtainLayoutResId() = R.layout.fragment_map
    private var mMap: GoogleMap? = null
    private val mapViewModel: MapViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        mainActivity.isBarHidden = true
        mapViewModel.setFragment(this)
    }

    private fun initViews() {
        btnSowPlaceWeather.isHidden = mapViewModel.currentLatLng == null
        btnSowPlaceWeather.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(
                PlaceWeatherFragment.currentPlaceKey,
                mapViewModel.currentLatLng?.getModel()
            )
            navGoTo(R.id.placeWeatherFragment, bundle)
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.mMap = map
        this.mMap?.setOnMapClickListener {
            addMarker(it)
        }

        if (mapViewModel.currentLatLng != null) {
            addMarker(mapViewModel.currentLatLng)
        }
    }

    private fun addMarker(latLng: LatLng?) {
        if (latLng == null) return
        val place = LatLng(latLng.latitude, latLng.longitude)
        mapViewModel.currentMarker?.remove()
        val markerOptions = MarkerOptions().position(place)
            .icon(getBitmapDescriptor(R.drawable.ic_marker))
        mapViewModel.currentMarker = mMap?.addMarker(markerOptions)

        mapViewModel.currentLatLng = latLng
        mMap?.animateCamera(CameraUpdateFactory.newLatLng(place))
        btnSowPlaceWeather.isHidden = false
    }
}