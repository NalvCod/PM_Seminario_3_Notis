package com.example.pm_seminario_3_notificaciones

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pm_seminario_3_notificaciones.MainActivity.Companion
import com.example.pm_seminario_3_notificaciones.databinding.ActivityEjercicio2Binding
import java.util.concurrent.atomic.AtomicInteger

class Ejercicio2 : AppCompatActivity() {
    private lateinit var binding: ActivityEjercicio2Binding

    companion object {
        private const val CHANNEL_ID = "74321"
        private val CHANNEL_NAME = "Notis Ejercicios 2"
        var id = AtomicInteger(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEjercicio2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.botonTextStyle.setOnClickListener{

        }

        binding.botonBigPicture.setOnClickListener{

        }

    }

    private fun showBigPictureNotification() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.baseline_3d_rotation_24) // Replace with your image

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_3d_rotation_24) // Replace with your icon
            .setContentTitle("Notificación con Imagen Grande")
            .setContentText("Esta es una notificación con una imagen grande.")
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(com.example.pm_seminario_3_notificaciones.MainActivity.id.toInt(), builder.build())
        }
    }

    private fun showBigTextNotification() {
        val bigText = "Este es un ejemplo de una notificación con mucho texto. " +
                "Puedes usar BigTextStyle para mostrar una gran cantidad de texto en tu notificación."

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_3d_rotation_24) // Replace with your icon
            .setContentTitle("Notificación con Texto Grande")
            .setContentText("Esta es una notificación con mucho texto.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(bigText)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(com.example.pm_seminario_3_notificaciones.MainActivity.id.toInt(), builder.build())
        }
    }


}