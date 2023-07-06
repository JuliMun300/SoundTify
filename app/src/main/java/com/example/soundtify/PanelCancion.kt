package com.example.soundtify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.squareup.picasso.Picasso

private lateinit var Titulo: TextView
private lateinit var Autor: TextView
private lateinit var Imagen: ImageView
private lateinit var BotonPlay: Button
private lateinit var BotonStop: Button
private lateinit var mediaPlayer: MediaPlayer


class PanelCancion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel_cancion)

        Titulo = findViewById(R.id.Titulocancion)
        Autor = findViewById(R.id.AutorCancion)
        Imagen = findViewById(R.id.imageCancion)
        BotonPlay = findViewById(R.id.BotonPlay)
        BotonStop = findViewById(R.id.BotonStop)

        //TRAEMOS EL OBJETO CANCION AL PANELCANCION
        val cancion = intent.getParcelableExtra<Cancion>("Cancion")

        NotifyChannel()

        //SI EL OBJETO NO VIENE VACIO QUE HAGA TODO ESTO
        if (cancion != null) {
            Titulo.text = cancion.titulo
            Autor.text = cancion.autor
            Picasso.get().load(cancion.imagen).into(Imagen)

            val notificationManager = NotificationManagerCompat.from(this)

            //AL PRESIONAR EL BOTON DE REPRODUCIR COMIENZA LA CANCION Y SE ACTIVA LA NOTIFICACION
            BotonStop.isEnabled = false
            BotonPlay.setOnClickListener {
                BotonStop.isEnabled = true
                BotonPlay.isEnabled = false

                // Lanzamos el servicio de musica (MusicService)
                val intent = Intent(this, MusicService::class.java)
                intent.putExtra("CancionService", cancion)
                startService(intent)

                //Se crea la notificacion
                CreateNotification(cancion, notificationManager)
            }

            //AL PRESIONAR EL BOTON STOP SE DETIENE LA CANCION Y SE ELIMINA LA NOTIFICACION
            BotonStop.setOnClickListener {
                if (BotonStop.isEnabled) {
                    BotonStop.isEnabled = false
                    BotonPlay.isEnabled = true
                }
                // Se termina el servicio o se apaga
                stopService(Intent(this, MusicService::class.java))

                //Se desactiva la notificacion
                CancelNotification(notificationManager)
            }
        }
    }

    //FUNCION PARA CREAR EL CANAL DE NOTIFICACION
    private fun NotifyChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val Channel = NotificationChannel(
                "1",
                "Music Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(Channel)
        }
    }

    //FUNCION PARA CREAR LA NOTIFICACION
    private fun CreateNotification(
        cancion: Cancion,
        notificationManagerCompat: NotificationManagerCompat
    ) {
        val pandingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this, "1")
            .setContentTitle("Se esta reproduciendo:")
            .setContentText("${cancion.titulo}")
            .setSmallIcon(R.drawable.icono_cancion)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pandingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, notification)
    }

    //FUNCION PARA DESACTIVAR LA NOTIFICACION
    private fun CancelNotification(notificationManagerCompat: NotificationManagerCompat) {
        notificationManagerCompat.cancelAll()
    }

    //CUANDO EL USUARIO PRESIONA EL BOTON DE RETROCESO Y LA MUSICA SUENA, SE CANCELA TODO
    override fun onBackPressed() {
        super.onBackPressed()
        stopService(Intent(this, MusicService::class.java))
        val notificationManager = NotificationManagerCompat.from(this)
        CancelNotification(notificationManager)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        BotonStop.isEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}