package com.ijk.weathermap.utils.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.ijk.data.base.baseMembersIconUrl
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadWeatherIcon(name: String) {
    Picasso.get().load("$baseMembersIconUrl/${name}.png").into(this)
}