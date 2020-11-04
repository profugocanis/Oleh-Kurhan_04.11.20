package com.ijk.weathermap.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ijk.domain.entity.PlaceWeatherEntity
import com.ijk.domain.entity.WeatherDay
import com.ijk.weathermap.R
import com.ijk.weathermap.base.BaseFragment
import com.ijk.weathermap.models.LatLngModel
import com.ijk.weathermap.utils.LoadView
import com.ijk.weathermap.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_place_weather.*

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class PlaceWeatherFragment : BaseFragment() {
    override fun obtainLayoutResId() = R.layout.fragment_place_weather
    private val placeWeatherViewModel: PlaceWeatherViewModel by viewModels()

    private var currentPlace: LatLngModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initLiveData()
    }

    private fun initViews() {
        contentView.alpha = 0f
        currentPlace = arguments?.getSerializable(currentPlaceKey) as? LatLngModel
        mainActivity.isBarHidden = false
        mainActivity.title = "Place Weather"
    }

    private fun initLiveData() {
        LoadView.show(requireView())
        placeWeatherViewModel.setFragment(this)
        placeWeatherViewModel.getCityWeather(currentPlace).observe(viewLifecycleOwner) {
            showUI(it)
        }

        placeWeatherViewModel.getCityWeatherDeyList(currentPlace) {
            updateUI(it)
        }.observe(viewLifecycleOwner) {
            dayRecyclerView.adapter = it
        }
    }

    private fun updateUI(weatherDay: WeatherDay) {
        txtDate.textAnim = (weatherDay.dt * 1000).getDayNormal()
        txtTemperature.textAnim = "${(weatherDay.temp.day - 273.15).toFloat().round10()}°"
        txtDescription.textAnim = weatherDay.weather.first().description
        imageWeather.loadWeatherIcon(weatherDay.weather.first().icon)
        txtSpeed.textAnim = "Wind ${weatherDay.speed} m/s"
        txtHumidity.textAnim = "Humidity ${weatherDay.humidity} %"
    }

    private fun showUI(place: PlaceWeatherEntity) {
        LoadView.dismiss()
        contentView.showAnim()
        if (place.name == "") {
            txtPlaceName.text = "${place.coord.lat}, ${place.coord.lon}"
        } else {
            txtPlaceName.text = place.name
        }

        txtDate.text = (place.dt * 1000).getDayNormal()
        if (place.weather.isNotEmpty()) {
            txtDescription.text = place.weather.first().description
            imageWeather.loadWeatherIcon(place.weather.first().icon)
        }
        txtTemperature.text = "${(place.main.temp - 273.15).toFloat().round10()}°"
        txtSpeed.text = "Wind ${place.wind.speed} m/s"
        txtHumidity.text = "Humidity ${place.main.humidity} %"
    }

    companion object {
        const val currentPlaceKey = "CurrentLatLng"
    }
}