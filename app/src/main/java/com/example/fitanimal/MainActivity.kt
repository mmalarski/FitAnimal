package com.example.fitanimal

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*
import kotlin.properties.Delegates
import android.app.ActivityManager
import android.widget.ImageButton


const val EXTRA_MESSAGE = "com.example.fitanimal.MESSAGE"

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var viewPager: ViewPager
    private lateinit var myadapter: SlideAdapter
    // we have assigned sensorManger to nullable
    private var sensorManager: SensorManager? = null
    // Creating a variable which will give the running status
    // and initially given the boolean value as false
    private var running = false

    // Creating a variable which will counts total steps
    // and it has been given the value of 0 float
    private var totalSteps = 0f

    // Creating a variable  which counts previous total
    // steps and it has also been given the value of 0 float
    private var previousTotalSteps = 0f

    //notification variables
    private val CHANNEL_ID = "channel_id_notif"
    private val notificationID = 101
    private var sendNotif = true

    private var lastTime by Delegates.notNull<Long>()
    private var deltaTime = 0

    public var hungerLevel by Delegates.notNull<Int>()
    public var energyLevel by Delegates.notNull<Int>()
    public var moodLevel by Delegates.notNull<Int>()
    public var coins by Delegates.notNull<Int>()
    public var isBowlEmpty by Delegates.notNull<Boolean>()

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewpager)
        myadapter = SlideAdapter(this)
        viewPager.adapter = myadapter
        viewPager.setCurrentItem(1);
        val currentTime: Long = Calendar.getInstance().timeInMillis
        lastTime = 0
        loadData()
        findViewById<TextView>(R.id.coinsTextViewMain).text = coins.toString()
        resetSteps()
        deltaTime = if (lastTime != 0.toLong()) {
            ((currentTime - lastTime) / 10000).toInt()
        } else {
            0
        }
        Log.d("Maincurr", "$currentTime")
        Log.d("Mainlast", "$lastTime")
        Log.d("MainDelta", "$deltaTime")
        // Adding a context of SENSOR_SERVICE aas Sensor Manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        createNotificationChannel()
        tweakBars()
    }

    override fun onPause() {
        super.onPause()
        if(sendNotif)
            sendNotification()
//        if (hungerLevel > 5) {
//            hungerLevel -= 5
//        } else {
//            hungerLevel = 0
//        }
//        findViewById<ProgressBar>(R.id.hungerBar).progress = hungerLevel
          saveData()
          tweakBars()
    }

    override fun onDestroy() {
        super.onDestroy()
        val currTime: Long = Calendar.getInstance().timeInMillis
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putLong("stopTime", currTime)
        editor.apply()

    }

    override fun onStop() {
        super.onStop()
        val currTime: Long = Calendar.getInstance().timeInMillis
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putLong("stopTime", currTime)
        editor.apply()
        Log.d("MainStop", "$currTime")

        //sound stops
        if (isMyServiceRunning(SoundService::class.java)) {
            stopService(Intent(this@MainActivity, SoundService::class.java))}
    }

    override fun onResume() {
        super.onResume()
        running = true

        // Returns the number of steps taken by the user since the last reboot while activated
        // This sensor requires permission android.permission.ACTIVITY_RECOGNITION.
        // So don't forget to add the following permission in AndroidManifest.xml present in manifest folder of the app.
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


        if (stepSensor == null) {
            // This will give a toast message to the user if there is no sensor in the device
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            // Rate suitable for the user interface
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }

        sendNotif = true
        //Log.d("MainHalo", "$deltaTime")



        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedTime = sharedPreferences.getLong("stopTime", 0)
        lastTime = savedTime
        val currentTime: Long = Calendar.getInstance().timeInMillis
        deltaTime = if (lastTime !=0.toLong()) {
            ((currentTime - lastTime) / 100000).toInt()
        } else {
            0
        }
        Log.d("Mainstop", "$lastTime")
        Log.d("Maincurrent", "$currentTime")
        Log.d("MainDelta", "$deltaTime")
        loadData()
        findViewById<TextView>(R.id.coinsTextViewMain).text = coins.toString()
        tweakBars()

        //findViewById<ProgressBar>(R.id.hungerBar).progress = hungerLevel - deltaTime
    }

    override fun onSensorChanged(event: SensorEvent?) {

        // Calling the TextView that we made in activity_main.xml
        // by the id given to that TextView
        val factory = layoutInflater
        val view: View = factory.inflate(R.layout.slide, null)
        var tvStepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)

        if (running) {
            totalSteps = event!!.values[0]

            // Current steps are calculated by taking the difference of total steps
            // and previous steps
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            Log.d("STATE", currentSteps.toString())
            // It will show the current steps to the user
            tvStepsTaken.text = "$currentSteps"
        }
    }

    fun resetSteps() {
        val factory = layoutInflater
        val view: View = factory.inflate(R.layout.slide, null)
        var tvStepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)
        tvStepsTaken.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tvStepsTaken.setOnLongClickListener {

            previousTotalSteps = totalSteps
            findViewById<ProgressBar>(R.id.hungerBar).progress = 95
            hungerLevel = 95
            coins = 15000
            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            tvStepsTaken.text = 0.toString()

            // This will save the data
            saveData()
            tweakBars()
            findViewById<TextView>(R.id.coinsTextViewMain).text = coins.toString()

            true
        }

    }

    private fun saveData() {

        // Shared Preferences will allow us to save
        // and retrieve data in the form of key,value pair.
        // In this function we will save data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.putInt("hungerLevel", hungerLevel)
        editor.putInt("energyLevel", energyLevel)
        editor.putInt("moodLevel  ", moodLevel)
        editor.putInt("coins", coins)
        editor.putBoolean("isBowlEmpty", isBowlEmpty)
        editor.apply()
    }

    private fun loadData() {

        // In this function we will retrieve data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        val savedHunger = sharedPreferences.getInt("hungerLevel", 95)
        val savedEnergy = sharedPreferences.getInt("energyLevel", 95)
        val savedMood = sharedPreferences.getInt("moodLevel", 95)
        val savedTime = sharedPreferences.getLong("stopTime", 0)
        val savedCoins = sharedPreferences.getInt("coins", 500)
        val savedIsBowlEmpty = sharedPreferences.getBoolean("isBowlEmpty", false)
        // Log.d is used for debugging purposes
        Log.d("MainActivity", "$savedNumber")

        previousTotalSteps = savedNumber
        hungerLevel = savedHunger
        moodLevel = savedMood
        energyLevel = savedEnergy
        lastTime = savedTime
        coins = savedCoins
        isBowlEmpty = savedIsBowlEmpty
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // We do not have to write anything in this function for this app
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


    //setting activity
    fun goToSettings(view: View) {
        sendNotif = false

        val intentSettings = Intent(this, SettingActivity::class.java)
        startActivity(intentSettings);
    }



    //notifications
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notif desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText;
            }
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmapLargeIcon : Bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.icon_notif)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Come back to me!")
            .setContentText("Your pet misses you")
            .setLargeIcon(bitmapLargeIcon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationID, builder.build())
        }
    }


    //pop up windows for food bowl & wardrobe
    fun openFoodBowl(view : View) {
        sendNotif = false
        val intentFood = Intent(this, FoodPopup::class.java)
        startActivity(intentFood)
    }

    fun openWardrobe(view: View) {
        sendNotif = false
        val intentWardrobe = Intent(this, WardrobePopup::class.java)
        startActivity(intentWardrobe)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("hungerLevel", hungerLevel)
        savedInstanceState.putInt("energyLevel", energyLevel)
        savedInstanceState.putInt("moodLevel  ", moodLevel)
    }

    private fun tweakBars() {
        loadData()
        hungerLevel = hungerLevel - deltaTime
        findViewById<ProgressBar>(R.id.hungerBar).progress = hungerLevel
        findViewById<ProgressBar>(R.id.energyBar).progress = energyLevel
        findViewById<ProgressBar>(R.id.moodBar).progress = moodLevel

        findViewById<ProgressBar>(R.id.hungerBar).progressTintList = ColorStateList.valueOf(
            resources.getColor(R.color.fullBar))
        findViewById<ProgressBar>(R.id.energyBar).progressTintList = ColorStateList.valueOf(
            resources.getColor(R.color.fullBar))
        findViewById<ProgressBar>(R.id.moodBar).progressTintList = ColorStateList.valueOf(
            resources.getColor(R.color.fullBar))
        if(hungerLevel < 70) {
            findViewById<ProgressBar>(R.id.hungerBar).progressTintList = ColorStateList.valueOf(
                resources.getColor(R.color.mediumBar))
        }
        if(energyLevel < 70) {
            findViewById<ProgressBar>(R.id.energyBar).progressTintList = ColorStateList.valueOf(
                resources.getColor(R.color.mediumBar))
        }
        if(moodLevel < 70) {
            findViewById<ProgressBar>(R.id.moodBar).progressTintList = ColorStateList.valueOf(
                resources.getColor(R.color.mediumBar))
        }
        if(hungerLevel < 30) {
            findViewById<ProgressBar>(R.id.hungerBar).progressTintList = ColorStateList.valueOf(
                resources.getColor(R.color.lowBar))
        }
        if(energyLevel < 30) {
            findViewById<ProgressBar>(R.id.energyBar).progressTintList = ColorStateList.valueOf(
                resources.getColor(R.color.lowBar))
        }
        if(moodLevel < 30) {
            findViewById<ProgressBar>(R.id.moodBar).progressTintList = ColorStateList.valueOf(
                resources.getColor(R.color.lowBar))
        }
        saveData()
    }



    public infix fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}