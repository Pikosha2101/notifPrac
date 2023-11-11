package com.example.notif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    private val NORMAL_CHANNEL = "NORMAL_CHANNEL"
    private lateinit var notificationManager : NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = resources.getString(R.string.NOT_IMPORTANT_CHANNEL_NAME)
            val channel = NotificationChannel(
                NORMAL_CHANNEL,
                name,
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = resources.getString(R.string.NOT_IMPORTANT_CHANNEL_DESCRIPTION)
            channel.enableVibration(false)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val but = findViewById<Button>(R.id.butAdd)
        val butDel = findViewById<Button>(R.id.butDel)
        val butBrowser = findViewById<Button>(R.id.butBrowser)
        val butComplex = findViewById<Button>(R.id.butComplex)

        but.setOnClickListener{
            showNotif()
        }
        butDel.setOnClickListener {
            delNotif()
        }
        butBrowser.setOnClickListener {
            openBrowser()
        }
        butComplex.setOnClickListener {
            complexNotif()
        }
    }



    private fun showNotif(){
        val notification = NotificationCompat.Builder(this, NORMAL_CHANNEL)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Простое оповещение")
            .setContentText("Что-то важное произошло")
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.baseline_panorama_24))
            .build()


        notificationManager.notify(
            R.id.SIMPLE_NOTIFICATION_ID,
            notification
        )
    }



    private fun delNotif(){
        notificationManager.cancel(R.id.SIMPLE_NOTIFICATION_ID)
    }



    private fun openBrowser(){
        val a2 = Intent(this, MainActivity2::class.java)
        val pa2 = PendingIntent.getActivity(
            this,
            R.id.BROWSER_PENDING_ID,
            a2,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, NORMAL_CHANNEL)
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentText("Посмотреть google.com")
            .setContentTitle("Запустить Браузер")
            .setContentIntent(pa2)
            .setAutoCancel(true)

        notificationManager.notify(R.id.GOOGLE_NOTIFICATION_ID, builder.build())

    }



    private fun complexNotif(){
        val browser = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse("https://www.louvre.fr"))
        val browserPI = PendingIntent.getActivity(
            this,
            R.id.BROWSER_PENDING_ID,
            browser,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val map = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse("geo:48.85, 2.34"))
        val mapPI = PendingIntent.getActivity(
            this,
            R.id.MAP_PENDING_ID,
            map,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, NORMAL_CHANNEL)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle("Экскурсия")
            .setContentText("Начинается через 5 минут")
            .addAction(NotificationCompat.Action(
                android.R.drawable.btn_star,
                "В браузере",
                browserPI
            ))
            .addAction(NotificationCompat.Action(
                android.R.drawable.btn_star,
                "На карте",
                mapPI
            ))

        notificationManager.notify(R.id.LOUVRE_NOTIFICATION_ID, builder.build())
    }
}