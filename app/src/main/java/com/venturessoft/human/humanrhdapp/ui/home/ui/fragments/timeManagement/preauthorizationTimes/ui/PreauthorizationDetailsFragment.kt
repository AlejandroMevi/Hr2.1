package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.HeaderUser
import com.venturessoft.human.humanrhdapp.core.Pickers
import com.venturessoft.human.humanrhdapp.databinding.FragmentPreauthorizationDetailsBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog.Companion.category
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog.Companion.departament
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog.Companion.zona
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreaturoizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.TiempoExtra
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui.vm.PreautorizacionVM
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce

class PreauthorizationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPreauthorizationDetailsBinding
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val preautorizacionVM: PreautorizacionVM by activityViewModels()
    private var pickers: Pickers = Pickers()
    private lateinit var spinnerCatalog: SpinnerCatalog
    private val addPreautorizacionRequest = AddPreautorizacionRequest()
    private val editPreaturoizacionRequest = EditPreaturoizacionRequest()
    private var tiempoExtra: TiempoExtra? = null
    private var mainInterface: MainInterface? = null
    private var buttonType = true
    private var horaAnterior = ""
    private var categoriaAnt = ""
    private var departamentoAnt = ""
    private var zonaAnt = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            tiempoExtra = bundle.getSerializable(Constants.DATA_KEY) as TiempoExtra
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreauthorizationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHintSpinner()
        loadData()
        loadButtons()
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
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

    private fun loadData() {
        welcomeVM.idMenu.observe(viewLifecycleOwner) { dataUser ->
            HeaderUser(requireContext(), dataUser, binding.headerUser)
            if (tiempoExtra != null) {
                editPreaturoizacionRequest.cia = User.numCia.toString()
                editPreaturoizacionRequest.numEmp = dataUser.numEmp
                editPreaturoizacionRequest.usuario = User.usuario
                binding.tvTitle.text = getString(R.string.label_edit_solicitud)
                binding.save.button.text = getString(R.string.edit)
                tiempoExtra?.fecha?.let {
                    binding.etDate.editText.setText(it)
                }
                tiempoExtra?.horaEntrada?.let {
                    binding.etInput.editText.setText(it)
                    horaAnterior = it
                }
                tiempoExtra?.horaSalida?.let {
                    binding.etOutput.editText.setText(it)
                }
               // binding.etTime.setText(tiempoExtra?.horas)
                buttonType = false
                spinnerCatalog.getCatalogoCategory(binding.spCategory.spinner, tiempoExtra?.idCategoria)
                categoriaAnt= tiempoExtra?.idCategoria.toString()
                spinnerCatalog.getCatalogoZonaService(binding.spZone.spinner, tiempoExtra?.idZona.toString())
                zonaAnt = tiempoExtra?.idZona.toString()
                spinnerCatalog.getCatalogoDepartament(binding.spDep.spinner, tiempoExtra?.idDepartamento?.trim())
                departamentoAnt = tiempoExtra?.idDepartamento?.trim().toString()
            } else {
                addPreautorizacionRequest.cia = User.numCia.toString()
                addPreautorizacionRequest.numEmp = dataUser.numEmp
                addPreautorizacionRequest.usuario = User.usuario
                buttonType = true
                binding.tvTitle.text = getString(R.string.label_solicitud)
                binding.save.button.text = getString(R.string.save)
                binding.etDate.editText.setText(Utilities.getDateLocal())
                binding.etInput.editText.setText(Utilities.getTimeLocal())
                binding.etOutput.editText.setText(Utilities.getTimeLocal())
                spinnerCatalog.getCatalogoCategory(binding.spCategory.spinner)
                spinnerCatalog.getCatalogoZonaService(binding.spZone.spinner)
                spinnerCatalog.getCatalogoDepartament(binding.spDep.spinner)
            }
            pickers.dataEditTextFrom( binding.etDate.editText.text.toString(),"dd/MM/yyyy","yyyy-MM-dd",1)
        }
    }

    private fun loadButtons() {
        if (tiempoExtra != null) {
            binding.etDate.editText.isEnabled = false
        } else {
            binding.etDate.editText.setOnClickListener {
                pickers.dataPickerFrom(
                    this,
                    binding.etDate.editText, 1, "yyyy-MM-dd"
                )
            }
        }

        binding.etInput.editText.setOnClickListener {
            pickers.timePickerText(requireActivity(), binding.etInput.editText, 1)
        }
        binding.etOutput.editText.setOnClickListener {
            pickers.timePickerText(requireActivity(), binding.etOutput.editText, 2)
        }

        binding.save.button.setOnClickListener {
            val validate = checkFields()
            if (tiempoExtra != null) {
                editPreauthService()
            } else {
                if (validate == "true") {
                    addPreauthService()
                } else {
                    Utilities.showToastyGeneral(requireContext(), validate)
                }
            }
        }
    }
    private fun checkFields(): String {
        if (binding.etDate.editText.text.isNullOrEmpty()) {
            return getString(R.string.selecciona_fecha)
        }
        if (binding.etInput.editText.text.isNullOrEmpty()) {
            return getString(R.string.seleccionar_hora_inicial)
        }
        if (binding.etOutput.editText.text.isNullOrEmpty()) {
            return getString(R.string.seleccionar_hora_final)
        }
        if (binding.spCategory.spinner.text.toString() == "Ninguno") {
            return getString(R.string.seleccionar_categoria)
        }
        if (binding.spZone.spinner.text.toString() == "Ninguno") {
            return getString(R.string.seleccionar_zona)
        }
        if (binding.spDep.spinner.text.toString() == "Ninguno") {
            return getString(R.string.seleccionar_departamento)
        }
        return "true"
    }
    private fun addPreauthService() {
        statusObserve()
        addPreautorizacionRequest.fecha = Pickers.fechaInicio
        addPreautorizacionRequest.horaEnt = binding.etInput.editText.text.toString()
        addPreautorizacionRequest.horaSal = binding.etOutput.editText.text.toString()
        addPreautorizacionRequest.categoria = category
        addPreautorizacionRequest.departamento = departament
        addPreautorizacionRequest.zona = zona.toString()
        preautorizacionVM.addPreautorizacionTiempos(addPreautorizacionRequest)
        preautorizacionVM.dataAddPreauthorization.value = null
        preautorizacionVM.dataAddPreauthorization.observeOnce(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val bundle = Bundle()
                    bundle.putInt(Constants.OPCION, 7)
                    findNavController().navigate(R.id.to_succesFragment, bundle)
                }
            }
        }
    }

    private fun editPreauthService() {
        val fecha = binding.etDate.editText.text.toString()
        editPreaturoizacionRequest.fecha = Utilities.changeDateFormat(fecha, "dd/MM/yyyy", "yyyy-MM-dd")
        editPreaturoizacionRequest.horaEntAnt = horaAnterior
        editPreaturoizacionRequest.horaEnt = binding.etInput.editText.text.toString()
        editPreaturoizacionRequest.categoria = if (category.isEmpty() || category == ""){
            if(SpinnerCatalog.categorySelected == true) "" else categoriaAnt
        } else {
            category
        }
        editPreaturoizacionRequest.horaSal = binding.etOutput.editText.text.toString()
        editPreaturoizacionRequest.usuario = User.usuario
        editPreaturoizacionRequest.zona = if (zona.toString().isEmpty() || zona.toString() == "0"){
            if(SpinnerCatalog.zonaSelected == true) "" else zonaAnt
        } else {
            zona.toString()
        }
        editPreaturoizacionRequest.departamento = if (departament.isEmpty() || departament == ""){
            if(SpinnerCatalog.departamentSelected == true) "" else departamentoAnt
        } else {
            departament
        }
        editPreaturoizacionRequest.status = "A"

        preautorizacionVM.editPreautorizacionTiempos(editPreaturoizacionRequest)
        preautorizacionVM.dataEditPreauthorization.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val bundle = Bundle()
                    bundle.putInt(Constants.OPCION, 7)
                    findNavController().navigate(R.id.to_succesFragment, bundle)
                }
            }
        }
    }

    private fun setHintSpinner() {
        editStatusObserve()
        binding.spDep.inputLayout.hint = getString(R.string.label_departamento)
        binding.spCategory.inputLayout.hint = getString(R.string.label_categorias)
        binding.spZone.inputLayout.hint = getString(R.string.label_zona)
        binding.etDate.inputLayout.hint = getString(R.string.fecha1)
        binding.etInput.inputLayout.hint = getString(R.string.fpHoraInicio)
        binding.etOutput.inputLayout.hint = getString(R.string.fpHoraFin)
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
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showToastyGeneral(requireContext(), errorCode)
                    }
                }
            }
        }
    }

    private fun clearService() {
        preautorizacionVM.dataAddPreauthorization.removeObservers(viewLifecycleOwner)
        preautorizacionVM.status.removeObservers(viewLifecycleOwner)
        preautorizacionVM.dataAddPreauthorization.value = null
        preautorizacionVM.status.value = null
        mainInterface?.showLoading(false)
    }

    private fun editStatusObserve() {
        preautorizacionVM.statusEdit.observe(viewLifecycleOwner) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        mainInterface?.showLoading(true)
                    }

                    is ApiResponceStatus.Success -> {
                        editClearService()
                    }

                    is ApiResponceStatus.Error -> {
                        editClearService()
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showToastyGeneral(requireContext(), errorCode)
                    }
                }
            }
        }
    }

    private fun editClearService() {
        preautorizacionVM.dataEditPreauthorization.removeObservers(viewLifecycleOwner)
        preautorizacionVM.statusEdit.removeObservers(viewLifecycleOwner)
        preautorizacionVM.statusEdit.value = null
        mainInterface?.showLoading(false)
    }

}