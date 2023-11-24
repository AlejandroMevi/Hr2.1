package com.venturessoft.human.humanrhdapp.core

import android.widget.Button
import androidx.fragment.app.Fragment
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm.TimeManagementVM
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
class ConceptsService(
    private var viewModel: TimeManagementVM,
    private val fragment: Fragment
) {
    private var p: Pickers = Pickers()
    companion object {
        val key = ArrayList<String>()
        val value = ArrayList<String>()
    }
    fun catalogoConceptosService(ilConcepto: Button) {
        key.clear()
        value.clear()
        viewModel.dataConcepto.observeOnce(fragment) { response ->
            if (response != null) {
                for (i in response.resp!!.indices) {
                    response.resp[i]?.key?.let { key.add(it) }
                    response.resp[i]?.value?.let { value.add(it) }
                }
            }
        }
        ilConcepto.setOnClickListener {
            Pickers.keySelect.clear()
            p.seleccionMultiple(
                value,
                fragment.requireContext(),
                key,
                ilConcepto,
                fragment.getString(R.string.sin_conceptos),
                fragment.getString(R.string.selecciona_concepto)
            )
        }
    }
}