package com.example.fitanimal

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import android.util.Log

import android.app.Activity
import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.KeyEvent

import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener


class BluetoothFragment : Fragment() {

    private val TAG = "BluetoothFragment"

    // Intent request codes
    private val REQUEST_CONNECT_DEVICE_SECURE = 1
    private val REQUEST_CONNECT_DEVICE_INSECURE = 2
    private val REQUEST_ENABLE_BT = 3

    /**
     * Name of the connected device
     */
    private var mConnectedDeviceName: String? = null

    /**
     * Array adapter for the conversation thread
     */
    private var mConversationArrayAdapter: ArrayAdapter<String>? = null

    /**
     * String buffer for outgoing messages
     */
    private var mOutStringBuffer: StringBuffer? = null

    /**
     * Local Bluetooth adapter
     */
    private var mBluetoothAdapter: BluetoothAdapter? = null

    /**
     * Member object for the chat services
     */
    private var mService: BluetoothService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // If the adapter is null, then Bluetooth is not supported
        val activity = activity
        if (mBluetoothAdapter == null && activity != null) {
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show()
            activity.finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mBluetoothAdapter == null) {
            return
        }
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
            // Otherwise, setup the chat session
        } else if (mService == null) {
            setupChat();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mService?.stop()
    }

    override fun onResume() {
        super.onResume()

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mService!!.getState() === BluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                mService!!.start()
            }
        }
    }

    private fun setupChat() {
        Log.d(TAG, "setupChat()")

        val activity = activity ?: return

        // Initialize the BluetoothChatService to perform bluetooth connections
        mService = BluetoothService(activity, mHandler)

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = StringBuffer()
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private fun sendMessage(message: String) {
        // Check that we're actually connected before trying anything
        if (mService?.getState()  != mService?.companion?.STATE_CONNECTED) {
            //Toast.makeText(activity, R.string.not_connected, Toast.LENGTH_SHORT).show()
            return
        }

        // Check that there's actually something to send
        if (message.length > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            val send = message.toByteArray()
            mService?.write(send)

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer!!.setLength(0)
           // mOutEditText.setText(mOutStringBuffer)
        }
    }

    /**
     * The action listener for the EditText widget, to listen for the return key
     */
    private val mWriteListener =
        OnEditorActionListener { view, actionId, event -> // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.action === KeyEvent.ACTION_UP) {
                val message = view.text.toString()
                sendMessage(message)
            }
            true
        }

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private fun setStatus(resId: Int) {
        val activity = activity ?: return
        val actionBar = activity.actionBar ?: return
        actionBar.setSubtitle(resId)
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private fun setStatus(subTitle: CharSequence) {
        val activity = activity ?: return
        val actionBar = activity.actionBar ?: return
        actionBar.subtitle = subTitle
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val activity = activity
            when (msg.what) {
                Constants.MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                    BluetoothService.STATE_CONNECTED -> {
                        setStatus(getString(R.string.title_connected_to, mConnectedDeviceName))
                        mConversationArrayAdapter!!.clear()
                    }
                    BluetoothService.STATE_CONNECTING -> setStatus(R.string.title_connecting)
                    BluetoothService.STATE_LISTEN, BluetoothService.STATE_NONE -> setStatus(
                        R.string.title_not_connected
                    )
                }
                Constants.MESSAGE_WRITE -> {
                    val writeBuf = msg.obj as ByteArray
                    // construct a string from the buffer
                    val writeMessage = String(writeBuf)
                    mConversationArrayAdapter!!.add("Me:  $writeMessage")
                }
                Constants.MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    // construct a string from the valid bytes in the buffer
                    val readMessage = String(readBuf, 0, msg.arg1)
                    mConversationArrayAdapter!!.add("$mConnectedDeviceName:  $readMessage")
                }
                Constants.MESSAGE_DEVICE_NAME -> {
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME)
                    if (null != activity) {
                        Toast.makeText(
                            activity, "Connected to "
                                    + mConnectedDeviceName, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Constants.MESSAGE_TOAST -> if (null != activity) {
                    Toast.makeText(
                        activity, msg.getData().getString(Constants.TOAST),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_CONNECT_DEVICE_SECURE ->                 // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true)
                }
            REQUEST_CONNECT_DEVICE_INSECURE ->                 // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false)
                }
            REQUEST_ENABLE_BT ->                 // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat()
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled")
                    val activity = activity
                    if (activity != null) {
                        Toast.makeText(
                            activity, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT
                        ).show()
                        activity.finish()
                    }
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An [Intent] with [DeviceListActivity.EXTRA_DEVICE_ADDRESS] extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */

    private fun connectDevice(data: Intent, secure: Boolean) {
        // Get the device MAC addressDeviceListActivity
        val extras = data.extras ?: return
        val address = extras.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS)
        // Get the BluetoothDevice object
        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        // Attempt to connect to the device
        mService?.connect(device, secure)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.bluetooth_chat, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.getItemId()) {
//            R.id.secure_connect_scan -> {
//
//                // Launch the DeviceListActivity to see devices and do scan
//                val serverIntent = Intent(activity, DeviceListActivity::class.java)
//                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE)
//                return true
//            }
//            R.id.insecure_connect_scan -> {
//
//                // Launch the DeviceListActivity to see devices and do scan
//                val serverIntent = Intent(activity, DeviceListActivity::class.java)
//                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE)
//                return true
//            }
//            R.id.discoverable -> {
//
//                // Ensure this device is discoverable by others
//                ensureDiscoverable()
//                return true
//            }
//        }
//        return false
//    }

}