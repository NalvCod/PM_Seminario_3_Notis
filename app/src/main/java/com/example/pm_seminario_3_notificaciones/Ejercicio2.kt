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
import android.widget.Toast
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
        private const val CHANNEL_NAME = "Notis Ejercicios 2"
        var id = AtomicInteger(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEjercicio2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar permisos de notificación
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        binding.botonTextStyle.setOnClickListener {
            showBigTextNotification(this)
        }

        binding.botonBigPicture.setOnClickListener {
            showBigPictureNotification(this)
        }
    }

    // Manejo de la respuesta de los permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, ahora podemos mostrar la notificación
                showBigTextNotification(this)
                showBigPictureNotification(this)
            } else {
                Toast.makeText(this, "Permiso denegado para mostrar notificaciones.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showBigPictureNotification(c: Context) {
        // Crear el canal de notificación para API 26 y superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.imagen)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_3d_rotation_24)
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
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(id.incrementAndGet(), builder.build())
        }
    }

    private fun showBigTextNotification(c: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val bigText = "Este es un ejemplo de una notificación con mucho texto. " +
                "Puedes usar BigTextStyle para mostrar una gran cantidad de texto en tu notificación."

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_3d_rotation_24)
            .setContentTitle("Notificación con Texto Grande")
            .setContentText("Esta es una notificación con mucho texto.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(bigText)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // Verificar permisos antes de mostrar la notificación
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            // Usar un ID único para cada notificación
            notify(id.incrementAndGet(), builder.build())
        }
    }
}