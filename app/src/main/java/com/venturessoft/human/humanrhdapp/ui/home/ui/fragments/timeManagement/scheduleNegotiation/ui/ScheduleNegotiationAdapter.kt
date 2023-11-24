package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ItemScheduleNegotiationBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiation
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class ScheduleNegotiationAdapter(
    private val item: List<ScheduleNegotiation>,
    private val itemClickListener: ScheduleNegotiationFragment,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    interface OnClickListener{
        fun onClick(scheduleNegotiation: ScheduleNegotiation)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemScheduleNegotiationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val itemholder = ViewHolder(itemBinding,parent.context)
        itemBinding.root.setOnClickListener {
            val position = itemholder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onClick(item[position])
        }
        return itemholder
    }
    override fun onBindViewHolder(holder: ViewHolderGeneral<*>, position: Int) {
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
    inner class ViewHolder(
        val binding: ItemScheduleNegotiationBinding,
        val context: Context
    ) : ViewHolderGeneral<ScheduleNegotiation>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: ScheduleNegotiation) {
            binding.dateStart.text = item.fechaInicio
            binding.dateEnd.text = item.fechaFin
            if (item.idRol != 0){
                binding.layoutRolTurn.text = context.getString(R.string.rol)
                binding.rolTurn.text = item.rolTurno
            }
            if(item.idTurno != 0){
                binding.layoutRolTurn.text = context.getString(R.string.turno)
                binding.rolTurn.text = item.turno
            }
            binding.category.text = item.categoria
            binding.zona.text = item.zona
        }
    }
}