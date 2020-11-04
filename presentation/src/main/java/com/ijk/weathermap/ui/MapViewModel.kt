package com.ijk.weathermap.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.ijk.domain.entity.RestResult
import com.ijk.domain.repository.WeatherRepository
import com.ijk.weathermap.base.BaseViewModel
import com.ijk.weathermap.utils.loget
import kotlinx.coroutines.launch

class MapViewModel @ViewModelInject constructor() : BaseViewModel() {
    var map: GoogleMap? = null
    val isMapNull: Boolean
        get() {
            return (map == null)
        }

    var currentLatLng: LatLng? = null
    var currentMarker: Marker? = null
}

