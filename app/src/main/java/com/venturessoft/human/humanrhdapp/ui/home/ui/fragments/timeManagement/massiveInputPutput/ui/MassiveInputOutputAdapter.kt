package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListItemInputOuttputBinding
import com.venturessoft.human.humanrhdapp.databinding.ListItemMassiveInputOuttputBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutput
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.data.models.MassiveInputOutput

class MassiveInputOutputAdapter(
    private val item: List<MassiveInputOutput>,
    private val itemClickListener: OnClickListener,
    ) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {

    interface OnClickListener{
        fun onClick(inputOutput: MassiveInputOutput)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListItemMassiveInputOuttputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        val binding: ListItemMassiveInputOuttputBinding,
        val context: Context
    ) : ViewHolderGeneral<MassiveInputOutput>(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun bind(item: MassiveInputOutput) {
            when (item.fueraTurno){
                1->{
                    binding.cvRegister.isVisible = true
                    binding.tvRegister.text = "Registro de comedor"
                    val color = ColorStateList.valueOf(context.resources.getColor(R.color.numEmployeeBackGround))
                    binding.cvRegister.backgroundTintList = color
                }
                2->{
                    binding.cvRegister.isVisible = true
                    binding.tvRegister.text = "Registro fuera de turno"
                    val color = ColorStateList.valueOf(context.resources.getColor(R.color.numEmployeeBackGround2))
                    binding.cvRegister.backgroundTintList = color
                }
                3->{
                    binding.cvRegister.isVisible = true
                    binding.tvRegister.text = "Registro de posible falta"
                    val color = ColorStateList.valueOf(context.resources.getColor(R.color.numEmployeeBackGround3))
                    binding.cvRegister.backgroundTintList = color
                }
            }
            if (!item.isAcctive){
                binding.cvDisable.isVisible = true
            }
            binding.tvNumEmp.text = item.numEmple
            binding.tvNameUser.text = item.nombreCompleto
            binding.tvInput.text = item.entrada
            binding.tvOutput.text = item.salida
            binding.tvLocation.text = item.estacion
            binding.tvSecuenceNumber.text = item.secuencia
        }
    }
}