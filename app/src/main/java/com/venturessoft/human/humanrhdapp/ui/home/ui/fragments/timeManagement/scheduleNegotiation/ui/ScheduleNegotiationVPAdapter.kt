package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ViewHolderGeneral
import com.venturessoft.human.humanrhdapp.databinding.ItemVpTimeBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.GeneralInformationFragment
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiation
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui.ScheduleNegotiationFragment.Companion.floatinfButton
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui.vm.NegociacionHorarioViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleNegotiationVPAdapter(
    private val fragment: Fragment,
    private var listMasterFilter: List<Int>,
    private var negociacionHorarioViewModel: NegociacionHorarioViewModel,
    private var viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ViewHolderGeneral<*>>(),ScheduleNegotiationAdapter.OnClickListener{

    interface OnClickListener{
        fun onClick(item: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGeneral<*> {
        val itemBinding = ItemVpTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: ViewHolderGeneral<*>, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind(listMasterFilter[position])
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int = listMasterFilter.size
    private inner class ViewHolder(
        val binding: ItemVpTimeBinding
    ) : ViewHolderGeneral<Int>(binding.root) {
        override fun bind(listaMaestroReloj: Int) {
            negociacionHorarioViewModel.dataScheduleNegotiation.observe(viewLifecycleOwner){dataResponce->
                if(dataResponce != null){
                    if (dataResponce.slista.isNotEmpty()){
                        binding.tvDataEmpty.root.isVisible = false
                        binding.rvGeneralInformation.isVisible = true
                      //  binding.rvGeneralInformation.adapter = ScheduleNegotiationAdapter(dataResponce.slista,this@ScheduleNegotiationVPAdapter)
                        binding.rvGeneralInformation.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                floatinfButton.value = dy <= 0
                            }
                        })
                    }else{
                        binding.rvGeneralInformation.isVisible = false
                        binding.tvDataEmpty.root.isVisible = true
                    }
                }else{
                    binding.rvGeneralInformation.isVisible = false
                    binding.tvDataEmpty.root.isVisible = false
                }
            }
        }
    }
    override fun onClick(scheduleNegotiation: ScheduleNegotiation) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.DATA_KEY,scheduleNegotiation)
        fragment.findNavController().navigate(R.id.to_scheduleNegotiationDetailsFragment,bundle)
    }
}