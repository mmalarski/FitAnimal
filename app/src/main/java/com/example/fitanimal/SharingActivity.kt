package com.example.fitanimal

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.budiyev.android.codescanner.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Exception
import java.util.*
import kotlin.properties.Delegates

private const val CAMERA_REQUEST_CODE = 101

class SharingActivity : AppCompatActivity() {

    private lateinit var bitMatrix: BitMatrix
    private lateinit var bitMap: Bitmap
    private lateinit var codeScanner: CodeScanner
    private lateinit var textView: TextView
    private lateinit var scanner_view: CodeScannerView
    private lateinit var imageView: ImageView
    private lateinit var multiFormatWriter: MultiFormatWriter
    private lateinit var barcodeEncoder: BarcodeEncoder
    private var coins by Delegates.notNull<Int>();
    private var sharedCoins by Delegates.notNull<Int>();

    @SuppressLint("LongLogTag", "HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)
        textView = findViewById<TextView>(R.id.textView2)
        scanner_view = findViewById<CodeScannerView>(R.id.scanner_view)
        imageView = findViewById<ImageView>(R.id.imageViewSharing)

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        coins = sharedPreferences.getInt("coins", 0)
        sharedCoins = sharedPreferences.getInt("sharedCoins", 0)

        changeCode(sharedCoins.toString())

        setupPermission()
        codeScanner()
    }

    private fun changeCode(string : String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(
                string,
                BarcodeFormat.QR_CODE,
                500,
                500
            )
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {

                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialisation error: ${it.message}")
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()

            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        sharedCoins = sharedPreferences.getInt("sharedCoins", 0)
        changeCode(sharedCoins.toString())
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermission() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need the camera permission to be able to use this app!", Toast.LENGTH_SHORT).show()
                } else {
                    //successful
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null) {
            try {
                val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                coins = sharedPreferences.getInt("coins", 0)
                var newAmount : Int = result.contents.toInt() + coins
                val editor = sharedPreferences.edit()
                editor.putInt("coins", newAmount)
                editor.apply()
                Toast.makeText(this, "Received " + result.contents.toString() + " coins", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun shareButton(view : View) {
        if(coins > 0) {
            val intentShare = Intent(this, MoneyShareActivity::class.java)
            startActivity(intentShare)
        } else {
            Toast.makeText(this, "Not enough money to share", Toast.LENGTH_SHORT).show()
        }

    }
}