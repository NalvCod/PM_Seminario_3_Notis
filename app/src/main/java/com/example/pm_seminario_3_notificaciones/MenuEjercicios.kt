package com.example.pm_seminario_3_notificaciones

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pm_seminario_3_notificaciones.databinding.ActivityMenuEjerciciosBinding

class MenuEjercicios : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityMenuEjerciciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuEjerciciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ej1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.ejer2.setOnClickListener {
            val intent = Intent(this, Ejercicio2::class.java)
            startActivity(intent)
        }
        binding.ej3.setOnClickListener {
            val intent = Intent(this, Ejercicio3::class.java)
            startActivity(intent)
        }

    }
}