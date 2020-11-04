package com.ijk.data.api

import com.ijk.domain.entity.PlaceWeatherDayListEntity
import com.ijk.domain.entity.PlaceWeatherEntity
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MembersApi {
    @GET("forecast/daily")
    suspend fun cityWeatherDayList(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String
    ): Response<PlaceWeatherDayListEntity>

    @GET("weather")
    suspend fun cityWeatherByCoordinate(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Response<PlaceWeatherEntity>
}