package com.example.fitanimal

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class BluetoothScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetoothscreen_main)
    }

    private val REQUEST_CONNECT_DEVICE_SECURE = 1
    private val REQUEST_CONNECT_DEVICE_INSECURE = 2
    private val REQUEST_ENABLE_BT = 3

    fun goToDeviceList(view: View) {

        // Launch the DeviceListActivity to see devices and do scan
        val intent = Intent(this, DeviceListActivity::class.java)
        startActivityForResult(
            intent,
            REQUEST_CONNECT_DEVICE_SECURE
        )
    }

    fun gotoIncesure(view: View) {

        // Launch the DeviceListActivity to see devices and do scan
        val intent = Intent(this, DeviceListActivity::class.java)
        startActivityForResult(
            intent,
            REQUEST_CONNECT_DEVICE_INSECURE
        )
    }
//
//    fun makeDiscoverable(view: View) {
//
//        val intent = Intent(this, SecureConnect::class.java)
//        ContextCompat.startActivity(intent);
//    }
}