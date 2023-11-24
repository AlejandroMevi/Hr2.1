package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.databinding.FragmentVacationAuthorizationBinding
import com.venturessoft.human.humanrhdapp.databinding.FragmentVacationAuthorizationDetailsBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.SaldoVacaciones
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.SecuenciasProgramadas
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.autorizacionVacaciones.data.models.VacationAutthorizationItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models.ItemItemBusqeda
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutput
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants

class VacationAuthorizationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentVacationAuthorizationDetailsBinding
    private var vacationAutthorizationItem: ItemItem?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVacationAuthorizationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            vacationAutthorizationItem = bundle.getSerializable(Constants.DATA_KEY) as ItemItem
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (vacationAutthorizationItem != null){
            val dataUser = MenuModel(
                numEmp = vacationAutthorizationItem?.numEmp.toString(),
                nombreCompleto = vacationAutthorizationItem?.nombreCompleto.toString(),
                fotografia = vacationAutthorizationItem?.fotografia.toString(),
                puesto = vacationAutthorizationItem?.puesto.toString()
            )
            HeaderUser(requireContext(),dataUser,binding.headerUser)
            binding.tvDateStart.text = "25/10/2023"
            binding.tvDateEnd.text = "26/10/2023"
            binding.tvPrepaid.text = "No"
            binding.tvSequencePayment.text = "No"
            binding.tvDays.text = "1"
            binding.tvDateStart2.text = "26/10/2023"
            binding.tvDateEnd2.text = "26/10/2023"
            binding.tvDays2.text = "1"
            binding.tvYear2.text = "2023"
            binding.tvPeriod.text = "2023"
            binding.tvDays3.text = "7"
            binding.tvAdditionalDays.text = "0"
            binding.tvDueDate.text = "20/12/2024"
        }

        binding.btnAcept.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.OPCION, 33)
            findNavController().navigate(R.id.to_succesFragment, bundle)
        }

        binding.btnCancel.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constants.OPCION, 33)
            findNavController().navigate(R.id.to_succesFragment, bundle)
        }
    }
}