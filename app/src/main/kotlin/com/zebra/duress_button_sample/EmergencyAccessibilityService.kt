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

//best doc on shared audio capturing: https://developer.android.com/guide/topics/media/platform/sharing-audio-input
class EmergencyAccessibilityService : AccessibilityService() {

    val TAG = "EmergencyAccessibilityService"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {
    }

    companion object{
         //val mm =MicManager( /*this as Context*/)  //EXCP HERE Companion cannot be cast to android.content.Context
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        Log.d(TAG, "accessib service is connected")

        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("DURESS BUTTON AVAILABLE")
            .setContentText("Long press the PTT button to trigger an emergency alert")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(2002, notification)
        Log.d(TAG, "accessib startForeground just called")

    }

    var timerRemoteAlertMonitor = Timer()
        .schedule(timerTask {
            Log.d(TAG, "timerRemoteAlertMonitor periodic callback invoked")
            try {
                val cxnt48Emergency = URL("https://cxnt48.com/emergency?get").readText()
                Log.i(TAG, "cxnt48Emergency: " + cxnt48Emergency)

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

    lateinit var toneTimer: Timer
    private fun triggerEmergencyAlarm() {

        if(isAlarmOn) {
            Log.d(TAG, "triggerEmergencyAlarm starting Tone")
            try {
                toneTimer = Timer()
                toneTimer.schedule(timerTask {
                    tg.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 200);
                }, 0, 1000)

                //DISPLAY A SYSTEM ALERT WINDOW
                startActivity(Intent(this, EmergencyOverlayActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })

            } catch (e: Exception) { }
        }
        else{
            Log.d(TAG, "triggerEmergencyAlarm cancel Tone")
            try{
                toneTimer.cancel()
            } catch (e: Exception) { }
        }
    }


    //BOOT-AWARE FGS
    private val CHANNEL_ID = "DURESS-FGS"

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "NotificatonAccessiblity Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }
}