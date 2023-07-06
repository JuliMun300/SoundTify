package com.example.soundtify

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SoundAdapter(
    val ListaCanciones: List<Cancion>,) : RecyclerView.Adapter<SoundAdapter.ViewHolder>() {

    lateinit var onItemClickListener: (Cancion) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_cancion, parent, false))
    }

    override fun onBindViewHolder(holder: SoundAdapter.ViewHolder, position: Int) {
        val item = ListaCanciones.get(position)
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return ListaCanciones.size
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val TextViewNombre = view.findViewById(R.id.textviewNombre) as TextView

        fun render(cancion: Cancion) {
            TextViewNombre.text = cancion.titulo
            view.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(cancion)

                } else {
                    Log.i("Adapter", "Te olvidaste impelementatr el adapter ")
                }
            }
        }
    }
}