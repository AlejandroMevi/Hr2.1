package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentNegociacionHorariosBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.DateIndicator
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.DateIndicator.Companion.monthData
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.DateIndicator.Companion.yearData
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.SwipeToDeleteCallback
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiation
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui.vm.NegociacionHorarioViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import java.util.*

class ScheduleNegotiationFragment : Fragment(),ScheduleNegotiationAdapter.OnClickListener {

    lateinit var binding: FragmentNegociacionHorariosBinding
    private val calendar: Calendar = Calendar.getInstance(Locale(Constants.LENGUAGEes, Constants.COUNTRYES))
    private var mainInterface: MainInterface? = null
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    internal val negociacionHorarioViewModel: NegociacionHorarioViewModel by activityViewModels()
    private var userData = MenuModel()
    private var listScheduleNegotiation = mutableListOf<ScheduleNegotiation>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNegociacionHorariosBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object{
        var floatinfButton = MutableLiveData<Boolean>()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateIndicator = DateIndicator(requireContext(), binding.btnDate, binding.btnBack, binding.btnNext)
        dateIndicator.setDate(calendar.get(Calendar.MONTH))
        floatinfButton.observe(viewLifecycleOwner){
            if (it){
                binding.floating.extend()
            }else{
                binding.floating.shrink()
            }
        }
        welcomeVM.idMenu.observeOnce(viewLifecycleOwner){user->
            userData = user
            negociacionHorarioViewModel.getScheduleNegotiation(user.numEmp.toLong(),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR))
            statusObserve()
        }
        negociacionHorarioViewModel.dataScheduleNegotiation.observe(viewLifecycleOwner){dataResponce->
            if (dataResponce?.slista != null && dataResponce.slista.isNotEmpty()){
                binding.tvDataEmpty.root.visibility = View.INVISIBLE
                binding.rvGeneralInformation.visibility = View.VISIBLE
                listScheduleNegotiation.addAll(dataResponce.slista)
                binding.rvGeneralInformation.adapter = ScheduleNegotiationAdapter(dataResponce.slista,this)
                binding.rvGeneralInformation.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        floatinfButton.value = dy <= 0
                    }
                })
                deleteAndUndo()
            }else{
                binding.rvGeneralInformation.visibility = View.INVISIBLE
                binding.tvDataEmpty.root.visibility = View.VISIBLE
            }
        }
        binding.btnDate.setOnClickListener {
            dateIndicator.createDialogWithoutDateField {
                negociacionHorarioViewModel.getScheduleNegotiation(userData.numEmp.toLong(), monthData + 1, yearData)
            }
        }
        binding.floating.setOnClickListener {
            findNavController().navigate(R.id.to_scheduleNegotiationDetailsFragment)
        }
        binding.btnBack.setOnClickListener {
            changeDate(false,dateIndicator)
        }
        binding.btnNext.setOnClickListener {
            changeDate(true, dateIndicator)
        }
    }
    private fun changeDate(isNext: Boolean, dateIndicator: DateIndicator){
        statusObserve()
        if (isNext){
            dateIndicator.setDate(monthData+1)
        }else{
            dateIndicator.setDate(monthData-1)
        }
        negociacionHorarioViewModel.getScheduleNegotiation(userData.numEmp.toLong(),monthData+1,yearData)
    }
    internal fun statusObserve() {
        negociacionHorarioViewModel.status.observe(viewLifecycleOwner) { status ->
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
    override fun onClick(scheduleNegotiation: ScheduleNegotiation) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.DATA_KEY,scheduleNegotiation)
        findNavController().navigate(R.id.to_scheduleNegotiationDetailsFragment,bundle)
    }
    internal fun clearService() {
        negociacionHorarioViewModel.status.removeObservers(viewLifecycleOwner)
        negociacionHorarioViewModel.dataGeneralResponse.removeObservers(viewLifecycleOwner)
        negociacionHorarioViewModel.status.value = null
        negociacionHorarioViewModel.dataGeneralResponse.value = null
        mainInterface?.showLoading(false)
    }
    private fun deleteAndUndo() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id: Int = (viewHolder as ScheduleNegotiationAdapter.ViewHolder).absoluteAdapterPosition
                statusObserve()
                negociacionHorarioViewModel.deleteScheduleNegotiation(userData.numEmp.toLong(), Utilities.changeDateFormat(listScheduleNegotiation[id].fechaInicio, "dd/MM/yyyy", "yyyy-MM-dd"),monthData +1 ,yearData)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvGeneralInformation)
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
    override fun onDestroy() {
        super.onDestroy()
        negociacionHorarioViewModel.dataScheduleNegotiation.value = null
    }
}
