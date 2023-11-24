package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.DialogFilterListEmploye.Companion.employeFilterSelect
import com.venturessoft.human.humanrhdapp.core.DialogFilterListEmploye.Companion.employeFilterSelectDetails
import com.venturessoft.human.humanrhdapp.core.SearchEmploye
import com.venturessoft.human.humanrhdapp.databinding.FragmentReportesEstacionesLibresBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.FilterStationFreeFragment.Companion.addInputOutput
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsEstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsFreeStationResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesEmpItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.vm.EstacionesLibresViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce

class ReportesEstacionesLibresFragment : Fragment(), EstacionesLibresAdapter.OnClickListener {

    private lateinit var binding: FragmentReportesEstacionesLibresBinding
    private lateinit var listaReportes: ArrayList<EstacionesEmpItem>
    private val freeStationVM: EstacionesLibresViewModel by activityViewModels()
    private var responseDetails: DetailsFreeStationResponse? = null
    private var mainInterface: MainInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportesEstacionesLibresBinding.inflate(inflater, container, false)
        mainInterface?.setTextToolbar(getString(R.string.registro_estaciones_libres))
        mainInterface?.showFilter(true,1)
        tokenServie()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        searchReport()
    }
    override fun onDestroy() {
        super.onDestroy()
        mainInterface?.showFilter(false,1)
        freeStationVM.listEmployeFilterSelect.value = null
        freeStationVM.detailsEmployeFilterSelect.value = null
        freeStationVM.activeFilters.value = false
        employeFilterSelectDetails.clear()
        employeFilterSelect.clear()
        addInputOutput?.apply {
            fechaInicio = null
            fechaFin = null
        }
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
    private fun tokenServie() {
        freeStationVM.dataToken.value = null
        freeStationVM.token("Basic SHVtYW46VCEyZURrVHdYNE1BaHNuWlNCZnBwWCpWelZ1a05T")
        freeStationVM.dataToken.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                freeStationServie(
                    addInputOutput?.fechaInicio?.ifEmpty { null },
                    addInputOutput?.fechaFin?.ifEmpty { null },
                    addInputOutput?.empls?.ifEmpty { null },
                    response.token
                )
            }
        }
        statusObserveToken()
    }

    private fun freeStationServie(
        fechaInicio: String? = null,
        fechaFin: String? = null,
        numEmp: List<Long>? = null,
        token: String
    ) {
        freeStationVM.dataFreeStation.value = null
        val estacionesLibresRequest =
            EstacionesLibresRequest(
                User.numCia.toString(),
                User.usuario,
                numEmp,
                fechaInicio,
                fechaFin
            )
        freeStationVM.freeStation(estacionesLibresRequest, token)
        freeStationVM.dataFreeStation.observeOnce(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.estacionesEmp!!.isNotEmpty()) {
                    binding.listEmployee.isVisible = true
                    binding.tvDataEmpty.root.isVisible = false
                    val list = java.util.ArrayList<EstacionesEmpItem>()
                    for (i in response.estacionesEmp!!.indices) {
                        val dataModel = EstacionesEmpItem()
                        dataModel.fechaChecada = response.estacionesEmp!![i]?.fechaChecada
                        dataModel.horaChecada = response.estacionesEmp!![i]?.horaChecada
                        dataModel.numEmp = response.estacionesEmp!![i]?.numEmp
                        dataModel.estacion = response.estacionesEmp!![i]?.estacion
                        dataModel.numCia = response.estacionesEmp!![i]?.numCia
                        dataModel.nombreEmp = response.estacionesEmp!![i]?.nombreEmp
                        dataModel.tipo = response.estacionesEmp!![i]?.tipo
                        list.add(dataModel)
                        listaReportes = list
                        setDataList(listaReportes)
                    }
                } else {
                    zeroState()
                }
            }
        }
        statusObserveFreeStation()
    }

    private fun setDataList(listaUsuarios: ArrayList<EstacionesEmpItem>) {
        binding.listEmployee.adapter =
            EstacionesLibresAdapter(listaUsuarios, this)
    }

    private fun zeroState() {
        binding.tvDataEmpty.root.isVisible = true
        binding.listEmployee.isVisible = false
    }

    private fun statusObserveToken() {
        freeStationVM.statusToken.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        println("TOKEN LOADING")
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceToken()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceToken()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Toast.makeText(requireActivity(), errorCode, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clearServiceToken() {
        freeStationVM.statusToken.value = null
        freeStationVM.dataToken.removeObservers(viewLifecycleOwner)
        freeStationVM.statusToken.removeObservers(viewLifecycleOwner)
    }

    private fun statusObserveFreeStation() {
        freeStationVM.statusFreeStation.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        binding.progressIn.isVisible = true
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceListaPermisos()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceListaPermisos()
                        zeroState()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Toast.makeText(requireActivity(), errorCode, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clearServiceListaPermisos() {
        freeStationVM.statusFreeStation.value = null
        freeStationVM.dataFreeStation.removeObservers(viewLifecycleOwner)
        freeStationVM.statusFreeStation.removeObservers(viewLifecycleOwner)
        binding.progressIn.isVisible = false
    }

    override fun onClick(estacionesEmpItem: EstacionesEmpItem) {
        tokenServieDetails(estacionesEmpItem)
    }

    private fun observers() {
        freeStationVM.activeFilters.observe(viewLifecycleOwner) { activeFilters ->
            if (activeFilters != null) {
                if (activeFilters) {
                    mainInterface?.showBadgeDrawable(activeFilters)
                } else {
                    mainInterface?.showBadgeDrawable(activeFilters)
                }
                tokenServie()
            }
        }
    }


    private fun tokenServieDetails(estacionesEmpItem: EstacionesEmpItem) {
        freeStationVM.dataToken.value = null
        freeStationVM.token("Basic SHVtYW46VCEyZURrVHdYNE1BaHNuWlNCZnBwWCpWelZ1a05T")
        freeStationVM.dataToken.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                detailsServices(response.token, estacionesEmpItem)
            }
        }
        statusObserveTokenDetails()
    }

    private fun statusObserveTokenDetails() {
        freeStationVM.statusToken.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                       println("TOKEN LOADING")
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceTokenDetails()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceTokenDetails()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Toast.makeText(requireActivity(), errorCode, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun clearServiceTokenDetails() {
        freeStationVM.statusToken.value = null
        freeStationVM.dataToken.removeObservers(viewLifecycleOwner)
        freeStationVM.statusToken.removeObservers(viewLifecycleOwner)
    }

    private fun detailsServices(token: String, estacionesEmpItem: EstacionesEmpItem) {
        freeStationVM.dataDetailsFreeStation.value = null
        val request =
            DetailsEstacionesLibresRequest(
                estacionesEmpItem.numCia.toString(),
                estacionesEmpItem.numEmp!!,
                User.usuario,
                estacionesEmpItem.fechaChecada.toString(),
                estacionesEmpItem.horaChecada.toString()
            )
        freeStationVM.detailsFreeStation(request, token)
        freeStationVM.dataDetailsFreeStation.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                responseDetails = response
                loadDetailsFragment(estacionesEmpItem, responseDetails)
            }
        }
        statusObserveDetails(estacionesEmpItem)
    }

    private fun statusObserveDetails(estacionesEmpItem: EstacionesEmpItem) {
        freeStationVM.statusDetailsFreeStation.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }

                    is ApiResponceStatus.Success -> {
                        clearServiceDetails()
                    }

                    is ApiResponceStatus.Error -> {
                        clearServiceDetails()
                        loadDetailsFragment(estacionesEmpItem, null)
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Toast.makeText(requireActivity(), errorCode, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun loadDetailsFragment(
        estacionesEmpItem: EstacionesEmpItem,
        r: DetailsFreeStationResponse? = null
    ) {
        val a = r?.detalle?.get(0)?.latitud?.toDouble()
        val b = r?.detalle?.get(0)?.longitud?.toDouble()
        val fullScreenDialogFragment = DetallesEstacionesLibresFragment(estacionesEmpItem, r, a, b)
        fullScreenDialogFragment.show(
            requireActivity().supportFragmentManager,
            "FullScreenDialogFragment"
        )
    }

    private fun clearServiceDetails() {
        freeStationVM.statusDetailsFreeStation.value = null
        freeStationVM.dataDetailsFreeStation.removeObservers(viewLifecycleOwner)
        freeStationVM.statusDetailsFreeStation.removeObservers(viewLifecycleOwner)
        mainInterface?.showLoading(false)
    }

    private fun searchReport(){
        val adapter = binding.listEmployee.adapter
        binding.etFilter.doOnTextChanged { text, _, _, _ ->
            if (binding.listEmployee.toString().contains(text!!)) {
                Toast.makeText(requireActivity(), "true", Toast.LENGTH_SHORT).show()
                println("DATOS"+binding.listEmployee.adapter.toString())
                println("DATOS a "+binding.listEmployee.toString())
            } else {
                Toast.makeText(requireActivity(), "false", Toast.LENGTH_SHORT).show()
            }
        }
    }
}