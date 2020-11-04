package com.ijk.weathermap.utils.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.ijk.weathermap.R
import com.ijk.weathermap.base.BaseFragment

fun BaseFragment.getBitmapDescriptor(@DrawableRes id: Int): BitmapDescriptor? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val vectorDrawable = AppCompatResources.getDrawable(requireContext(), id) as VectorDrawable
        val h = vectorDrawable.intrinsicHeight
        val w = vectorDrawable.intrinsicWidth
        vectorDrawable.setBounds(0, 0, w, h)
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bm)
    } else {
        BitmapDescriptorFactory.fromResource(id)
    }
}