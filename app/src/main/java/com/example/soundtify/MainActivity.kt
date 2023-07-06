package com.example.soundtify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var toolbar: Toolbar
private lateinit var recyclerCancion: RecyclerView
private lateinit var adapter: SoundAdapter
private lateinit var mediaPlayer: MediaPlayer

// Lista de las canciones
private var listaCanciones = mutableListOf<Cancion>(
    Cancion("Me Llamas", "Piso 21", R.raw.me_llamas, "https://lastfm.freetls.fastly.net/i/u/ar0/28458996d835ce3d0e41ca8731319471"),
    Cancion("Glad you Came", "The Wanted", R.raw.glad_you_came, "https://i.discogs.com/7i98VPEJ_0HBktvQZwRZT2AWt_0KGFd0OYm2u_Z6Oyg/rs:fit/g:sm/q:90/h:600/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTMyMDgw/ODItMTMyMDUzMzUz/My5qcGVn.jpeg"),
    Cancion("Let Me Love You", "Dj Snake", R.raw.let_me_love_you, "https://lastfm.freetls.fastly.net/i/u/ar0/4a867e2dcb985951f8eb73d662492c83"),
    Cancion("Candy Shop", "50 Cent", R.raw.candy_shop, "https://cdns-images.dzcdn.net/images/cover/8550ab66b25e9ccfbe3cad3cdb7d8284/350x350.jpg"),
    Cancion("Watermelon Sugar", "Harry Styles", R.raw.watermelon_sugar, "https://lastfm.freetls.fastly.net/i/u/770x0/dadfd33d7d823269c9f643c6c4e74218.jpg") ,
    Cancion("One Thing", "One Direction", R.raw.one_thing, "https://lastfm.freetls.fastly.net/i/u/770x0/18268644f2524dac89fcebdc4a9979c3.jpg"),
    Cancion("Que Tengo Que Hacer", "Daddy Yankee", R.raw.que_tengo_que_hacer, "https://i1.sndcdn.com/artworks-Fmr7rjvsrpVA-0-t500x500.jpg"),
    Cancion("The Unforgiven", "Metallica", R.raw.the_unforgiven, "https://lastfm.freetls.fastly.net/i/u/ar0/502ef8d654cf412c9caeba15ce9f5a97"),
    Cancion("Promiscuos", "Nelly Furtado", R.raw.promiscuos, "https://lastfm.freetls.fastly.net/i/u/770x0/96bf31f5f3eb4212baca172214b2b562.jpg")
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Para cambiar el color del Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.Azul_Osccuro)
        }

        SetupRecyclerView()
        SetupToolbar()
    }

    //FUNCION PARA EJECUTAR Y CAMBIAR EL TOOLBAR
    private fun SetupToolbar() {
        toolbar = findViewById(R.id.toolbarSong)
        toolbar.title = "SoundTify"
        setSupportActionBar(toolbar)
    }

   // FUNCION PARA EJECUTAR LA LISTA DE LAS CANCIONES
    private fun SetupRecyclerView() {
        recyclerCancion = findViewById(R.id.RecyclerviewCanciones)
        recyclerCancion.layoutManager = LinearLayoutManager(this)
        adapter = SoundAdapter(listaCanciones)
        recyclerCancion.adapter = adapter

        adapter.onItemClickListener = {
            val intent = Intent(this, PanelCancion::class.java)
            intent.putExtra("Cancion", it)
            startActivity(intent)
        }
    }
}




