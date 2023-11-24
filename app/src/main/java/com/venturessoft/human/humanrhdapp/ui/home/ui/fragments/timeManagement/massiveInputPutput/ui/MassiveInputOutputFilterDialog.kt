package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.DialogMassiveInputOutputFilterBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.SpinnerCatalog
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.massiveInputPutput.ui.vm.MassiveInputOutputVM

class MassiveInputOutputFilterDialog : DialogFragment() {

    private lateinit var binding: DialogMassiveInputOutputFilterBinding
    private val massiveInputOutputVM: MassiveInputOutputVM by activityViewModels()
    private val timeManagementVM: TimeManagementVM by activityViewModels()
    private lateinit var spinnerCatalog: SpinnerCatalog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogMassiveInputOutputFilterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHintSpinner()
        setCheks()
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
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
    private fun setHintSpinner(){
        spinnerCatalog = SpinnerCatalog(timeManagementVM, this)
        spinnerCatalog.getCatalogoProceso(binding.spProcess.spinner)
        binding.spProcess.inputLayout.hint = "Proceso"
    }
    private fun setCheks(){
        binding.cball.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked){
                binding.cb1.isChecked = true
            }
            binding.cb2.isChecked = compoundButton.isChecked
            binding.cb3.isChecked = compoundButton.isChecked
            binding.cb4.isChecked = compoundButton.isChecked
            binding.cb5.isChecked = compoundButton.isChecked
            binding.cb6.isChecked = compoundButton.isChecked
            binding.cb7.isChecked = compoundButton.isChecked
        }

        binding.save.button.setOnClickListener {
            val listFilter= mutableListOf<Int>()
            if (binding.cb1.isChecked) listFilter.add(1)
            if (binding.cb2.isChecked) listFilter.add(2)
            if (binding.cb3.isChecked) listFilter.add(3)
            if (binding.cb4.isChecked) listFilter.add(4)
            if (binding.cb5.isChecked) listFilter.add(5)
            if (binding.cb6.isChecked) listFilter.add(6)
            if (binding.cb7.isChecked) listFilter.add(7)
            if (listFilter.isEmpty()){
                massiveInputOutputVM.dataMassiveFilter.value = null
            }else{
                massiveInputOutputVM.dataMassiveFilter.value = listFilter
            }
            dismiss()
        }
    }
}