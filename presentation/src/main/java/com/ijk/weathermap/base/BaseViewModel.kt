package com.ijk.weathermap.base

import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijk.domain.entity.WeatherDay
import com.ijk.weathermap.R
import com.ijk.weathermap.utils.LoadView
import com.ijk.weathermap.utils.collection_adapter.CollectionAdapter
import com.ijk.weathermap.utils.collection_adapter.CollectionAdapterFactory

open class BaseViewModel : ViewModel() {
    var errorLiveData = MutableLiveData<String>()

    fun setFragment(fragment: Fragment) {
        errorLiveData.observe(fragment) {
            Toast.makeText(fragment.requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    open fun errorConsumer(error: Exception?) {
        LoadView.dismiss()
        errorLiveData.value = error?.localizedMessage
    }
}