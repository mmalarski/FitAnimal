package com.example.fitanimal

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast

var BLUE_BALL = 0
var GREEN_BALL = 1
var RED_BALL = 2

var RED_BED = 0
var BLUE_BED = 1
var YELLOW_BED = 2

var BULLDOG = 0
var CORGI = 1
var OTHER_DOG = 2

class WardrobePopup : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wardrobe_popup)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * 0.9).toInt(), (height * 0.9).toInt())
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val coins = sharedPreferences.getInt("coins", 0)
        findViewById<ImageView>(R.id.padlock).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeBowl", GREEN_BALL).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            true
        }
        findViewById<ImageView>(R.id.padlock2).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeBowl", RED_BALL).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock3).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeBed", BLUE_BED).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock4).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeBed", YELLOW_BED).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock5).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeDog", CORGI).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            it.visibility = View.GONE
            true
        }
        findViewById<ImageView>(R.id.padlock6).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeDog", OTHER_DOG).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            it.visibility = View.GONE
            true
        }
    }

    fun displayHint(view: View) {
        Toast.makeText(this,"Long tap to purchase - 1000 coins", Toast.LENGTH_SHORT).show()
    }
}
