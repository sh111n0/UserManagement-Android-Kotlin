package com.example.parcialkotlinapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.parcialkotlinapp.api.ApiClient
import com.example.parcialkotlinapp.models.CrearUsuarioRequest
import com.example.parcialkotlinapp.models.Usuario
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnAtras = findViewById<Button>(R.id.btnAtrasRegistro)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etCorreo = findViewById<EditText>(R.id.etCorreoRegistro)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegistro)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val etUniversidad = findViewById<EditText>(R.id.etUniversidad)
        val spSemestre = findViewById<Spinner>(R.id.spSemestre)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val tvMensaje = findViewById<TextView>(R.id.tvMensajeRegistro)

        var fechaNacimientoApi = ""

        val semestres = arrayOf(
            "Seleccione semestre",
            "Semestre 1",
            "Semestre 2",
            "Semestre 3",
            "Semestre 4",
            "Semestre 5",
            "Semestre 6",
            "Semestre 7",
            "Semestre 8",
            "Semestre 9",
            "Semestre 10"
        )

        spSemestre.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            semestres
        ).also {
            it.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
        }

        btnAtras.setOnClickListener {
            finish()
        }

        etFechaNacimiento.setOnClickListener {
            val fechaInicial = Calendar.getInstance().apply {
                add(Calendar.YEAR, -18)
            }

            val dialogoFecha = DatePickerDialog(
                this,
                { _, anio, mes, dia ->
                    etFechaNacimiento.setText(
                        String.format(
                            Locale.getDefault(),
                            "%02d/%02d/%04d",
                            dia,
                            mes + 1,
                            anio
                        )
                    )

                    fechaNacimientoApi = String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        anio,
                        mes + 1,
                        dia
                    )

                    etFechaNacimiento.error = null
                },
                fechaInicial.get(Calendar.YEAR),
                fechaInicial.get(Calendar.MONTH),
                fechaInicial.get(Calendar.DAY_OF_MONTH)
            )

            dialogoFecha.datePicker.maxDate =
                System.currentTimeMillis()

            dialogoFecha.show()
        }

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString()
            val universidad = etUniversidad.text.toString().trim()
            val semestre = spSemestre.selectedItemPosition

            if (nombre.isBlank()) {
                etNombre.error = "Ingrese el nombre"
                return@setOnClickListener
            }

            if (apellido.isBlank()) {
                etApellido.error = "Ingrese el apellido"
                return@setOnClickListener
            }

            if (correo.isBlank()) {
                etCorreo.error = "Ingrese el correo"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(correo)
                    .matches()
            ) {
                etCorreo.error = "Correo no válido"
                return@setOnClickListener
            }

            if (password.length < 6) {
                etPassword.error =
                    "La contraseña debe tener mínimo 6 caracteres"
                return@setOnClickListener
            }

            if (fechaNacimientoApi.isBlank()) {
                etFechaNacimiento.error =
                    "Seleccione la fecha de nacimiento"
                return@setOnClickListener
            }

            if (universidad.isBlank()) {
                etUniversidad.error = "Ingrese la universidad"
                return@setOnClickListener
            }

            if (semestre == 0) {
                Toast.makeText(
                    this,
                    "Seleccione el semestre",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val nuevoUsuario = CrearUsuarioRequest(
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                password = password,
                fechaNacimiento = fechaNacimientoApi,
                universidad = universidad,
                semestre = semestre
            )

            tvMensaje.text = "Registrando usuario..."
            btnRegistrar.isEnabled = false

            ApiClient.usuarioApi
                .crearUsuario(nuevoUsuario)
                .enqueue(object : Callback<Usuario> {

                    override fun onResponse(
                        call: Call<Usuario>,
                        response: Response<Usuario>
                    ) {
                        btnRegistrar.isEnabled = true

                        if (response.isSuccessful) {
                            tvMensaje.text = ""
                            mostrarRegistroExitoso()
                        } else {
                            tvMensaje.text =
                                obtenerMensajeError(response)
                        }
                    }

                    override fun onFailure(
                        call: Call<Usuario>,
                        t: Throwable
                    ) {
                        btnRegistrar.isEnabled = true
                        tvMensaje.text =
                            "No se pudo conectar con el servidor: ${t.message}"
                    }
                })
        }
    }

    private fun mostrarRegistroExitoso() {
        AlertDialog.Builder(this)
            .setTitle("Registro exitoso")
            .setMessage("El usuario fue registrado correctamente.")
            .setCancelable(false)
            .setPositiveButton("ACEPTAR") { _, _ ->
                finish()
            }
            .show()
    }

    private fun obtenerMensajeError(
        response: Response<Usuario>
    ): String {
        return try {
            val contenido =
                response.errorBody()?.string().orEmpty()

            val mensaje =
                JSONObject(contenido)
                    .optString("mensaje")

            mensaje.ifBlank {
                "Error del servidor: ${response.code()}"
            }
        } catch (_: Exception) {
            "Error del servidor: ${response.code()}"
        }
    }
}
