package com.ijk.data.repository

import com.google.gson.GsonBuilder
import com.ijk.data.api.RestApi
import com.ijk.data.api.safeApiCall
import com.ijk.data.dayWeatherListJson
import com.ijk.domain.entity.PlaceWeatherDayListEntity
import com.ijk.domain.entity.PlaceWeatherEntity
import com.ijk.domain.entity.RestResult
import com.ijk.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val restApi: RestApi,
    private val dispatcher: CoroutineDispatcher,
    @Named("weatherApiKey") val apiKey: String
) : WeatherRepository {

//    override suspend fun getPlaceWeatherDayList(
//        lat: Double,
//        lon: Double
//    ): RestResult<PlaceWeatherDayListEntity?> {
//        return safeApiCall(dispatcher) {
//            restApi.membersApi
//                .cityWeatherDayList(lat, lon, 4, apiKey)
//        }
//    }

    override suspend fun getPlaceWeatherDayList(
        lat: Double,
        lon: Double
    ): RestResult<PlaceWeatherDayListEntity?> {
        val obj = GsonBuilder().create()
            .fromJson(dayWeatherListJson, PlaceWeatherDayListEntity::class.java)
        return RestResult.Success(obj)
    }

    override suspend fun getPlaceWeatherByCoordinate(
        lat: Double,
        lon: Double
    ): RestResult<PlaceWeatherEntity?> {
        return safeApiCall(dispatcher) {
            restApi.membersApi.cityWeatherByCoordinate(lat, lon, apiKey)
        }
    }
}