package com.example.parcialkotlinapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { vista, insets ->
            val bordesSistema =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            vista.setPadding(
                bordesSistema.left,
                bordesSistema.top,
                bordesSistema.right,
                bordesSistema.bottom
            )

            insets
        }

        val tvSaludo =
            findViewById<TextView>(R.id.tvSaludo)

        val btnCerrarSesion =
            findViewById<Button>(R.id.btnCerrarSesion)

        val btnParImpar =
            findViewById<Button>(R.id.btnParImpar)

        val nombreUsuario =
            intent.getStringExtra("nombreUsuario")
                .orEmpty()
                .ifBlank { "estudiante" }

        tvSaludo.text =
            "${obtenerSaludo()}, $nombreUsuario"

        btnParImpar.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ParImparActivity::class.java
                )
            )
        }

        btnCerrarSesion.setOnClickListener {
            mostrarConfirmacionSalida()
        }
    }

    private fun obtenerSaludo(): String {
        return when (
            Calendar.getInstance()
                .get(Calendar.HOUR_OF_DAY)
        ) {
            in 5..11 -> "Buenos días"
            in 12..17 -> "Buenas tardes"
            else -> "Buenas noches"
        }
    }

    private fun mostrarConfirmacionSalida() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage(
                "¿Está seguro de que desea cerrar sesión?"
            )
            .setNegativeButton("CANCELAR", null)
            .setPositiveButton("SALIR") { _, _ ->
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                ).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
                finish()
            }
            .show()
    }
}
