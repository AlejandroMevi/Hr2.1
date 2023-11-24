package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ItemVacationAuthorizationBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.VacationAutthorizationItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models.ItemItemBusqeda
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter

class VacationAuthorizationAdapter(
    private val item: ArrayList<ItemItem>,
    private val itemClickListener: OnClickListener,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    interface OnClickListener{
        fun onClick(vacationAutthorizationItem: ItemItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemVacationAuthorizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val itemholder = ViewHolder(itemBinding,parent.context)
        itemBinding.root.setOnClickListener {
            val position = itemholder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onClick(item[position])
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
        val binding: ItemVacationAuthorizationBinding,
        val context: Context
        ) : ViewHolderGeneral<ItemItem>(binding.root) {
        override fun bind(item: ItemItem) {
            binding.listName.text = item.nombreCompleto
            binding.listNumEmployee.text = item.numEmp
            if(!item.puesto.contains("null")){
                binding.date.text = item.puesto
            }
            if (item.fotografia.contains("File not foundjava", ignoreCase = true) || item.fotografia.isEmpty()) {
                binding.listImage.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.user_icon_big)
                )
            } else {
                val imagenBase64 = Base64.decode(item.fotografia, Base64.DEFAULT)
                val imagenconverBitmap = BitmapFactory.decodeByteArray(imagenBase64, 0, imagenBase64.size)
                binding.listImage.setImageBitmap(imagenconverBitmap)
            }
        }
    }
}