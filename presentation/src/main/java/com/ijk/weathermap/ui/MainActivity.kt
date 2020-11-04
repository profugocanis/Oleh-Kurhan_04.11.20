package com.ijk.weathermap.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.ijk.weathermap.R
import com.ijk.weathermap.base.BaseActivity
import com.ijk.weathermap.utils.extensions.isHidden
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun obtainLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnBack.setOnClickListener {
            findNavController(R.id.nav_host_fragment).popBackStack()
        }
    }

    var title: String
        get() = appTitle.text.toString()
        set(value) {
            appTitle.text = value
        }

    var isBarHidden: Boolean = true
        get() = appToolbar.isHidden
        set(value) {
            setToolBarHiddenAnimate(value)
            field = value
        }

    private fun setToolBarHiddenAnimate(isHidden: Boolean) {
        if (isHidden) {
            toolbarAnim(-300f)
        } else {
            toolbarAnim(0f)
        }
    }

    private fun toolbarAnim(value: Float) {
        ObjectAnimator.ofFloat(appToolbar, View.TRANSLATION_Y, value)
            .setDuration(200)
            .start()
    }
}