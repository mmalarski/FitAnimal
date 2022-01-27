package com.example.fitanimal

import android.app.Service
import android.content.Intent
import android.os.IBinder


import android.media.MediaPlayer
import android.widget.Toast
import androidx.annotation.Nullable


class SoundService : Service() {
    var mediaPlayer: MediaPlayer? = null
    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    //Music: www.bensound.com
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundsound)
        mediaPlayer?.setLooping(true) // Set looping
        mediaPlayer?.setVolume(100F, 100F)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return startId
    }

    override fun onStart(intent: Intent?, startId: Int) {}
    override fun onDestroy() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
    }

    override fun onLowMemory() {}
}