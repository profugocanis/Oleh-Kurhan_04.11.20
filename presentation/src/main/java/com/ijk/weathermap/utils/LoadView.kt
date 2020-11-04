package com.ijk.weathermap.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.viewanimator.ViewAnimator
import com.ijk.weathermap.R

object LoadView {
    private var loadView: View? = null

    fun dismiss() {
        animHide {}
    }

    fun show(view: View?) {
        if (view !is ViewGroup) {
            return
        }
        (loadView?.parent as? ViewGroup)?.removeView(loadView)
        if (loadView == null) {
            val inflater =
                view.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            loadView = inflater.inflate(R.layout.load_view, view, false)
        }
        loadView?.setOnClickListener { dismiss() }
        view.addView(loadView)
        animShow()
    }

    private fun animShow() {
        ViewAnimator
            .animate(loadView?.findViewById(R.id.loadContent))
            .waitForHeight()
            .dp().width(50f, 80f)
            .dp().height(50f, 80f)
            .duration(200)
            .fadeIn()
            .start()
    }

    private fun animHide(callback: () -> Unit) {
        ViewAnimator
            .animate(loadView?.findViewById(R.id.loadContent))
            .duration(200)
            .fadeOut()
            .start()

        (loadView?.parent as? ViewGroup)?.removeView(loadView)
        callback()
    }
}
