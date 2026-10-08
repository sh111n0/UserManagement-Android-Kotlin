package com.example.parcialkotlinapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ParImparActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_par_impar)

        val btnAtras =
            findViewById<Button>(R.id.btnAtrasParImpar)

        val etNumero =
            findViewById<EditText>(R.id.etNumero)

        val btnValidar =
            findViewById<Button>(R.id.btnValidar)

        val tvResultado =
            findViewById<TextView>(R.id.tvResultado)

        btnAtras.setOnClickListener {
            finish()
        }

        btnValidar.setOnClickListener {
            val texto =
                etNumero.text.toString().trim()

            if (texto.isEmpty()) {
                etNumero.error = "Ingrese un número"
                return@setOnClickListener
            }

            val numero = texto.toIntOrNull()

            if (numero == null) {
                etNumero.error =
                    "Ingrese un número entero válido"
                return@setOnClickListener
            }

            tvResultado.text =
                if (numero % 2 == 0) {
                    "$numero es PAR"
                } else {
                    "$numero es IMPAR"
                }
        }
    }
}
