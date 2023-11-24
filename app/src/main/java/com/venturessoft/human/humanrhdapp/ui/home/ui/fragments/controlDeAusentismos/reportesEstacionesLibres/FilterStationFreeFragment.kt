package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.core.DialogFilterListEmploye
import com.venturessoft.human.humanrhdapp.core.Pickers
import com.venturessoft.human.humanrhdapp.core.SearchEmploye
import com.venturessoft.human.humanrhdapp.databinding.FragmentFilterStationFreeBinding
import com.venturessoft.human.humanrhdapp.ui.home.MainInterface
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.FilterFreeStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.vm.EstacionesLibresViewModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm.ListEmployeeViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.getDateLocal
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.getRoundedDrawable

class FilterStationFreeFragment :
    DialogFragment() {

    private lateinit var binding: FragmentFilterStationFreeBinding
    private val freeStationVM: EstacionesLibresViewModel by activityViewModels()
    private var mainInterface: MainInterface? = null
    private var pickers: Pickers = Pickers()
    private val searchEmploye = SearchEmploye(ListEmployeeViewModel(), this)

    companion object {
        var addInputOutput: FilterFreeStationRequest? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterStationFreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = DialogFilterListEmploye(
            this,
            searchEmploye,
            binding.btnClearFilters,
            freeStationVM,
            viewLifecycleOwner
        )
        toolbar()
        initView()
        initButtons()
        binding.buttonEmployee.setOnClickListener {
            dialog.showDialogList()
        }
        loadData()
        validateFilters()
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

    private fun toolbar() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                mainInterface?.showBadgeDrawable(false)
                freeStationVM.validateFilters.value = true
                dismiss()
            }
            toolbar.title = getString(R.string.filtros)
        }
    }

    private fun initView() {
        if (addInputOutput?.fechaInicio.isNullOrEmpty()) binding.etInput.editText.text = null
        else binding.etInput.editText.setText(addInputOutput?.fechaInicio)
        if (addInputOutput?.fechaInicio.isNullOrEmpty()) binding.etOutput.editText.text = null
        else binding.etOutput.editText.setText(addInputOutput?.fechaFin)
        binding.etInput.inputLayout.hint = getString(R.string.faFechaInicial)
        binding.etOutput.inputLayout.hint = getString(R.string.faFechaFinal)
    }

    private fun initButtons() {
        binding.buttonFilter.setOnClickListener {
            Utilities.vibrate(requireContext())
            freeStationVM.validateFilters.value = true
            dismiss()
        }
        binding.etInput.editText.setOnClickListener {
            pickers.dataPickerFrom(
                this, binding.etInput.editText, 1, "dd-MM-yyyy", freeStationVM
            )
        }
        binding.etOutput.editText.setOnClickListener {
            pickers.dataPickerFrom(
                this, binding.etOutput.editText, 2, "dd-MM-yyyy", freeStationVM
            )
        }
        binding.cbAllEmployes.setOnCheckedChangeListener { _, isChecked ->
            binding.selectEmployee.isVisible = !isChecked
            binding.btnClearFilters.isVisible = !isChecked
            if (isChecked) deleteAllChip()
        }
        binding.btnClearFilters.setOnClickListener {
            deleteAllChip()
            binding.selectEmployee.isVisible = false
            binding.btnClearFilters.isVisible = false
            binding.cbAllEmployes.isChecked = true
            binding.etInput.editText.text = null
            binding.etOutput.editText.text = null
            addInputOutput?.apply {
                fechaInicio = null
                fechaFin = null
            }
            Utilities.vibrate(requireContext())
        }
    }

    private fun validateFilters() {
        freeStationVM.validateFilters.observe(viewLifecycleOwner) {
            val fechaInicio: String?
            val fechaFin: String?
            val filtroEmpleados: Boolean = if (binding.cbAllEmployes.isChecked) {
                true
            } else {
                binding.cgCondiciones.childCount.toString().toInt() == 0
            }
            val filtroFechaI: Boolean =
                binding.etInput.editText.text.toString().isEmpty()
            val filtroFechaF: Boolean =
                binding.etOutput.editText.text.toString().isEmpty()

            freeStationVM.activeFilters.value = !(filtroEmpleados && filtroFechaI && filtroFechaF)

            var listUser: List<Long> = listOf(0)
            if (!freeStationVM.listEmployeFilterSelect.value.isNullOrEmpty()) {
                listUser = freeStationVM.listEmployeFilterSelect.value!!
            }

            if (!filtroFechaI || !filtroFechaF) {
                binding.btnClearFilters.isVisible = true
            } else {
                binding.btnClearFilters.isVisible = !binding.cbAllEmployes.isChecked
            }

            if (filtroFechaI && filtroFechaF) {
                fechaInicio = null; fechaFin = null
            } else {
                fechaInicio = binding.etInput.editText.text.toString().ifEmpty { getDateLocal() }
                fechaFin = binding.etOutput.editText.text.toString().ifEmpty { getDateLocal() }
            }
            if (freeStationVM.listEmployeFilterSelect.value.isNullOrEmpty()) {
                addInputOutput =
                    FilterFreeStationRequest(
                        fechaInicio,
                        fechaFin,
                        null
                    )
            } else {
                addInputOutput =
                    (if (!freeStationVM.listEmployeFilterSelect.value.isNullOrEmpty()) listUser else null)?.let { it1 ->
                        FilterFreeStationRequest(
                            fechaInicio,
                            fechaFin,
                            it1
                        )
                    }
            }
        }
    }

    private fun loadData() {
        freeStationVM.detailsEmployeFilterSelect.observe(viewLifecycleOwner) { listEmployeSelect ->
            if (listEmployeSelect.isNullOrEmpty()) {
                binding.cgCondiciones.removeAllViews()
            } else {
                binding.cbAllEmployes.isChecked = false
                binding.selectEmployee.isVisible = true
                binding.cgCondiciones.removeAllViews()
                for (i in listEmployeSelect.indices) {
                    newChip(
                        listEmployeSelect[i].nombreCompleto,
                        listEmployeSelect[i].fotografia
                    )
                }
                chipInit()
            }
        }
    }

    private fun chipInit() {

        var chip: Chip
        for (i in 0 until binding.cgCondiciones.childCount) {
            chip = binding.cgCondiciones.getChildAt(i) as Chip
            chip.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

            chip.setOnCloseIconClickListener {
                binding.cgCondiciones.removeView(it)
                val aux = it as Chip
                val nameDelete = "${aux.text}"
                deleteChip(nameDelete)
            }
            /*
                        chip.setOnClickListener {
                val aux = it as Chip
                Toast.makeText(requireActivity(), "${aux.text}", Toast.LENGTH_SHORT).show()
            }
             */
        }
    }

    private fun deleteAllChip() {
        var chip: Chip
        if (freeStationVM.detailsEmployeFilterSelect.value != null) {
            for (i in 0 until freeStationVM.detailsEmployeFilterSelect.value!!.size) {
                try {
                    chip = binding.cgCondiciones.getChildAt(i) as Chip
                    chip.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                    val aux = chip
                    val nameDelete = "${aux.text}"
                    deleteChip(nameDelete)
                } catch (e: Exception) {
                    println("Exception : $e")
                }
            }
            binding.cgCondiciones.removeAllViews()
        }
    }

    private fun newChip(nombre: String, foto: String) {
        val roundedDrawable: Drawable? =
            if (foto.contains("File not foundjava", ignoreCase = true) || foto.isEmpty()) {
                ContextCompat.getDrawable(requireContext(), R.drawable.user_icon_big)
            } else {
                val decodedBytes = Base64.decode(foto, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                getRoundedDrawable(bitmap, requireActivity())
            }
        val nullParent: ViewGroup? = null
        val chipNew =
            layoutInflater.inflate(R.layout.chip_filter, nullParent) as Chip
        chipNew.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        chipNew.setPadding(0, 32, 0, 32)
        chipNew.text = nombre
        chipNew.chipIcon = roundedDrawable
        chipNew.isChipIconVisible = true
        chipNew.isCloseIconVisible = true
        chipNew.isClickable = true
        chipNew.isCheckable = false
        binding.cgCondiciones.addView(chipNew as View)
        chipNew.setOnCloseIconClickListener { binding.cgCondiciones.removeView(chipNew as View) }
    }

    private fun deleteChip(name: String) {
        try {
            for (i in 0..freeStationVM.detailsEmployeFilterSelect.value!!.size) {
                if (name == freeStationVM.detailsEmployeFilterSelect.value!![i].nombreCompleto) {
                    freeStationVM.detailsEmployeFilterSelect.value!!.removeAt(i)
                    freeStationVM.listEmployeFilterSelect.value!!.removeAt(i)
                } else {
                    println("no Coinciden : $name y ${freeStationVM.detailsEmployeFilterSelect.value!![i].nombreCompleto}")
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}