package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.DialogListEmploye
import com.venturessoft.human.humanrhdapp.core.Pickers
import com.venturessoft.human.humanrhdapp.core.SearchEmploye
import com.venturessoft.human.humanrhdapp.databinding.FragmentMassiveInputOutputDetailsBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter.Companion.employeSelectLD
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm.ListEmployeeViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.InputOutput
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.data.models.MassiveInputOutput
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities

class MassiveInputOutputDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMassiveInputOutputDetailsBinding
    private val listEmployeeViewModel = ListEmployeeViewModel()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private val searchEmploye = SearchEmploye(listEmployeeViewModel, this)
    private var mainInterface: MainInterface? = null
    private lateinit var spinnerCatalog: SpinnerCatalog
    private var pickers: Pickers = Pickers()
    private var inputOutput: MassiveInputOutput?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            inputOutput = bundle.getSerializable(Constants.DATA_KEY) as MassiveInputOutput
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMassiveInputOutputDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHintSpinner()
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
        val dialog = DialogListEmploye(this, searchEmploye, binding.btnAddEmploye,true)
        employeSelectLD.value = null
        if (inputOutput != null){
            binding.llEdit.isVisible = true
            binding.btnAddEmploye.isVisible = false
            binding.icEdit.isVisible = false
            binding.listNumEmployee.text = inputOutput!!.numEmple
            binding.listName.text = inputOutput!!.nombreCompleto
            binding.puesto.text = inputOutput!!.puesto
            binding.turno.text = inputOutput!!.estacion
            if (inputOutput!!.foto.contains("File not foundjava", ignoreCase = true) || inputOutput!!.foto.isEmpty()) {
                binding.listImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.user_icon_big))
            } else {
                val imagenBase64 = Base64.decode(inputOutput!!.foto, Base64.DEFAULT)
                val imagenconverBitmap = BitmapFactory.decodeByteArray(imagenBase64, 0, imagenBase64.size)
                binding.listImage.setImageBitmap(imagenconverBitmap)
            }

            binding.etDate.editText.setText(inputOutput?.fecha)
            binding.etInput.editText.setText(inputOutput?.entrada)
            binding.etOutput.editText.setText(inputOutput?.salida)
            binding.swRegister.isChecked = inputOutput!!.isAcctive
            binding.etDate.root.isEnabled = false
            binding.save.button.text = getString(R.string.edit)
            binding.btnDelete.isVisible = true
            binding.btnDelete.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt(Constants.OPCION, 55)
                findNavController().navigate(R.id.to_succesFragment, bundle)
            }
        }else{
            employeSelectLD.observe(viewLifecycleOwner){
                if (it != null){
                    if (it.fotografia.contains("File not foundjava", ignoreCase = true) || it.fotografia.isEmpty()) {
                        binding.listImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.user_icon_big))
                    } else {
                        val imagenBase64 = Base64.decode(it.fotografia, Base64.DEFAULT)
                        val imagenconverBitmap = BitmapFactory.decodeByteArray(imagenBase64, 0, imagenBase64.size)
                        binding.listImage.setImageBitmap(imagenconverBitmap)
                    }
                    binding.llEdit.isVisible = true
                    binding.btnAddEmploye.isVisible = false
                    binding.listNumEmployee.text = it.numEmp
                    binding.listName.text = it.nombreCompleto
                    binding.puesto.text = it.puesto
                    binding.turno.text = "VSM"
                }else{
                    binding.llEdit.isVisible = false
                    binding.btnAddEmploye.isVisible = true
                }
            }
            binding.btnAddEmploye.setOnClickListener {
                dialog.showDialogList()
            }
            binding.cvEmploye.setOnClickListener {
                dialog.showDialogList()
            }
            binding.save.button.text = getString(R.string.save)
            binding.etDate.editText.setText(Utilities.getDateLocal())
            binding.etInput.editText.setText(Utilities.getTimeLocal())
            binding.etOutput.editText.setText(Utilities.getTimeLocal())
            binding.save.button.text = getString(R.string.save)
        }
        pickers.dataEditTextFrom( binding.etDate.editText.text.toString(),"dd/MM/yyyy","dd-MM-yyyy",1)
        spinnerCatalog.getCatalogStation(binding.spStation.spinner,inputOutput?.estacion)
        spinnerCatalog.getCatalogStatus(binding.spDelayed.spinner,inputOutput?.retardos)
        spinnerCatalog.getCatalogoZonaService(binding.spZone.spinner,inputOutput?.zona)
        spinnerCatalog.getCatalogoDepartament(binding.spDepartament.spinner,inputOutput?.departamento)

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
            validateSpinners()
        }
    }
    private fun validateSpinners() {
        if( SpinnerCatalog.station == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione una Estacion")
            return
        }
/*        if( SpinnerCatalog.status == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione un Estatus")
            return
        }*/
        if( SpinnerCatalog.zona == 0L){
            Utilities.showToastyGeneral(requireContext(), "Seleccione una Zona")
            return
        }
        if( SpinnerCatalog.departament == "" ){
            Utilities.showToastyGeneral(requireContext(), "Seleccione un Departamento")
            return
        }
        val bundle = Bundle()
        bundle.putInt(Constants.OPCION, 55)
        findNavController().navigate(R.id.to_succesFragment, bundle)
    }
    private fun setHintSpinner(){
        binding.spStation.inputLayout.hint = "Estacion"
        binding.spDelayed.inputLayout.hint = "Retardos"
        binding.spZone.inputLayout.hint = "Zona"
        binding.spDepartament.inputLayout.hint = "Departamento"
        binding.etDate.inputLayout.hint = getString(R.string.fecha1)
        binding.etInput.inputLayout.hint = getString(R.string.fpHoraInicio)
        binding.etOutput.inputLayout.hint = getString(R.string.fpHoraFin)
    }
    override fun onResume() {
        super.onResume()
        mainInterface?.showFilter(false,2)
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