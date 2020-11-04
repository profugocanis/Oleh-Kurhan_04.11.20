package com.ijk.weathermap.ui.place

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ijk.domain.entity.PlaceWeatherEntity
import com.ijk.domain.entity.RestResult
import com.ijk.domain.entity.WeatherDay
import com.ijk.domain.repository.WeatherRepository
import com.ijk.weathermap.R
import com.ijk.weathermap.base.BaseViewModel
import com.ijk.weathermap.models.LatLngModel
import com.ijk.weathermap.utils.collection_adapter.CollectionAdapter
import com.ijk.weathermap.utils.collection_adapter.CollectionAdapterFactory
import com.ijk.weathermap.utils.collection_adapter.CollectionDelegate
import com.ijk.weathermap.utils.extensions.getDay
import com.ijk.weathermap.utils.extensions.loadWeatherIcon
import com.ijk.weathermap.utils.extensions.round10
import com.ijk.weathermap.utils.loget
import kotlinx.android.synthetic.main.item_day_weather.view.*
import kotlinx.coroutines.launch

class PlaceWeatherViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

//    private var weatherDayList: List<WeatherDay> = listOf()

    fun getCityWeather(latLngModel: LatLngModel?): LiveData<PlaceWeatherEntity> {
        val res = MutableLiveData<PlaceWeatherEntity>()
        viewModelScope.launch {
            val response = weatherRepository.getPlaceWeatherByCoordinate(
                latLngModel?.latitude ?: 0.0,
                latLngModel?.longitude ?: 0.0
            )
            when (response) {
                is RestResult.NetworkError -> {
                    errorConsumer(Exception("Network error"))
                }
                is RestResult.GenericError -> {
                    errorConsumer(response.error)
                }
                is RestResult.Success -> {
                    res.value = response.value
                }
            }
        }
        return res
    }

    fun getCityWeatherDeyList(
        latLngModel: LatLngModel?,
        updateUI: (WeatherDay) -> Unit
    ): LiveData<CollectionAdapter<WeatherDay, ViewGroup>?> {
        val res = MutableLiveData<CollectionAdapter<WeatherDay, ViewGroup>?>()
        viewModelScope.launch {
            val response = weatherRepository.getPlaceWeatherDayList(
                latLngModel?.latitude ?: 0.0,
                latLngModel?.longitude ?: 0.0
            )
            when (response) {
                is RestResult.NetworkError -> {
                    errorConsumer(Exception("Network error"))
                }
                is RestResult.GenericError -> {
                    errorConsumer(response.error)
                }
                is RestResult.Success -> {
                    if (adapterDay == null) {
                        adapterDay =
                            createDayWeatherAdapter(response.value?.list ?: listOf(), updateUI)
                    }
                    res.value = adapterDay
                }
            }
        }
        return res
    }

    var adapterDay: CollectionAdapter<WeatherDay, ViewGroup>? = null

    @SuppressLint("SetTextI18n")
    private fun createDayWeatherAdapter(
        weatherDayList: List<WeatherDay>,
        updateUI: (WeatherDay) -> Unit
    ): CollectionAdapter<WeatherDay, ViewGroup> {
        var selectedIndex = 0
        var adapter: CollectionAdapter<WeatherDay, ViewGroup>? = null
        adapter = CollectionAdapterFactory.Builder<WeatherDay>()
            .setRes(R.layout.item_day_weather)
            .setList(weatherDayList)
            .setBindView { v, data, i ->
                loget(data)
                v.txtDay.text = (data.dt * 1000).getDay()
                v.imageDay.loadWeatherIcon(data.weather.first().icon)
                v.txtTemperatureDay.text = "${(data.temp.day - 273.15).toFloat().round10()} °C"
                if (selectedIndex == i) {
                    v.setBackgroundResource(R.drawable.background_selected)
                } else {
                    v.setBackgroundResource(R.drawable.background_normal)
                }
            }
            .setDelegate(object : CollectionDelegate<WeatherDay> {
                override fun onItemClick(item: WeatherDay, view: View, position: Int) {
                    loget("wgweg")
                    selectedIndex = position
                    adapter?.notifyDataSetChanged()
                    updateUI(item)
                    view.setBackgroundResource(R.drawable.background_selected)
                }
            })
            .create()
        return adapter
    }
}