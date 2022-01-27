package com.example.fitanimal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.properties.Delegates

public const val ERROR = -1
public const val SMALL_FOOD = 0
public const val LARGE_FOOD = 1
public const val PREMIUM_FOOD = 2
public const val PREMIUM_FOOD_XL = 3

public var smallFoodQuantity by Delegates.notNull<Int>()
public var largeFoodQuantity by Delegates.notNull<Int>()
public var premiumFoodQuantity by Delegates.notNull<Int>()
public var premiumFoodXLQuantity by Delegates.notNull<Int>()

class FoodPopup : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_popup)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        loadData()

        findViewById<TextView>(R.id.quantitySmallFood).text = smallFoodQuantity.toString()
        findViewById<TextView>(R.id.quantityLargeFood).text = largeFoodQuantity.toString()
        findViewById<TextView>(R.id.quantityPremiumFood).text = premiumFoodQuantity.toString()
        findViewById<TextView>(R.id.quantityPremiumXLFood).text = premiumFoodXLQuantity.toString()

        window.setLayout((width * 0.9).toInt(), (height * 0.9).toInt())
    }

    override fun onResume() {
        super.onResume()

        loadData()

        findViewById<TextView>(R.id.quantitySmallFood).text = smallFoodQuantity.toString()
        findViewById<TextView>(R.id.quantityLargeFood).text = largeFoodQuantity.toString()
        findViewById<TextView>(R.id.quantityPremiumFood).text = premiumFoodQuantity.toString()
        findViewById<TextView>(R.id.quantityPremiumXLFood).text = premiumFoodXLQuantity.toString()
    }

    fun openOptions(view: View) {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (view.id == R.id.buySmallFood) {
            editor.putInt("foodChoice", SMALL_FOOD)
            editor.apply()
        } else if (view.id == R.id.buyLargeFood) {
            editor.putInt("foodChoice", LARGE_FOOD)
            editor.apply()
        } else if (view.id == R.id.buyPremiumFood) {
            editor.putInt("foodChoice", PREMIUM_FOOD)
            editor.apply()
        } else if (view.id == R.id.buyPremiumXLFood) {
            editor.putInt("foodChoice", PREMIUM_FOOD_XL)
            editor.apply()
        } else {
            editor.putInt("foodChoice", ERROR)
            editor.apply()
        }

        val intentOptions = Intent(this, FoodOptionsActivity::class.java)
        startActivity(intentOptions)
    }

    fun refillBowl(view: View) {
        var quantity: Int = 0
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var isBowlEmpty = sharedPreferences.getBoolean("isBowlEmpty", false)
        if (view.id == R.id.refillSmallFood) {
            quantity = smallFoodQuantity
        } else if (view.id == R.id.refillLargeFood) {
            quantity = largeFoodQuantity
        } else if (view.id == R.id.refillPremiumFood) {
            quantity = premiumFoodQuantity
        } else if (view.id == R.id.refillPremiumXLFood) {
            quantity = premiumFoodXLQuantity
        }
        if (quantity > 0) {
            if (isBowlEmpty) {
                quantity--
                isBowlEmpty = true
                editor.putBoolean("isBowlEmpty", isBowlEmpty).apply()
            } else {
                Toast.makeText(this, "Bowl is not empty", Toast.LENGTH_LONG).show()
            }
        } else {
            quantity = 0
            Toast.makeText(this, "You don't have enough food to refill the bowl", Toast.LENGTH_LONG).show()
        }
        if (view.id == R.id.refillSmallFood) {
            smallFoodQuantity = quantity
            findViewById<TextView>(R.id.quantitySmallFood).text = smallFoodQuantity.toString()
            editor.putInt("smallFoodQuantity", smallFoodQuantity).apply()
        } else if (view.id == R.id.refillLargeFood) {
            largeFoodQuantity = quantity
            findViewById<TextView>(R.id.quantityLargeFood).text = largeFoodQuantity.toString()
            editor.putInt("largeFoodQuantity", largeFoodQuantity).apply()
        } else if (view.id == R.id.refillPremiumFood) {
            premiumFoodQuantity = quantity
            findViewById<TextView>(R.id.quantityPremiumFood).text = premiumFoodQuantity.toString()
            editor.putInt("premiumFoodQuantity", premiumFoodQuantity).apply()
        } else if (view.id == R.id.refillPremiumXLFood) {
            premiumFoodXLQuantity = quantity
            findViewById<TextView>(R.id.quantityPremiumXLFood).text = premiumFoodXLQuantity.toString()
            editor.putInt("premiumXLFoodQuantity", premiumFoodXLQuantity).apply()
        }
    }

    @SuppressLint("LongLogTag")
    fun loadData() {
        // In this function we will retrieve data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedSmallFoodQuantity     = sharedPreferences.getInt("smallFoodQuantity", 3)
        val savedLargeFoodQuantity     = sharedPreferences.getInt("largeFoodQuantity", 0)
        val savedPremiumFoodQuantity   = sharedPreferences.getInt("premiumFoodQuantity", 0)
        val savedPremiumXLFoodQuantity = sharedPreferences.getInt("premiumXLFoodQuantity", 0)
        // Log.d is used for debugging purposes

        smallFoodQuantity = savedSmallFoodQuantity
        largeFoodQuantity = savedLargeFoodQuantity
        premiumFoodQuantity = savedPremiumFoodQuantity
        premiumFoodXLQuantity = savedPremiumXLFoodQuantity
    }

    fun saveData() {

        // Shared Preferences will allow us to save
        // and retrieve data in the form of key,value pair.
        // In this function we will save data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putInt("smallFoodQuantity", smallFoodQuantity)
        editor.putInt("largeFoodQuantity", largeFoodQuantity)
        editor.putInt("premiumFoodQuantity", premiumFoodQuantity)
        editor.putInt("premiumXLFoodQuantity", premiumFoodXLQuantity)
        editor.apply()
    }
}