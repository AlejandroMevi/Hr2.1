package com.venturessoft.human.humanrhdapp.ui.login.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ItemEnterpriceBinding
import com.venturessoft.human.humanrhdapp.ui.login.data.models.scia

class EnterpriceAdapter(
    private val item: ArrayList<scia>
    ) : RecyclerView.Adapter<ViewHolderGeneral<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemEnterpriceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding, parent.context)
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
        val binding: ItemEnterpriceBinding,
        val context: Context
    ) : ViewHolderGeneral<scia>(binding.root) {
        override fun bind(item: scia) {
            binding.tvEnterprice.text = item.razonSocial
        }
    }
}