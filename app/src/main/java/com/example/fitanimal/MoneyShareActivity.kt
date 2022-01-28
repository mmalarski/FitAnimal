package com.example.fitanimal

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MoneyShareActivity : Activity() {

    var amount : Int = 0
    var coins : Int = 0
    var sharedCoins : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.money_share_activity)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * 0.6).toInt(), (height * 0.5).toInt())

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        coins = sharedPreferences.getInt("coins", 0)
        findViewById<TextView>(R.id.playersMoney).text = ("your money\n $coins")
        findViewById<TextView>(R.id.amountTextView).text = amount.toString()

        findViewById<ImageButton>(R.id.minusButtonMoney).setImageResource(R.drawable.path10)
        findViewById<ImageButton>(R.id.plusButtonMoney).setImageResource(R.drawable.text835)


        val editor = sharedPreferences.edit()
        editor.putInt("sharedCoins", sharedCoins)
        editor.apply()
    }


    fun increase(view: View) {
        if(amount < coins)
            amount += 50
        findViewById<TextView>(R.id.amountTextView).text = amount.toString()
    }

    fun decrease(view: View) {
        if(amount >= 50)
            amount -= 50
        findViewById<TextView>(R.id.amountTextView).text = amount.toString()
    }

    fun share(view : View) {
        if(amount <= coins) {
            val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            coins -= amount
            editor.putInt("coins", coins)
            editor.apply()
            findViewById<TextView>(R.id.playersMoney).text = ("your money\n $coins")
            editor.putInt("sharedCoins", amount)
            editor.apply()
        }
    }
}