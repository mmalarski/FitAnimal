package com.example.fitanimal

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics


class WardrobePopup : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wardrobe_popup)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        getWindow().setLayout((width * 0.8).toInt(), (height * 0.6).toInt())
    }
}
