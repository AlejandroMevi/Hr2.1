package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ListItemStationBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.Station
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.GeneralStationsFragment.Companion.lisEdit
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.GeneralStationsFragment.Companion.listAdd
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.GeneralStationsFragment.Companion.listFirstSations

class StationAdapter(
    private val item: List<Station>
    ):RecyclerView.Adapter<ViewHolderGeneral<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ListItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding,parent.context)
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
    inner class ViewHolder(val binding: ListItemStationBinding, val context: Context):ViewHolderGeneral<Station>(binding.root) {
        override fun bind(item: Station) {
            binding.tvTitle.text = item.descripcion
            when(item.tipo){
                "A"-> binding.radioGroup.check(R.id.rb_acceso)
                "T"-> binding.radioGroup.check(R.id.rb_tiempos)
                "C"-> binding.radioGroup.check(R.id.rb_tiempos_acceso)
                else -> {
                    binding.radioGroup.check(R.id.rb_et)
                    for (i in 0 until binding.radioGroup.childCount) {
                        binding.radioGroup.getChildAt(i).isEnabled = false
                    }
                }
            }
            if (item.gafete == 0L){
                for (i in 0 until binding.radioGroup.childCount) {
                    binding.radioGroup.getChildAt(i).isEnabled = false
                }
            }
            binding.radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
                val type = when (radioGroup.checkedRadioButtonId) {
                    R.id.rb_acceso -> "A"
                    R.id.rb_tiempos -> "T"
                    R.id.rb_tiempos_acceso -> "C"
                    else -> ""
                }
                val newStatio = Station(
                    id = item.id,
                    estacion = item.estacion,
                    descripcion = item.descripcion,
                    tipo = type,
                    gafete = item.gafete,
                    status = item.status,
                    service = item.service
                )
                addToService(newStatio)
            }
        }
    }
    private fun addToService(newStatio: Station) {
        when (newStatio.service){
            1->{
                if (!listFirstSations.contains(newStatio)){
                    try {
                        lisEdit.remove(lisEdit.find { it.estacion == newStatio.estacion})
                    }catch (_:Exception){ }
                    lisEdit.add(newStatio)
                }else{
                    try {
                        lisEdit.remove(lisEdit.find { it.estacion == newStatio.estacion})
                    }catch (_:Exception){ }
                }
                Log.i("Estacion Edit",Gson().toJson(lisEdit))
            }
            2->{
                try {
                    listAdd.remove(listAdd.find { it.estacion == newStatio.estacion})
                }catch (_:Exception){ }
                listAdd.add(newStatio)
                Log.i("Estacion Add",Gson().toJson(listAdd))
            }
        }
    }
}