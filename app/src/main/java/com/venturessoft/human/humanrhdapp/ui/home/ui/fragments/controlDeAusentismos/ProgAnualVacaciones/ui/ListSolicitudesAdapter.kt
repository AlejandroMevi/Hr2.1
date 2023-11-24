package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListaSolicitudesProgAnualVacBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.VacacionesProgramadasItem
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class ListSolicitudesAdapter(
    private val item: List<VacacionesProgramadasItem>
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    companion object {
        var listSolicitudSelect = MutableLiveData<List<Long>>()
        val solicitudesSelect = mutableListOf<Long>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListaSolicitudesProgAnualVacBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding, parent.context)
    }
    override fun onBindViewHolder(holder: ViewHolderGeneral<*>, position: Int) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_in)
        animation.duration = 500
        holder.itemView.startAnimation(animation)
        when (holder) {
            is ViewHolder -> holder.bind(item[position])
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int = item.size
    private inner class ViewHolder(val binding: ListaSolicitudesProgAnualVacBinding, val context: Context) : ViewHolderGeneral<VacacionesProgramadasItem>(binding.root) {
        override fun bind(item: VacacionesProgramadasItem) {
            val grupo = item.grupo.toString()
            val secuencia = item.secuencia.toString()
            val fechaInicio = item.fechaInicio
            val fechaTermino = item.fechaTermino
            val diasTomados = item.diasTomados.toString()
            binding.tvGrupo.text = grupo
            binding.tvSecuencia.text = secuencia
            binding.tvFechaInicio.text = fechaInicio
            if (fechaInicio != null) {
                binding.tvFechaInicio.text = Utilities.cambiarFormatoFecha(fechaInicio, true)
            } else {
                binding.tvFechaInicio.isVisible = false
                binding.titleFechaInicio.isVisible = false
            }
            if (fechaTermino != null){
                binding.tvFechaTermino.text = Utilities.cambiarFormatoFecha(fechaTermino, true)
            }else{
                binding.tvFechaTermino.isVisible = false
                binding.titleFechaTermino.isVisible = false
            }
            binding.tvDiasTomados.text = diasTomados
        }
    }
}