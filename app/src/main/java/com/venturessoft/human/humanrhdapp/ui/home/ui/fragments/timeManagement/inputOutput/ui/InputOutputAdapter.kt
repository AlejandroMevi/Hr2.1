package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListItemInputOuttputBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutput

class InputOutputAdapter(
    private val item: List<InputOutput>,
    private val itemClickListener: OnClickListener,
    ) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {

    interface OnClickListener{
        fun onClick(inputOutput: InputOutput)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListItemInputOuttputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        val binding: ListItemInputOuttputBinding,
        val context: Context
    ) : ViewHolderGeneral<InputOutput>(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun bind(item: InputOutput) {
            binding.date.text = item.fecha
            binding.sec.text = item.sec.toString()
            binding.entradasalida.text = "${item.entrada} - ${item.salida}"
            binding.estacion.text = item.estacion
            binding.turno.text = item.turno
            if (item.status == "B"){
                binding.listImage.setImageDrawable(context.getDrawable(R.color.letraOthers))
            }else{
                binding.listImage.setImageDrawable(context.getDrawable(R.color.principal))
            }
        }
    }
}