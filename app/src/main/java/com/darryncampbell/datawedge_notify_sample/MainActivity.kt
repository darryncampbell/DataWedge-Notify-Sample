package com.darryncampbell.datawedge_notify_sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

//  https://techdocs.zebra.com/datawedge/latest/guide/api/notify/

private const val DATAWEDGE_ACTION = "com.symbol.datawedge.api.ACTION"
private const val NOTIFY_EXTRA = "com.symbol.datawedge.api.notification.NOTIFY"
private const val NOTIFICATION_CONFIG = "NOTIFICATION_CONFIG"
private const val ACTION_RESULT_DATAWEDGE_FROM_6_2 = "com.symbol.datawedge.api.RESULT_ACTION"
private const val DEVICE_IDENTIFIER = "DEVICE_IDENTIFIER"
private const val NOTIFICATION_SETTINGS = "NOTIFICATION_SETTINGS"
private const val EXTRA_SEND_RESULT = "SEND_RESULT"
private const val EXTRA_RESULT = "RESULT"
private const val EXTRA_RESULT_INFO = "RESULT_INFO"
private const val EXTRA_COMMAND = "COMMAND"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notifyOptions = resources.getStringArray(R.array.NotifyOptions)
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        val spinner1Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, notifyOptions)
        spinner1.adapter = spinner1Adapter
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val spinner2Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, notifyOptions)
        spinner2.adapter = spinner2Adapter
        val spinner3 = findViewById<Spinner>(R.id.spinner3)
        val spinner3Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, notifyOptions)
        spinner3.adapter = spinner3Adapter
        val spinner4 = findViewById<Spinner>(R.id.spinner4)
        val spinner4Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, notifyOptions)
        spinner4.adapter = spinner4Adapter
        val spinner5 = findViewById<Spinner>(R.id.spinner5)
        val spinner5Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, notifyOptions)
        spinner5.adapter = spinner5Adapter
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(ACTION_RESULT_DATAWEDGE_FROM_6_2) //  DW 6.3 for notifications
        filter.addCategory(Intent.CATEGORY_DEFAULT) //  NOTE: this IS REQUIRED for DW6.2 and up!
        registerReceiver(myBroadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myBroadcastReceiver);
    }

    fun notifyClick(view: View) {
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val spinner3 = findViewById<Spinner>(R.id.spinner3)
        val spinner4 = findViewById<Spinner>(R.id.spinner4)
        val spinner5 = findViewById<Spinner>(R.id.spinner5)
        val notifyParam1 = convertSpinnerValueToNotifyParam(spinner1.selectedItemPosition)
        val notifyParam2 = convertSpinnerValueToNotifyParam(spinner2.selectedItemPosition)
        val notifyParam3 = convertSpinnerValueToNotifyParam(spinner3.selectedItemPosition)
        val notifyParam4 = convertSpinnerValueToNotifyParam(spinner4.selectedItemPosition)
        val notifyParam5 = convertSpinnerValueToNotifyParam(spinner5.selectedItemPosition)
        var arraySize = 0;
        var count = 0;
        if (notifyParam1 >= 0)
            arraySize++;
        if (notifyParam2 >= 0)
            arraySize++;
        if (notifyParam3 >= 0)
            arraySize++;
        if (notifyParam4 >= 0)
            arraySize++;
        if (notifyParam5 >= 0)
            arraySize++;

        if (arraySize > 0)
        {
            val notifyParams = IntArray(arraySize)
            if (notifyParam1 >= 0)
            {
                notifyParams[count] = notifyParam1;
                count++;
            }
            if (notifyParam2 >= 0)
            {
                notifyParams[count] = notifyParam2;
                count++;
            }
            if (notifyParam3 >= 0)
            {
                notifyParams[count] = notifyParam3;
                count++;
            }
            if (notifyParam4 >= 0)
            {
                notifyParams[count] = notifyParam4;
                count++;
            }
            if (notifyParam5 >= 0)
            {
                notifyParams[count] = notifyParam5;
                count++;
            }

            notify(notifyParams)
        }
    }

    fun convertSpinnerValueToNotifyParam(spinnerValue: Int): Int {
        when(spinnerValue) {
            1 -> return 43
            2 -> return 42
            3 -> return 47
            4 -> return 48
            5 -> return 0
            6 -> return 1
            7 -> return 2
            8 -> return 3
            9 -> return 4
            10 -> return 5
            11 -> return 6
            12 -> return 7
            13 -> return 8
            14 -> return 9
            15 -> return 10
            16 -> return 11
            17 -> return 12
            18 -> return 13
            19 -> return 14
            20 -> return 15
            21 -> return 16
            22 -> return 17
            23 -> return 18
            24 -> return 19
            25 -> return 20
            26 -> return 21
            27 -> return 22
            28 -> return 23
            29 -> return 24
            30 -> return 25
            31 -> return 26
            else -> return -1
        }
    }

    fun notify(notifyParams: IntArray)
    {
        val editDeviceId = findViewById<EditText>(R.id.editDeviceId)
        val deviceId: String = editDeviceId.text.toString()
        val i = Intent()
        val bundleNotify = Bundle()
        val bundleNotificationConfig = Bundle()
        i.action = DATAWEDGE_ACTION;

        bundleNotificationConfig.putString(DEVICE_IDENTIFIER, deviceId)
        bundleNotificationConfig.putIntArray(NOTIFICATION_SETTINGS, notifyParams)
        //bundleNotificationConfig.putIntArray("NOTIFICATION_SETTINGS", intArrayOf(17, 23, 8, 43))
        bundleNotify.putBundle(NOTIFICATION_CONFIG, bundleNotificationConfig)
        i.putExtra(NOTIFY_EXTRA, bundleNotify)
        i.putExtra(EXTRA_SEND_RESULT, "true")
        this.sendBroadcast(i)

    }

    private fun outputResult(result: String?, info: String)
    {
        val sdf = SimpleDateFormat("hh:mm:ss")
        val currentDate = sdf.format(Date())

        val resultsUi = findViewById<View>(R.id.txtIntentResultCode) as TextView
        resultsUi.text = "Time: $currentDate\n$result  $info"
    }

    private val myBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (action == ACTION_RESULT_DATAWEDGE_FROM_6_2) {
                //  Process any result codes
                if (intent.hasExtra(EXTRA_RESULT)) {
                    if (intent.hasExtra(EXTRA_COMMAND)) {
                        val result = intent.getStringExtra(EXTRA_RESULT)
                        val command = intent.getStringExtra(EXTRA_COMMAND)
                        var info = ""
                        if (intent.hasExtra(EXTRA_RESULT_INFO)) {
                            val result_info = intent.getBundleExtra(EXTRA_RESULT_INFO)
                            val result_code = result_info!!.getString("RESULT_CODE")
                            if (result_code != null) info = result_code
                        }
                        outputResult(result, info);
                    }
                }
            }
        }
    }

}