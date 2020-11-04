package com.ijk.weathermap.models

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class LatLngModel(val latitude: Double, val longitude: Double) : Serializable

fun LatLng.getModel(): LatLngModel {
    return LatLngModel(this.latitude, this.longitude)
}