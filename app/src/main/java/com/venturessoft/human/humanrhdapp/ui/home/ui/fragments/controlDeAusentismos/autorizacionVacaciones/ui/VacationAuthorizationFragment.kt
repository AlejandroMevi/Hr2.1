package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.databinding.FragmentVacationAuthorizationBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.SaldoVacaciones
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.SecuenciasProgramadas
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.VacationAutthorizationItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models.ItemItemBusqeda
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm.ListEmployeeViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui.PreauthorizationAdapter
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.util.Calendar
import java.util.Locale

class VacationAuthorizationFragment : Fragment(), VacationAuthorizationAdapter.OnClickListener {

    private lateinit var binding: FragmentVacationAuthorizationBinding
    private val calendar: Calendar = Calendar.getInstance(Locale(Constants.LENGUAGEes, Constants.COUNTRYES))
    private var mainInterface: MainInterface? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVacationAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        binding.rvVacationsAuthorization.adapter = VacationAuthorizationAdapter(User.listUsuFalse, this)
        binding.btnDate.setOnClickListener {
            showCalendarMonthYear()
        }
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

    @SuppressLint("SetTextI18n")
    private fun setDate() {
        binding.btnDate.text = Utilities.getDateFromMiliseconds(calendar.timeInMillis, "MMMM / yyyy")
        if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.YEAR) == 2023){
            binding.tvDataEmpty.root.visibility = View.INVISIBLE
            binding.rvVacationsAuthorization.visibility = View.VISIBLE
        }else{
            binding.rvVacationsAuthorization.visibility = View.INVISIBLE
            binding.tvDataEmpty.root.visibility = View.VISIBLE
        }
    }
    override fun onClick(vacationAutthorizationItem: ItemItem) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.DATA_KEY,vacationAutthorizationItem)
        findNavController().navigate(R.id.to_vacationAuthorizationDetailsFragment,bundle)
    }

    override fun onResume() {
        super.onResume()
        mainInterface?.setTextToolbar(getString(R.string.submenu_22))
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