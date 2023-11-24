package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentPreautorizacionTiemposBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.PreautorizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.TiempoExtra
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui.vm.PreautorizacionVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui.ScheduleNegotiationAdapter
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.COUNTRYES
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.LENGUAGEes
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.util.*
import kotlin.collections.ArrayList

class PreautorizacionTiemposFragment : Fragment(),PreauthorizationAdapter.OnClickListener {

    private lateinit var binding: FragmentPreautorizacionTiemposBinding
    private val calendar: Calendar = Calendar.getInstance(Locale(LENGUAGEes, COUNTRYES))
    private var mainInterface: MainInterface? = null
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private val preautorizacionVM: PreautorizacionVM by activityViewModels()
    private val preautorizacionRequest = PreautorizacionRequest()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPreautorizacionTiemposBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preautorizacionRequest.cia = User.numCia.toInt()
        welcomeVM.idMenu.observeOnce(viewLifecycleOwner){employe->
            preautorizacionRequest.numEmp = employe.numEmp.toInt()
            setDate()
            statusObserve()
            binding.rvExtratime.layoutManager = LinearLayoutManager(requireContext())
            binding.rvExtratime.setHasFixedSize(true)
            getDataPreautorizacion()
        }
        binding.btnDate.setOnClickListener {
            showCalendarMonthYear()
        }

        binding.floating.setOnClickListener {
            findNavController().navigate(R.id.to_preauthorizationDetailsFragment)
        }

        binding.rvExtratime.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0){
                    binding.floating.shrink()
                } else {
                    binding.floating.extend()
                }
            }
        })
    }
    private fun showCalendarMonthYear() {
        val builder = MonthPickerDialog.Builder(context, { _, _ -> }, Calendar.YEAR, Calendar.MONTH)
        builder
            .setActivatedMonth(calendar.get(Calendar.MONTH))
            .setMinYear(calendar.get(Calendar.YEAR) - 5)
            .setActivatedYear(calendar.get(Calendar.YEAR))
            .setMaxYear(calendar.get(Calendar.YEAR) + 5)
            .setMinMonth(Calendar.JANUARY)
            .setTitle("Seleccione una Fecha")
            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
            .setOnMonthChangedListener { month ->
                calendar.set(Calendar.MONTH, month)
                setDate()
            }.setOnYearChangedListener { year ->
                calendar.set(Calendar.YEAR, year)
                setDate()
            }.build().show()
    }

    private fun getDataPreautorizacion(){
        preautorizacionVM.dataPreauthorization.observe(viewLifecycleOwner){preautorizacion->
            if (preautorizacion?.stiempoExtra != null && preautorizacion.stiempoExtra.isNotEmpty()) {
                val list = Utilities.orderDatesPreauthorization(preautorizacion.stiempoExtra as ArrayList<TiempoExtra>,"dd/MM/yyyy")
                binding.rvExtratime.adapter = PreauthorizationAdapter(list,this)
                binding.tvDataEmpty.root.visibility = View.INVISIBLE
                binding.rvExtratime.visibility = View.VISIBLE
            }else{
                binding.rvExtratime.adapter = PreauthorizationAdapter(listOf(),this)
                binding.rvExtratime.visibility = View.INVISIBLE
                binding.tvDataEmpty.root.visibility = View.VISIBLE
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setDate() {
        binding.btnDate.text = Utilities.getDateFromMiliseconds(calendar.timeInMillis, "MMMM / yyyy")
        preautorizacionRequest.anio = calendar.get(Calendar.YEAR)
        preautorizacionRequest.mes = calendar.get(Calendar.MONTH)+1
        preautorizacionVM.preautorizacionTiempos(preautorizacionRequest)
        getDataPreautorizacion()
    }
    private fun statusObserve() {
        preautorizacionVM.status.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }
                    is ApiResponceStatus.Success -> {
                        clearService()
                    }
                    is ApiResponceStatus.Error -> {
                        clearService()
                    }
                }
            }
        }
    }
    private fun clearService() {
        preautorizacionVM.status.removeObservers(viewLifecycleOwner)
        preautorizacionVM.dataPreauthorization.removeObservers(viewLifecycleOwner)
        preautorizacionVM.status.value = null
        mainInterface?.showLoading(false)
    }

    override fun onClick(tiempoExtra: TiempoExtra) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.DATA_KEY,tiempoExtra)
        findNavController().navigate(R.id.to_preauthorizationDetailsFragment,bundle)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInterface) {
            mainInterface = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        mainInterface = null
    }
}