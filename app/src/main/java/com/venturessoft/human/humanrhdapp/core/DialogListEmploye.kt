package com.venturessoft.human.humanrhdapp.core

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.DialogListEmployeBinding
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter.Companion.employeSelectLD
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeAdapter.Companion.listEmployeSelect

@SuppressLint("SetTextI18n")
class DialogListEmploye(private val fragment: Fragment, searchEmploye: SearchEmploye, txt: Button,private val isSingleSelection:Boolean) :
    ListEmployeeAdapter.OnClickListener {
    val dialog = MaterialAlertDialogBuilder(fragment.requireContext(), R.style.AlertDialogCustomList)
    val bind: DialogListEmployeBinding = DialogListEmployeBinding.inflate(fragment.layoutInflater)

    private val employeSelect = mutableListOf<Long>()
    init {
        dialog.setView(bind.root)
        dialog.setCancelable(false)
        dialog.setTitle(fragment.getString(R.string.lista_Empleados))
        dialog.setPositiveButton(fragment.getString(R.string.list_company_positive)) { _, _ -> }
        dialog.setNegativeButton(fragment.getString(R.string.list_company_negative)){ _, _ -> }
        dialog.create()

        searchEmploye.searchEmploye(bind.etFilter, bind.progressIn)
        SearchEmploye.listUser.observe(fragment) { listUser ->
            bind.miLista.adapter = ListEmployeeAdapter(listUser, fragment, this,isSingleSelection)
        }
        if (!isSingleSelection){
            listEmployeSelect.observe(fragment){listEmploye->
                if (listEmploye.isNullOrEmpty()) {
                    txt.text = fragment.getString(R.string.seleccionar_empleados)
                } else {
                    txt.text = if (listEmploye.size == 1) {
                        "${listEmploye.size}  ${fragment.getString(R.string.empleado_seleccionado)}"
                    } else {
                        "${listEmploye.size}  ${fragment.getString(R.string.empleados_seleccionados)}"
                    }
                }
            }
        }
    }
    fun showDialogList() {
        try {
            dialog.show()
        } catch (e: IllegalStateException) {
            (bind.root.parent as ViewGroup).removeView(bind.root)
            dialog.show()
        }
    }
    override fun onClick(item: ItemItem, position: Int) {
        try {
            if (isSingleSelection){
                employeSelectLD.value = null
                listEmployeSelect.value = null
                employeSelectLD.value = item
            }else{
                if (employeSelect.contains(item.numEmp?.toLong())) {
                    employeSelect.remove(item.numEmp?.toLong())
                } else {
                    item.numEmp?.let {
                        employeSelect.add(it.toLong())
                    }
                }
                listEmployeSelect.value = employeSelect
            }
        } catch (_: java.lang.Exception) { }
        bind.miLista.adapter?.notifyItemChanged(position)
    }
}