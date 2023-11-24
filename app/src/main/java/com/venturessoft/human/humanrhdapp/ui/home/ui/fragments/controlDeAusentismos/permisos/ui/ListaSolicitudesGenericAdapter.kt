package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListaSolicitudesGenericoBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.ListaSolicitudesItems
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class ListaSolicitudesGenericAdapter(
    private val item: List<ListaSolicitudesItems>,
    private val lifecycleOwner: LifecycleOwner,
    private val itemClickListener: OnClickListener
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    companion object {
        var listSolicitudGenericSelect = MutableLiveData<List<Long>>()
    }

    interface OnClickListener {
        fun onClick(item: ListaSolicitudesItems, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListaSolicitudesGenericoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val itemholder = ViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener {
            val position =
                itemholder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onClick(item[position], position)
        }
        return itemholder
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
    private inner class ViewHolder(
        val binding: ListaSolicitudesGenericoBinding,
        val context: Context
    ) : ViewHolderGeneral<ListaSolicitudesItems>(binding.root) {
        override fun bind(item: ListaSolicitudesItems) {

            val concepto = item.concepto.toString()
            val conceptoPermiso = item.conceptoPermiso.toString()
            val dias = item.dias?.toFloat().toString()
            val estatus = item.estatus.toString()
            val fP = item.fechaPermiso.toString()
            val fI = item.fechaInicial.toString()
            val horasPermiso = item.horasPermiso.toString()
            val horaInicial = item.horaInicial.toString()
            val horaFinal = item.horaFinal.toString()
            val motivoDescripcion = item.motivoDescripcion.toString()
            val minutosPermiso = item.minutosPermiso.toString()
            val motivo = item.motivo.toString()
            val numEmp = item.numEmp.toString()
            val observaciones = item.observaciones.toString()

            var fechaPermiso: String? = null
            var fechaInicial: String? = null

            if (fP.isEmpty() || fP != "null") {
                fechaPermiso = Utilities.cambiarFormatoFecha(fP, false)
                binding.fechaInicio.isVisible = false
            } else {
                fechaInicial = fI
                binding.fechaPermiso.isVisible = false
            }

            if (concepto != "null") {
                binding.tvConcepto.text = concepto; binding.concepto.isVisible = true
            }
            if (conceptoPermiso != "null") {
                binding.tvConceptoPermiso.text =
                    conceptoPermiso
            }
            if (dias != "null") {
                binding.tvDias.text = dias; binding.dias.isVisible = true
            }
            if (estatus != "null") {
                binding.tvEstatus.text = estatus
            }
            if (!fechaPermiso.isNullOrEmpty()) {
                binding.tvFechaPermiso.text = fechaPermiso; binding.fechaPermiso.isVisible = true
            }
            if (!fechaInicial.isNullOrEmpty()) {
                binding.tvFechaInicio.text = fechaInicial; binding.fechaInicio.isVisible = true
            }
            if (horasPermiso != "null") {
                binding.tvHorasPermiso.text = horasPermiso; binding.horasPermiso.isVisible = true
            }
            if (horaInicial != "null") {
                binding.tvHoraInicial.text = horaInicial; binding.horasInicial.isVisible = true
            }
            if (horaFinal != "null") {
                binding.tvHoraFinal.text = horaFinal; binding.horaFinal.isVisible = true
            }
            if (motivoDescripcion != "null") {
                binding.tvMotivoDescripcion.text = motivoDescripcion; binding.motivoDescripcion.isVisible = true
            }
            if (minutosPermiso != "null") {
                binding.tvMinutosPermiso.text = minutosPermiso; binding.minutosPermiso.isVisible =
                    true
            }
            if (motivo != "null") {
                binding.tvMotivo.text = motivo
            }
            if (numEmp != "null") {
                binding.tvNumEmp.text = numEmp
            }
            if (observaciones != "null") {
                binding.tvObservaciones.text = observaciones; binding.observaciones.isVisible = true
            }
        }
    }
}