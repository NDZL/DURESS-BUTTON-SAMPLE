package com.zebra.duress_button_sample

import android.accessibilityservice.AccessibilityService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import androidx.core.app.NotificationCompat
import java.net.URL
import java.util.Timer
import kotlin.concurrent.timerTask

class EmergencyAccessibilityService : AccessibilityService() {

    val TAG = "EmergencyAccessibilityService"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {
    }

    companion object{
    }


    override fun onServiceConnected() {
        super.onServiceConnected()

    }

    var remoteAlertSet =false
    var remotelySetAlertType ="N/A"

    var timerRemoteAlertMonitor = Timer()
        .schedule(timerTask {
            Log.d(TAG, "timerRemoteAlertMonitor periodic callback invoked")
            try {
                val cxnt48Emergency = URL("https://cxnt48.com/emergency?get").readText()
                Log.i(TAG, "cxnt48Emergency: " + cxnt48Emergency)
                val webEmergencyBundle = cxnt48Emergency.split(";")
                remotelySetAlertType = webEmergencyBundle[0]
                val remotelySetAlertEpochMillis = webEmergencyBundle[1].toLong(10)
                val currentEpochMillis = System.currentTimeMillis()
                if( (!remoteAlertSet) && (currentEpochMillis-remotelySetAlertEpochMillis <20*1000) && remotelySetAlertType!="NO_EMERGENCY") {
                    //startAct(remotelySetAlertType)
                    startActivity(Intent(baseContext, EmergencyOverlayActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra("emergency_type", remotelySetAlertType);
                    })
                    remoteAlertSet = true
                }
                else {
                    remoteAlertSet = false
                }

            } catch (e: Exception) {
                Log.d(TAG, "timerRemoteAlertMonitor periodic callback EXCEPTION: " + e.toString())
            }
        }, 1000, 2000)
        .also { Log.d(TAG, "timerRemoteAlertMonitor scheduled") }


    var isAlarmOn = false
    val tg = ToneGenerator(AudioManager.STREAM_ALARM, 100)
    val timerLongPress = object: CountDownTimer(1500, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            isAlarmOn = !isAlarmOn
            Log.d(TAG, "timerLongPress finished")
            triggerEmergencyAlarm()
        }
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        Log.d(TAG, "accessib service key pressed " + event!!.keyCode)
        if( (event!!.keyCode==104 || event!!.keyCode==26 || event!!.keyCode==10036  ) &&  event!!.action== KeyEvent.ACTION_DOWN){
            try {
                timerLongPress.start()
            } catch (e: Exception) { }
        }
        else if(  (event!!.keyCode==104 || event!!.keyCode==26 || event!!.keyCode==10036  ) && event!!.action== KeyEvent.ACTION_UP){
            try {
                timerLongPress.cancel()
            } catch (e: Exception) { }
        }

        return super.onKeyEvent(event)
    }

    private fun triggerEmergencyAlarm() {

        if(isAlarmOn) {
            Log.d(TAG, "triggerEmergencyAlarm starting Tone")
            try {
                    startActivity(Intent(baseContext, EmergencyOverlayActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra("emergency_type", "USER INITIATED ALARM");
                    })

            } catch (e: Exception) { }
        }
        else{
            Log.d(TAG, "triggerEmergencyAlarm cancel Tone")
            try{

            } catch (e: Exception) { }
        }
    }

}