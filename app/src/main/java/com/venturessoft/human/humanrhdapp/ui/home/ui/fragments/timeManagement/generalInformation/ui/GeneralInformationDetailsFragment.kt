package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui

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
import com.venturessoft.human.humanrhdapp.databinding.FragmentTimeManagmentBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.HomeActivity
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm.WelcomeFragmentViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.InfoGenralRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.ListaMaestroReloj
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.vm.GeneralInformationVM
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants.Companion.DATA_KEY
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.changeDateFormat
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.formatYearMonthDay
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.getDateLocal
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.showToastyGeneral

class GeneralInformationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTimeManagmentBinding
    private val welcomeVM: WelcomeFragmentViewModel by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val generalInformationVM: GeneralInformationVM by activityViewModels()
    private lateinit var spinnerCatalog: SpinnerCatalog
    private var listaMaestroReloj: ListaMaestroReloj? = null
    private var pickers: Pickers = Pickers()
    private val dataRequest = InfoGenralRequest()
    private var mainInterface: MainInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            listaMaestroReloj = bundle.getSerializable(DATA_KEY) as ListaMaestroReloj
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeManagmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHintSpinner()
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
        welcomeVM.idMenu.observe(viewLifecycleOwner) { dataUser ->
            HeaderUser(requireContext(), dataUser, binding.headerUser)
            dataRequest.numEmp = dataUser.numEmp
            if (listaMaestroReloj != null) {
                spinnerCatalog.getCatalogoCalendar(binding.spCalendar.spinner, listaMaestroReloj!!.calendario)
                if (listaMaestroReloj!!.rolHorario != 0) {
                    binding.rgType.check(R.id.rb_role)
                    spinnerCatalog.getCatalogoRolService(binding.spRol.spinner, listaMaestroReloj!!.rolHorario)
                    isEnableDays(false)
                }
                if (listaMaestroReloj!!.turno != 0) {
                    binding.rgType.check(R.id.rb_shift)
                    spinnerCatalog.getCatalogoTurnoService(binding.spRol.spinner, listaMaestroReloj!!.turno)
                }
                binding.tvTitle.text = getString(R.string.label_edit_solicitud)
                binding.save.button.text = getString(R.string.edit)
                binding.etInput.editText.setText(formatYearMonthDay(listaMaestroReloj!!.fechaAplicacion).toString())
                binding.etBadge.setText(listaMaestroReloj!!.gafete.toString())
                binding.cbLun.isChecked = listaMaestroReloj!!.lun.contains("S")
                binding.cbMar.isChecked = listaMaestroReloj!!.mar.contains("S")
                binding.cbMie.isChecked = listaMaestroReloj!!.mie.contains("S")
                binding.cbJue.isChecked = listaMaestroReloj!!.jue.contains("S")
                binding.cbVie.isChecked = listaMaestroReloj!!.vie.contains("S")
                binding.cbSab.isChecked = listaMaestroReloj!!.sab.contains("S")
                binding.cbDom.isChecked = listaMaestroReloj!!.dom.contains("S")
                binding.etInput.root.isEnabled = false
            } else {
                isEnableDays(false)
                binding.etBadge.setText(dataUser.numEmp)
                spinnerCatalog.getCatalogoCalendar(binding.spCalendar.spinner)
                spinnerCatalog.getCatalogoRolService(binding.spRol.spinner)
                binding.tvTitle.text = getString(R.string.label_solicitud)
                binding.save.button.text = getString(R.string.save)
                binding.etInput.editText.setText(getDateLocal())
            }
        }
        binding.rgType.setOnCheckedChangeListener { radioGroup, _ ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rb_role -> {
                    SpinnerCatalog.rol = 0
                    isEnableDays(false)
                    spinnerCatalog.getCatalogoRolService(binding.spRol.spinner)
                    binding.spRol.inputLayout.hint = getString(R.string.label_rol)
                }

                R.id.rb_shift -> {
                    SpinnerCatalog.turno = 0
                    isEnableDays(true)
                    spinnerCatalog.getCatalogoTurnoService(binding.spRol.spinner)
                    binding.spRol.inputLayout.hint = getString(R.string.label_turno)
                }
            }
        }
        binding.etInput.editText.setOnClickListener {
            pickers.dataPickerFrom(
                this, binding.etInput.editText, 1,"dd-MM-yyyy"
            )
        }
        binding.save.button.setOnClickListener {
            validateSpinners()
        }
    }
    private fun validateSpinners(){
        if( SpinnerCatalog.calendar == 0L ){
            showToastyGeneral(requireContext(),"Seleccione una opcion de calendario")
            return
        }
        if (binding.rbRole.isChecked){
            if (SpinnerCatalog.rol == 0){
                showToastyGeneral(requireContext(),"Seleccione un Rol")
                return
            }
        }
        if (binding.rbShift.isChecked){
            if (SpinnerCatalog.turno == 0){
                showToastyGeneral(requireContext(),"Seleccione un Turno")
                return
            }
        }
        statusObserve()
        if (listaMaestroReloj!=null){
            val fecha = binding.etInput.editText.text.toString()
            generalInformationVM.editAdministrarMR(dataRequest.numEmp.toLong(), changeDateFormat(fecha, "dd/MM/yyyy", "yyyy-MM-dd"), getData())
        }else{
            generalInformationVM.addAdministrarMR(getData())
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
    private fun setHintSpinner() {
        binding.spCalendar.inputLayout.hint = getString(R.string.label_calendario)
        binding.spRol.inputLayout.hint = getString(R.string.label_rol)
        binding.etInput.inputLayout.hint = getString(R.string.label_fecha)
    }
    private fun getData(): InfoGenralRequest {
        dataRequest.cia = User.numCia.toString()
        dataRequest.fechaAplicacion = binding.etInput.editText.text.toString()
        dataRequest.gafete = binding.etBadge.text.toString()
        dataRequest.calendario = SpinnerCatalog.calendar.toString()
        dataRequest.rolHorario = if (binding.rbRole.isChecked) SpinnerCatalog.rol.toString() else "0"
        dataRequest.turno = if (binding.rbShift.isChecked) SpinnerCatalog.turno.toString() else "0"
        dataRequest.dom = if (binding.cbDom.isChecked) "S" else "N"
        dataRequest.lun = if (binding.cbLun.isChecked) "S" else "N"
        dataRequest.mar = if (binding.cbMar.isChecked) "S" else "N"
        dataRequest.mie = if (binding.cbMie.isChecked) "S" else "N"
        dataRequest.jue = if (binding.cbJue.isChecked) "S" else "N"
        dataRequest.vie = if (binding.cbVie.isChecked) "S" else "N"
        dataRequest.sab = if (binding.cbSab.isChecked) "S" else "N"
        dataRequest.cond1 = ""
        dataRequest.cond2 = ""
        dataRequest.cond3 = ""
        dataRequest.cond4 = ""
        dataRequest.cond5 = ""
        dataRequest.cond6 = ""
        dataRequest.cond7 = ""
        dataRequest.cond8 = ""
        dataRequest.cond9 = ""
        dataRequest.cond10 = ""
        dataRequest.status = ""
        dataRequest.usuario = User.usuario
        return dataRequest
    }
    private fun statusObserve() {
        generalInformationVM.dataResponce.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val bundle = Bundle()
                    bundle.putInt(Constants.OPCION, 6)
                    findNavController().navigate(R.id.to_succesFragment, bundle)
                }
            }
        }
        generalInformationVM.status.observe(viewLifecycleOwner) { status ->
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
                        showToastyGeneral(requireContext(),errorCode)
                    }
                }
            }
        }
    }
    private fun clearService() {
        generalInformationVM.status.removeObservers(viewLifecycleOwner)
        generalInformationVM.dataResponce.removeObservers(viewLifecycleOwner)
        generalInformationVM.status.value = null
        generalInformationVM.dataResponce.value = null
        mainInterface?.showLoading(false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInterface) {
            mainInterface = context
        }
    }
    override fun onResume() {
        super.onResume()
        if (listaMaestroReloj != null) {
            mainInterface?.showStations(listaMaestroReloj?.gafete ?: 0)
        }
    }
    override fun onPause() {
        super.onPause()
        HomeActivity.showStations.value = false
    }
    override fun onDetach() {
        super.onDetach()
        mainInterface = null
    }
}