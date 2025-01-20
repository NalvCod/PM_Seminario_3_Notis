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
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pm_seminario_3_notificaciones.Ejercicio3.Companion
import com.example.pm_seminario_3_notificaciones.databinding.ActivityEjercicio3Binding
import java.util.concurrent.atomic.AtomicInteger

class Ejercicio3 : AppCompatActivity() {
    private lateinit var binding: ActivityEjercicio3Binding
    private lateinit var titulo: String
    private lateinit var texto: String
    private lateinit var nombreBotones: List<String>
    private  var imagenSeleccionada: Int = 0
    private var iconoSeleccionado: Int = 0

    companion object {
        private const val CHANNEL_ID = "74321"
        private const val CHANNEL_NAME = "Notis Ejercicios 2"
        var id = AtomicInteger(0)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEjercicio3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.atras.setOnClickListener {
            val intent = Intent(this, MenuEjercicios::class.java)
            startActivity(intent)
        }

        binding.enviar.setOnClickListener {
            titulo = binding.tituloNoti.text.toString()
            texto = binding.texto.text.toString()
            nombreBotones = binding.nombreBotones.text.toString().split(",")

            showBigPictureNotification(this, nombreBotones.size)
        }

        binding.botonmass.setOnClickListener {
            if (binding.cantidadBotones.text.toString().toInt() != 3){
            var cantidad = binding.cantidadBotones.text.toString().toInt() + 1
            binding.cantidadBotones.text = cantidad.toString()
            }
        }

        binding.botonmenos.setOnClickListener {
            if (binding.cantidadBotones.text.toString().toInt() != 0) {
                var cantidad = binding.cantidadBotones.text.toString().toInt() - 1
                binding.cantidadBotones.text = cantidad.toString()
            }
        }

        val spinner1Items = listOf("Icono 1", "Icono 2")
        val spinner1Adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner1Items)
        spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner1.adapter = spinner1Adapter
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spinner1.selectedItem.toString()) {
                    "Icono 1" -> iconoSeleccionado = R.drawable.icono3
                    "Icono 2" -> iconoSeleccionado = R.drawable.icono2
                    else -> iconoSeleccionado = R.drawable.baseline_3d_rotation_24
                }

                val selectedIcon = spinner1Items[position]
                Toast.makeText(this@Ejercicio3, "Seleccionaste $selectedIcon", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinner2Items = listOf("Imagen 1", "Imagen 2")
        val spinner2Adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, spinner2Items)
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = spinner2Adapter
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spinner2.selectedItem.toString()) {
                    "Imagen 1" -> imagenSeleccionada = R.drawable.imagen
                    "Imagen 2" -> imagenSeleccionada = R.drawable.imagendos
                }
                val selectedIcon = spinner1Items[position]
                Toast.makeText(this@Ejercicio3, "Seleccionaste $selectedIcon", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun showBigPictureNotification(c: Context, acciones: Int) {
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

        val bitmap = BitmapFactory.decodeResource(resources, imagenSeleccionada)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(iconoSeleccionado)
            .setContentTitle(titulo)
            .setContentText(texto)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        for (i in 0..acciones - 1) {
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val action = NotificationCompat.Action.Builder(
                R.drawable.baseline_3d_rotation_24, "boton ${nombreBotones[i]}", pendingIntent
            ).build()
            builder.addAction(action)
        }
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
}
