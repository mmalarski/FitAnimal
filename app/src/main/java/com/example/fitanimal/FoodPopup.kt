package com.example.fitanimal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View

public const val ERROR = -1
public const val SMALL_FOOD = 0
public const val LARGE_FOOD = 1
public const val PREMIUM_FOOD = 2
public const val PREMIUM_FOOD_XL = 3


class FoodPopup : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_popup)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * 0.8).toInt(), (height * 0.6).toInt())
    }

    fun openOptions(view: View) {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (view.id == R.id.smallFoodText || view.id == R.id.smallFoodImage) {
            editor.putInt("foodChoice", SMALL_FOOD)
            editor.apply()
        } else if (view.id == R.id.largeFoodText || view.id == R.id.largeFoodImage) {
            editor.putInt("foodChoice", LARGE_FOOD)
            editor.apply()
        } else if (view.id == R.id.premiumFoodText || view.id == R.id.premiumFoodImage) {
            editor.putInt("foodChoice", PREMIUM_FOOD)
            editor.apply()
        } else if (view.id == R.id.largeFoodText || view.id == R.id.largeFoodImage) {
            editor.putInt("foodChoice", PREMIUM_FOOD_XL)
            editor.apply()
        } else {
            editor.putInt("foodChoice", ERROR)
            editor.apply()
        }

        val intentOptions = Intent(this, FoodOptionsActivity::class.java)
        startActivity(intentOptions)
    }
}