package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListItemFilterEmployeeReportsBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.vm.EstacionesLibresViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.setTextColorRes

class ListFilterEmployeeAdapter(
    private val item: List<ItemItem>,
    private val freeStationVM: EstacionesLibresViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val itemClickListener: OnClickListener,
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {
    interface OnClickListener {
        fun onClick(item: ItemItem, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListItemFilterEmployeeReportsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val itemholder = ViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener {
            val position = itemholder.bindingAdapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            } ?: return@setOnClickListener
            itemClickListener.onClick(item[position], position)
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
    private inner class ViewHolder(
        val binding: ListItemFilterEmployeeReportsBinding,
        val context: Context
    ) : ViewHolderGeneral<ItemItem>(binding.root) {
        override fun bind(item: ItemItem) {
            val numero = item.numEmp.toLong()
            val numeroFormateado = String.format("%010d", numero)
            val numeroConCeros = numeroFormateado.padStart(10, '0')
            binding.listName.text = item.nombreCompleto
            binding.listNumEmployee.text = numero.toString()
            binding.listPuesto.text = if (item.puesto == "null") "" else item.puesto
            if (item.fotografia.contains(
                    "File not foundjava",
                    ignoreCase = true
                ) || item.fotografia.isEmpty()
            ) {
                binding.listImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.user_icon_big
                    )
                )
            } else {
                val imagenBase64 = Base64.decode(item.fotografia, Base64.DEFAULT)
                val imagenconverBitmap =
                    BitmapFactory.decodeByteArray(imagenBase64, 0, imagenBase64.size)
                binding.listImage.setImageBitmap(imagenconverBitmap)
            }
            freeStationVM.listEmployeFilterSelect.observe(lifecycleOwner) {
                if (it != null) {
                    try {
                        if (it.contains(item.numEmp.toLong())) {
                            binding.cardviewlista.backgroundTintList =
                                context.getColorStateList(R.color.backFilter)
                            binding.listName.setTextColorRes(R.color.principal)
                            binding.listNumEmployee.setTextColorRes(R.color.principal)
                            binding.listPuesto.setTextColorRes(R.color.principal)
                            binding.backSelect.isVisible = true
                        } else {
                            binding.cardviewlista.backgroundTintList =
                                context.getColorStateList(R.color.white)
                            binding.listName.setTextColorRes(R.color.gray_title)
                            binding.listNumEmployee.setTextColorRes(R.color.black)
                            binding.listPuesto.setTextColorRes(R.color.gray_alpha)
                            binding.backSelect.isVisible = false
                        }
                    } catch (_: Exception) { }
                }
            }
        }
    }
}