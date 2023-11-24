package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui

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
import com.venturessoft.human.humanrhdapp.core.Pickers.Companion.fechaFin
import com.venturessoft.human.humanrhdapp.core.Pickers.Companion.fechaInicio
import com.venturessoft.human.humanrhdapp.databinding.FragmentScheduleNegotiationDetailsBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiation
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui.vm.NegociacionHorarioViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class ScheduleNegotiationDetailsFragment : Fragment() {

    private lateinit var binding:FragmentScheduleNegotiationDetailsBinding
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val negociacionHorarioViewModel: NegociacionHorarioViewModel by activityViewModels()
    private lateinit var spinnerCatalog: SpinnerCatalog
    private var scheduleNegotiation: ScheduleNegotiation? = null
    private var pickers: Pickers = Pickers()
    private val scheduleNegotiationRequest = ScheduleNegotiationRequest()
    private var mainInterface: MainInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            scheduleNegotiation = bundle.getSerializable(Constants.DATA_KEY) as ScheduleNegotiation
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleNegotiationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHintSpinner()
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
        welcomeVM.idMenu.observe(viewLifecycleOwner) { dataUser->
            HeaderUser(requireContext(),dataUser,binding.headerUser)
            scheduleNegotiationRequest.cia = User.numCia.toString()
            scheduleNegotiationRequest.numEmp = dataUser.numEmp
            scheduleNegotiationRequest.usuario = User.usuario
            if (scheduleNegotiation !=null){
                spinnerCatalog.getCatalogoDepartament(binding.spDep.spinner,scheduleNegotiation?.idDepartamento?.replace("\\s".toRegex(), ""))
                spinnerCatalog.getCatalogoCategory(binding.spCategory.spinner,scheduleNegotiation?.idCategoria)
                spinnerCatalog.getCatalogoZonaService(binding.spZone.spinner,scheduleNegotiation?.idZona.toString())
                if(scheduleNegotiation?.idRol != 0){
                    binding.rgType.check(R.id.rb_role)
                    spinnerCatalog.getCatalogoRolService(binding.spRol.spinner,scheduleNegotiation?.idRol)
                    isEnableDays(false)
                }
                if(scheduleNegotiation?.idTurno != 0){
                    binding.rgType.check(R.id.rb_shift)
                    spinnerCatalog.getCatalogoTurnoService(binding.spRol.spinner,scheduleNegotiation?.idTurno)
                }
                binding.tvTitle.text = getString(R.string.label_edit_negociacion)
                binding.save.button.text = getString(R.string.edit)
                binding.etInput.editText.setText(scheduleNegotiation?.fechaInicio)
                binding.etOutput.editText.setText(scheduleNegotiation?.fechaFin)
                binding.cbLun.isChecked = scheduleNegotiation?.lun == "S"
                binding.cbMar.isChecked = scheduleNegotiation?.mar == "S"
                binding.cbMie.isChecked = scheduleNegotiation?.mie == "S"
                binding.cbJue.isChecked = scheduleNegotiation?.jue == "S"
                binding.cbVie.isChecked = scheduleNegotiation?.vie == "S"
                binding.cbSab.isChecked = scheduleNegotiation?.sab == "S"
                binding.cbDom.isChecked = scheduleNegotiation?.dom == "S"
                binding.etInput.root.isEnabled = false
                binding.etOutput.root.isEnabled = false
            }else{
                isEnableDays(false)
                spinnerCatalog.getCatalogoDepartament(binding.spDep.spinner)
                spinnerCatalog.getCatalogoCategory(binding.spCategory.spinner)
                spinnerCatalog.getCatalogoZonaService(binding.spZone.spinner)
                spinnerCatalog.getCatalogoRolService(binding.spRol.spinner)
                binding.tvTitle.text = getString(R.string.label_negociacion)
                binding.save.button.text = getString(R.string.save)
                binding.etInput.editText.setText(Utilities.getDateLocal())
                binding.etOutput.editText.setText(Utilities.getDateLocal())
            }
            pickers.dataEditTextFrom( binding.etInput.editText.text.toString(),"dd/MM/yyyy","yyyy-MM-dd",1)
            pickers.dataEditTextFrom( binding.etOutput.editText.text.toString(),"dd/MM/yyyy","yyyy-MM-dd",2)
        }
        binding.rgType.setOnCheckedChangeListener { radioGroup, _ ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rb_role ->{
                    isEnableDays(false)
                    spinnerCatalog.getCatalogoRolService(binding.spRol.spinner)
                    binding.spRol.inputLayout.hint = getString(R.string.label_rol)
                }
                R.id.rb_shift ->{
                    isEnableDays(true)
                    spinnerCatalog.getCatalogoTurnoService(binding.spRol.spinner)
                    binding.spRol.inputLayout.hint = getString(R.string.label_turno)
                }
            }
        }
        binding.etInput.editText.setOnClickListener {
            pickers.dataPickerFrom(this, binding.etInput.editText, 1,"yyyy-MM-dd")
        }
        binding.etOutput.editText.setOnClickListener {
            pickers.dataPickerFrom(this, binding.etOutput.editText,0, "yyyy-MM-dd")
        }
        binding.save.button.setOnClickListener {
            validateSpinners()
        }
    }
    private fun validateSpinners(){
        if( SpinnerCatalog.departament == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione una opcion de Departamento")
            return
        }
        if( SpinnerCatalog.category == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione una categoria")
            return
        }
        if( SpinnerCatalog.zona == 0L ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione una Zona")
            return
        }
        if (binding.rbRole.isChecked){
            if (SpinnerCatalog.rol == 0){
                Utilities.showToastyGeneral(requireContext(), "Seleccione un Rol")
                return
            }
        }
        if (binding.rbShift.isChecked){
            if (SpinnerCatalog.turno == 0){
                Utilities.showToastyGeneral(requireContext(), "Seleccione un Turno")
                return
            }
        }
        statusObserve()
        if (scheduleNegotiation !=null){
            negociacionHorarioViewModel.putScheduleNegotiation(scheduleNegotiationRequest.numEmp.toLong(), Utilities.changeDateFormat(scheduleNegotiation?.fechaInicio.toString(), "dd/MM/yyyy", "yyyy-MM-dd"),getData())
        }else{
            negociacionHorarioViewModel.postScheduleNegotiation(getData())
        }
    }
    private fun isEnableDays(isEnable:Boolean){
        binding.cbLun.isEnabled = isEnable
        binding.cbMar.isEnabled = isEnable
        binding.cbMie.isEnabled = isEnable
        binding.cbJue.isEnabled = isEnable
        binding.cbVie.isEnabled = isEnable
        binding.cbSab.isEnabled = isEnable
        binding.cbDom.isEnabled = isEnable
        if (!isEnable){
            binding.cbLun.isChecked = false
            binding.cbMar.isChecked = false
            binding.cbMie.isChecked = false
            binding.cbJue.isChecked = false
            binding.cbVie.isChecked = false
            binding.cbSab.isChecked = false
            binding.cbDom.isChecked = false
        }
    }
    private fun setHintSpinner(){
        binding.spDep.inputLayout.hint = getString(R.string.label_departamento)
        binding.spCategory.inputLayout.hint = getString(R.string.label_categorias)
        binding.spZone.inputLayout.hint = getString(R.string.label_zona)
        binding.spRol.inputLayout.hint = getString(R.string.label_rol)
        binding.etInput.inputLayout.hint = getString(R.string.label_fecha_inicio)
        binding.etOutput.inputLayout.hint = getString(R.string.label_fecha_fin)
    }
    private fun getData(): ScheduleNegotiationRequest {
        scheduleNegotiationRequest.fechaInicio = fechaInicio
        scheduleNegotiationRequest.fechaFin = fechaFin
        scheduleNegotiationRequest.rolTurno = SpinnerCatalog.rol.toString()
        scheduleNegotiationRequest.turno = SpinnerCatalog.turno.toString()
        scheduleNegotiationRequest.dom = if(binding.cbDom.isChecked)"S" else "N"
        scheduleNegotiationRequest.lun = if(binding.cbLun.isChecked)"S" else "N"
        scheduleNegotiationRequest.mar = if(binding.cbMar.isChecked)"S" else "N"
        scheduleNegotiationRequest.mie = if(binding.cbMie.isChecked)"S" else "N"
        scheduleNegotiationRequest.jue = if(binding.cbJue.isChecked)"S" else "N"
        scheduleNegotiationRequest.vie = if(binding.cbVie.isChecked)"S" else "N"
        scheduleNegotiationRequest.sab = if(binding.cbSab.isChecked)"S" else "N"
        scheduleNegotiationRequest.categoria = SpinnerCatalog.category
        scheduleNegotiationRequest.zona = SpinnerCatalog.zona.toString()
        scheduleNegotiationRequest.departamento = SpinnerCatalog.departament
        return scheduleNegotiationRequest
    }
    private fun statusObserve() {
        negociacionHorarioViewModel.dataGeneralResponse.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val bundle = Bundle()
                    bundle.putInt(Constants.OPCION, 8)
                    findNavController().navigate(R.id.to_succesFragment, bundle)
                }
            }
        }
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
                        val errorCode = Utilities.textcode(status.messageId, requireContext())
                        Utilities.showToastyGeneral(requireContext(), errorCode)
                    }
                }
            }
        }
    }
    private fun clearService() {
        negociacionHorarioViewModel.status.removeObservers(viewLifecycleOwner)
        negociacionHorarioViewModel.dataGeneralResponse.removeObservers(viewLifecycleOwner)
        negociacionHorarioViewModel.status.value = null
        negociacionHorarioViewModel.dataGeneralResponse.value = null
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