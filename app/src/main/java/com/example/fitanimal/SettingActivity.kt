package com.example.fitanimal

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.SubMenu
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class SettingActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val ib = findViewById<ImageButton>(R.id.sound)
        if (isMyServiceRunning(SoundService::class.java)) {
            ib.setImageResource(R.drawable.playing)
        } else {
           ib.setImageResource(R.drawable.muted)
        }
        //textView = findViewById(R.id.textViewChangeUsername)
    }

//    override fun onResume() {
//        super.onResume()
//        val ib = findViewById<ImageButton>(R.id.sound)
//        if (isMyServiceRunning(SoundService::class.java)) {
//            ib.setImageResource(R.drawable.muted)
//        } else {
//            ib.setImageResource(R.drawable.playing)
//        }
//    }


    fun goToBluetooth(view: View) {

        val intent = Intent(this, BluetoothScreen::class.java)
        startActivity(intent);
    }

    fun goToSharing(view: View) {

        val intent = Intent(this, SharingActivity::class.java)
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    //sound
    fun soundOnOff(view: View) {
        val ib = findViewById<ImageButton>(R.id.sound)
        if (isMyServiceRunning(SoundService::class.java)) {
            ib.setImageResource(R.drawable.muted)
            stopService(Intent(this@SettingActivity, SoundService::class.java))
        } else {
            ib.setImageResource(R.drawable.playing)
            startService(Intent(this@SettingActivity, SoundService::class.java))
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}