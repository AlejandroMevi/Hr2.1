package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ItemExtratimeBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.TiempoExtra
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.formatHourMin
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.formatYearMonthDay
import java.util.*

class PreauthorizationAdapter(
    private val preauthorizationList: List<TiempoExtra>,
    private val itemClickListener: OnClickListener,
): RecyclerView.Adapter<ViewHolderGeneral<*>>()  {

    interface OnClickListener{
        fun onClick(tiempoExtra: TiempoExtra)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemExtratimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val itemholder = ViewHolder(itemBinding)
        itemBinding.root.setOnClickListener {
            val position = itemholder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onClick(preauthorizationList[position])
        }
        return itemholder
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: ViewHolderGeneral<*>, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(preauthorizationList[position])
        }
    }
    override fun getItemCount(): Int {
        return preauthorizationList.size
    }
    private inner class ViewHolder(
        val binding: ItemExtratimeBinding
    ) : ViewHolderGeneral<TiempoExtra>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: TiempoExtra) {
            binding.date.text = item.fecha
            binding.timeIn.text = item.horaEntrada
            binding.timeOut.text = item.horaSalida
        }
    }
}