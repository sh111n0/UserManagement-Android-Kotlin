# User Management Android Kotlin

Aplicación Android para registrar usuarios e iniciar sesión mediante una API REST desarrollada con .NET. El proyecto muestra consumo de servicios HTTP, validación de formularios y navegación entre pantallas.

Backend relacionado: [UserManagement API .NET](https://github.com/sh111n0/UserManagement-API-DotNet).

## Funcionalidades

- Registro de usuarios con nombre, apellido, correo, contraseña, fecha de nacimiento, universidad y semestre.
- Validación de campos, correo, longitud de contraseña y selección de semestre.
- Inicio de sesión contra la API REST.
- Manejo de respuestas correctas, credenciales inválidas y fallos de conexión.
- Navegación entre registro, inicio de sesión y pantalla principal.
- Módulo adicional para determinar si un número es par o impar.

## Tecnologías

- Kotlin
- Android SDK
- Retrofit 2.11
- Gson Converter
- Gradle Kotlin DSL
- XML para interfaces

## Estructura principal

```text
app/src/main/java/com/example/parcialkotlinapp/
├── api/        Cliente Retrofit y definición de endpoints
├── models/     Objetos enviados y recibidos por la API
├── LoginActivity.kt
├── RegistroActivity.kt
├── MainActivity.kt
└── ParImparActivity.kt
```

## Endpoints utilizados

| Método | Ruta | Uso |
|---|---|---|
| GET | `api/Usuarios` | Consulta de usuarios |
| POST | `api/Usuarios` | Registro |
| POST | `api/Usuarios/login` | Inicio de sesión |

## Ejecución local

Requisitos:

- Android Studio
- JDK 11 o compatible
- Emulador o dispositivo con Android 7.0 o superior
- [API .NET](https://github.com/sh111n0/UserManagement-API-DotNet) en ejecución

1. Clona el repositorio y ábrelo en Android Studio.

```bash
git clone https://github.com/sh111n0/UserManagement-Android-Kotlin.git
```

2. Ejecuta el backend.

3. Revisa la constante `BASE_URL` en `ApiClient.kt`. Para acceder al equipo anfitrión desde el emulador Android se usa `10.0.2.2`. El puerto debe coincidir con el puerto HTTP de la API.

4. Sincroniza Gradle y ejecuta la aplicación.

## Conocimientos demostrados

- Consumo de APIs REST con Retrofit.
- Conversión entre JSON y objetos Kotlin.
- Validación de formularios Android.
- Llamadas asíncronas y manejo de errores.
- Navegación mediante `Intent`.
- Diseño de interfaces con XML.

## Alcance actual

El proyecto está configurado para desarrollo local: utiliza HTTP, una URL fija para el backend y no conserva una sesión mediante token. Estas mejoras pueden incorporarse en una siguiente versión.
