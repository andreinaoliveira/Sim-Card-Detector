package com.example.simcard


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telephony.TelephonyManager
import android.util.Log
import kotlin.concurrent.thread
import android.telephony.VisualVoicemailService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.io.File
import java.io.FileInputStream


const val TAG = "Time_06"
private val CHANNEL_ID = "Time06Notify"
private val notificationId = 101


class SimCardService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    override fun onCreate(){
        showLog("Oncreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showLog("OnStartCommand")
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val loop = true

        val runnable = Runnable {
            while (loop){
                var simState = telephonyManager.observerData()
                createNotificationChannel()
                if (simState == "Inserted"){
                    sendNotificationInsert()
                }
                if (simState == "Removed"){
                    sendNotificationRemove()
                }
            }
        }
        val thread =  Thread(runnable)
        thread.start()
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        showLog("OnDestroy")
        super.onDestroy()
    }

    fun showLog(message : String){
        Log.d(TAG, message)
    }

    // NOTIFICAÇÃO EM TELA
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    // ENVIANDO NOTIFICAÇÃO EM TELA SIM CARD INSERIDO
    private fun sendNotificationInsert() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("SIM Card Identificado")
            .setContentText("Veja detalhes do seu novo SIM")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
    // ENVIANDO NOTIFICAÇÃO EM TELA SIM CARD REMOVIDO
    private fun sendNotificationRemove() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("SIM Card Removido")
            .setContentText("Seu SIM Card foi ejetado.")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

}