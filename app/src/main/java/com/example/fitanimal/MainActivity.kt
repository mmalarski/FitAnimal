package com.example.fitanimal

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager

const val EXTRA_MESSAGE = "com.example.fitanimal.MESSAGE"

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager;
    private lateinit var myadapter: SlideAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        myadapter = SlideAdapter(this);
        viewPager.adapter = myadapter;
    }


//    fun sendMessage(view: View) {
//
//        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
//        val message = editText.text.toString()
//        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
//            putExtra(EXTRA_MESSAGE, message)
//        }
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

    fun goToSettings(view: View) {

        val intentSettings = Intent(this, SettingActivity::class.java)
        startActivity(intentSettings);
    }

    fun openWardrobe(view: View) {
        val intentWardrobe = Intent(this, WardrobePopup::class.java)
        startActivity(intentWardrobe)
    }
}