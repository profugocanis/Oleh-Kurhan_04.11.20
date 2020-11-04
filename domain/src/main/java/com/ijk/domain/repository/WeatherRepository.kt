package com.ijk.domain.repository

import com.ijk.domain.entity.PlaceWeatherDayListEntity
import com.ijk.domain.entity.PlaceWeatherEntity
import com.ijk.domain.entity.RestResult

interface WeatherRepository {
    suspend fun getPlaceWeatherDayList(
        lat: Double,
        lon: Double
    ): RestResult<PlaceWeatherDayListEntity?>

    suspend fun getPlaceWeatherByCoordinate(
        lat: Double,
        lon: Double
    ): RestResult<PlaceWeatherEntity?>
}