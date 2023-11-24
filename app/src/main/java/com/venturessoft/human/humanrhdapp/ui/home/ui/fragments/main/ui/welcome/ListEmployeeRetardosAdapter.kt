package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.welcome

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListItemRetardosEmployeeBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem

class ListEmployeeRetardosAdapter(
    private val item: List<ItemItem>
) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListItemRetardosEmployeeBinding.inflate(
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
    private inner class ViewHolder(
        val binding: ListItemRetardosEmployeeBinding,
        val context: Context
    ) : ViewHolderGeneral<ItemItem>(binding.root) {
        override fun bind(item: ItemItem) {
            val numero = item.numEmp.toLong()
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
            when (item.numEmp.toInt()) {
                in 1..8 -> {
                    binding.txtPosibleFalta.isVisible = true
                }

                in 9..30 -> {
                    binding.permisoHrs.isVisible = true
                }

                in 31..100 -> {
                    binding.txtIncapacidades.isVisible = true
                }

                in 100..192 -> {
                    binding.txtVacaciones.isVisible = true
                }

                else -> {
                    binding.txtAusentismos.isVisible = true
                }
            }
        }
    }
}