package com.example.fitanimal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlin.properties.Delegates

class FoodOptionsActivity : AppCompatActivity() {
    var amount: Int = 1
    var foodCoins by Delegates.notNull<Int>()
    var foodChoice by Delegates.notNull<Int>()
    var price by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_options)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        foodCoins = sharedPreferences.getInt("coins", 0)
        findViewById<TextView>(R.id.coinsTextViewFood).setText(foodCoins.toString())
        foodChoice = sharedPreferences.getInt("foodChoice", -1)
        window.setLayout((width * 0.9).toInt(), (height * 0.9).toInt())
        if (foodChoice == SMALL_FOOD) {
            findViewById<ImageView>(R.id.foodImage).setImageResource(R.drawable.dogfood2)
            price = 100
            findViewById<TextView>(R.id.foodPrice).setText(price.toString())
        }
        if (foodChoice == LARGE_FOOD) {
            findViewById<ImageView>(R.id.foodImage).setImageResource(R.drawable.dogfood3)
            price = 200
            findViewById<TextView>(R.id.foodPrice).setText(price.toString())
        }
        if (foodChoice == PREMIUM_FOOD) {
            findViewById<ImageView>(R.id.foodImage).setImageResource(R.drawable.dogfood4)
            price = 300
            findViewById<TextView>(R.id.foodPrice).setText(price.toString())
        }
        if (foodChoice == PREMIUM_FOOD_XL) {
            findViewById<ImageView>(R.id.foodImage).setImageResource(R.drawable.dogfood5)
            price = 400
            findViewById<TextView>(R.id.foodPrice).setText(price.toString())
        }
    }

    fun increase(view: View) {
        amount++
        findViewById<TextView>(R.id.amountTextView).setText("Amount: $amount")
        findViewById<TextView>(R.id.foodPrice).setText("${amount * price}")
    }

    fun decrease(view: View) {
        amount--
        if (amount < 1) {
            amount = 1
        }
        findViewById<TextView>(R.id.amountTextView).setText("Amount: $amount")
        findViewById<TextView>(R.id.foodPrice).setText("${amount * price}")
    }

    fun buy(view: View){
        if ((findViewById<TextView>(R.id.foodPrice).text).toString().toInt() <= foodCoins){
            val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            foodCoins -= (findViewById<TextView>(R.id.foodPrice).text).toString().toInt()

            if (foodChoice == SMALL_FOOD) {
                var quantity = sharedPreferences.getInt("smallFoodQuantity", -1)
                quantity += amount
                editor.putInt("smallFoodQuantity", quantity)
            }
            if (foodChoice == LARGE_FOOD) {
                var quantity = sharedPreferences.getInt("largeFoodQuantity", -1)
                quantity += amount
                editor.putInt("largeFoodQuantity", quantity)
            }
            if (foodChoice == PREMIUM_FOOD) {
                var quantity = sharedPreferences.getInt("premiumFoodQuantity", -1)
                quantity += amount
                editor.putInt("premiumFoodQuantity", quantity)
            }
            if (foodChoice == PREMIUM_FOOD_XL) {
                var quantity = sharedPreferences.getInt("premiumXLFoodQuantity", -1)
                quantity += amount
                editor.putInt("premiumXLFoodQuantity", quantity)
            }
            editor.putInt("coins", foodCoins)
            editor.apply()
            findViewById<TextView>(R.id.coinsTextViewFood).setText(foodCoins.toString())
        }
    }
}