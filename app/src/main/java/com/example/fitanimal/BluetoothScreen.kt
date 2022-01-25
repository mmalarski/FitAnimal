package com.example.fitanimal

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class BluetoothScreen : AppCompatActivity() {
    private val btFrag: BluetoothFragment = BluetoothFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetoothscreen_main)

    }

    private val REQUEST_CONNECT_DEVICE_SECURE = 1
    private val REQUEST_CONNECT_DEVICE_INSECURE = 2
    private val REQUEST_ENABLE_BT = 3
    private var option: Int = 0


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (option) {
            REQUEST_CONNECT_DEVICE_SECURE ->                 // When DeviceListActivity returns with a device to connect
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        btFrag.connectDevice(data, true)
                    }
                }
            REQUEST_CONNECT_DEVICE_INSECURE ->                 // When DeviceListActivity returns with a device to connect
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        btFrag.connectDevice(data, true)
                    }
                }
            REQUEST_ENABLE_BT ->                 // When the request to enable Bluetooth returns
                if (result.resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    Log.d("HEJKA", "setup.chat()")
//                    btFrag.setupChat()
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d("TAG", "BT not enabled")
                }
        }
    }

    fun goToDeviceList(view: View) {
        option = REQUEST_CONNECT_DEVICE_SECURE
        // Launch the DeviceListActivity to see devices and do scan
        val intent = Intent(this, DeviceListActivity::class.java)
        resultLauncher.launch(intent)
    }

    fun gotoIncesure(view: View) {
        option = REQUEST_CONNECT_DEVICE_INSECURE
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