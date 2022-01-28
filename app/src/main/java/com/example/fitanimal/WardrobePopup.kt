package com.example.fitanimal

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast

var BLUE_BALL = R.drawable.ball2
var GREEN_BALL = R.drawable.ball_green
var RED_BALL = R.drawable.ball_red

var RED_BED = R.drawable.bed2
var BLUE_BED = R.drawable.bed_blue
var YELLOW_BED = R.drawable.bed_yellow

var BULLDOG = R.drawable.bulldog
var CORGI = R.drawable.corgi
var OTHER_DOG = R.drawable.dog_1

class WardrobePopup : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wardrobe_popup)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val refresh = false
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
            true
        }
        findViewById<ImageView>(R.id.padlock3).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeBed", BLUE_BED).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            true
        }
        findViewById<ImageView>(R.id.padlock4).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeBed", YELLOW_BED).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            true
        }
        findViewById<ImageView>(R.id.padlock5).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeDog", CORGI).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            true
        }
        findViewById<ImageView>(R.id.padlock6).setOnLongClickListener {
            if (coins > 1000) {
                it.visibility = View.GONE
                sharedPreferences.edit().putInt("coins", coins - 1000).apply()
                sharedPreferences.edit().putInt("activeDog", OTHER_DOG).apply()
            } else { Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show() }
            true
        }
        findViewById<ImageView>(R.id.imageView21).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeDog", BULLDOG).apply()
            Toast.makeText(this, "Changed the skin", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView22).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeDog", CORGI).apply()
            Toast.makeText(this, "Changed the skin", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView23).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeDog", OTHER_DOG).apply()
            Toast.makeText(this, "Changed the skin", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView15).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeBowl", BLUE_BALL).apply()
            Toast.makeText(this, "Changed the ball", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView16).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeBowl", GREEN_BALL).apply()
            Toast.makeText(this, "Changed the ball", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView17).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeBowl", RED_BALL).apply()
            Toast.makeText(this, "Changed the ball", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView18).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeBed", RED_BED).apply()
            Toast.makeText(this, "Changed the bed", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView19).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeBed", BLUE_BED).apply()
            Toast.makeText(this, "Changed the bed", Toast.LENGTH_SHORT).show()
            true
        }
        findViewById<ImageView>(R.id.imageView20).setOnLongClickListener {
            sharedPreferences.edit().putInt("activeBed", YELLOW_BED).apply()
            Toast.makeText(this, "Changed the bed", Toast.LENGTH_SHORT).show()
            true
        }




    }

    fun displayHint(view: View) {
        Toast.makeText(this,"Long tap to purchase - 1000 coins", Toast.LENGTH_SHORT).show()
    }
}
