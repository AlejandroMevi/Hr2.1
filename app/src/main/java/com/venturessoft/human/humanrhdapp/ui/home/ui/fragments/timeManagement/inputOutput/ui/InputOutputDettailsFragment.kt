package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.ui

import android.annotation.SuppressLint
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
import com.venturessoft.human.humanrhdapp.databinding.FragmentInputOutputDettailsBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog.Companion.station
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog.Companion.status
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.AddInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.EditInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutput
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.ui.vm.InputOutputVM
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.DATA_KEY
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.changeDateFormat

class InputOutputDettailsFragment : Fragment() {

    private lateinit var binding: FragmentInputOutputDettailsBinding
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val inputOutputVM: InputOutputVM by activityViewModels()
    private var inputOutput: InputOutput?= null
    private lateinit var spinnerCatalog: SpinnerCatalog
    private var pickers: Pickers = Pickers()
    private var mainInterface: MainInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            inputOutput = bundle.getSerializable(DATA_KEY) as InputOutput
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInputOutputDettailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
        setHintSpinner()
        welcomeVM.idMenu.observe(viewLifecycleOwner) { dataUser ->
            HeaderUser(requireContext(),dataUser,binding.headerUser)
            if (inputOutput != null){
                binding.section.text = inputOutput?.sec.toString()
                binding.turn.text = inputOutput?.turno
                binding.concept.text = inputOutput?.concepto.toString()
                binding.category.text = "Ninguno"
                binding.departament.text = inputOutput?.departamento
                binding.vt1.text = inputOutput?.vt1
                binding.vt2.text = inputOutput?.vt2
                binding.vt3.text = inputOutput?.vt3
                binding.vt4.text = inputOutput?.vt4
                binding.cause.text = "Ninguno"
                binding.etDate.editText.setText(inputOutput?.fecha)
                binding.etInput.editText.setText(inputOutput?.entrada)
                binding.etOutput.editText.setText(inputOutput?.salida)
                binding.etDate.root.isEnabled = false
                binding.spStatus.root.isEnabled = false
            }else{
                binding.save.button.text = getString(R.string.save)
                binding.etDate.editText.setText(Utilities.getDateLocal())
                binding.etInput.editText.setText(Utilities.getTimeLocal())
                binding.etOutput.editText.setText(Utilities.getTimeLocal())
            }
            pickers.dataEditTextFrom( binding.etDate.editText.text.toString(),"dd/MM/yyyy","dd-MM-yyyy",1)
            spinnerCatalog.getCatalogStation(binding.spStation.spinner,inputOutput?.estacion)
            spinnerCatalog.getCatalogStatus(binding.spStatus.spinner,inputOutput?.status)

            binding.etDate.editText.setOnClickListener {
                pickers.dataPickerFrom(
                    this,
                    binding.etDate.editText, 1,"dd-MM-yyyy"
                )
            }
            binding.etInput.editText.setOnClickListener {
                pickers.timePickerText(requireActivity(), binding.etInput.editText, 1)
            }
            binding.etOutput.editText.setOnClickListener {
                pickers.timePickerText(requireActivity(), binding.etOutput.editText, 2)
            }
            binding.save.button.setOnClickListener {
                validateSpinners(dataUser)
            }
        }
    }
    private fun validateSpinners(dataUser: MenuModel) {
        if( station == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione una Estacion")
            return
        }
        if( status == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione un Estatus")
            return
        }
        if (inputOutput != null){
            editInputOutput(dataUser)
        }else{
            inserInputOutput(dataUser)
        }
        statusObserve()
    }
    private fun inserInputOutput(dataUser: MenuModel) {
        val addInputOutput = AddInputOutputRequest(
            cia = User.numCia.toString(),
            numEmp = dataUser.numEmp,
            fecha = Pickers.fechaInicio,
            entrada = binding.etInput.editText.text.toString(),
            salida = binding.etOutput.editText.text.toString(),
            estacion = station,
            usuario = User.usuario,
            status = status
        )
        inputOutputVM.addInputOutput(addInputOutput)
    }
    private fun editInputOutput(dataUser: MenuModel){
        val editInputOutput = EditInputOutputRequest()
        editInputOutput.entrada = binding.etInput.editText.text.toString()
        editInputOutput.salida = binding.etOutput.editText.text.toString()
        editInputOutput.estacion = station
        editInputOutput.usuario = User.usuario
        val fecha = binding.etDate.editText.text.toString()
        inputOutputVM.editInputOutput(User.numCia.toString(), dataUser.numEmp, changeDateFormat(fecha, "dd/MM/yyyy", "dd-MM-yyyy"), inputOutput?.sec.toString(),editInputOutput)
    }
    private fun statusObserve() {
        inputOutputVM.status.observe(viewLifecycleOwner) { status ->
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
        inputOutputVM.dataEntradasSalidas.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val bundle = Bundle()
                    bundle.putInt(Constants.OPCION, 5)
                    findNavController().navigate(R.id.to_succesFragment, bundle)
                }
            }
        }
    }
    private fun setHintSpinner(){
        binding.spStation.inputLayout.hint = "Estacion"
        binding.spStatus.inputLayout.hint = "Estatus"
        binding.etDate.inputLayout.hint = getString(R.string.fecha1)
        binding.etInput.inputLayout.hint = getString(R.string.fpHoraInicio)
        binding.etOutput.inputLayout.hint = getString(R.string.fpHoraFin)
    }
    private fun clearService() {
        inputOutputVM.status.removeObservers(viewLifecycleOwner)
        inputOutputVM.status.value = null
        inputOutputVM.dataEntradasSalidas.value = null
        mainInterface?.showLoading(false)
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