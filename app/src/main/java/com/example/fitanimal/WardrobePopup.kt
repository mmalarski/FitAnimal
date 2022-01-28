package com.example.fitanimal

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast


class WardrobePopup : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wardrobe_popup)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * 0.9).toInt(), (height * 0.9).toInt())
        findViewById<ImageView>(R.id.padlock).setOnLongClickListener {
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock2).setOnLongClickListener {
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock3).setOnLongClickListener {
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock4).setOnLongClickListener {
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock5).setOnLongClickListener {
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock6).setOnLongClickListener {
            it.visibility = View.GONE
            true
        }
    }

    fun displayHint(view: View) {
        Toast.makeText(this,"Long tap to purchase - 1000 coins", Toast.LENGTH_LONG).show()
    }
}
