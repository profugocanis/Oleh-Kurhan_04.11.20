package com.ijk.domain.entity

data class PlaceWeatherDayListEntity(
    val city: PlaceWeatherEntity,
    val cod: String,
    val message: Float,
    val cnt: Int,
    val list: List<WeatherDay>
)

data class WeatherDay(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val feels_like: FeelsLike,
    val pressure: Float,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Float,
    val deg: Int,
    val clouds: Int,
    val pop: Float
)

data class Temp(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
)

data class FeelsLike(
    val day: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
)
//data class City(
//val id: Int,
//val name
//)
