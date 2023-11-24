package com.venturessoft.human.humanrhdapp.core

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.databinding.DialogFilterListEmployeBinding
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.ListFilterEmployeeAdapter
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.vm.EstacionesLibresViewModel

@SuppressLint("SetTextI18n")
class DialogFilterListEmploye(
    private val fragment: Fragment,
    searchEmploye: SearchEmploye,
    txt: Button,
    private val freeStationVM: EstacionesLibresViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : ListFilterEmployeeAdapter.OnClickListener {
    val dialog = MaterialAlertDialogBuilder(fragment.requireContext(), R.style.AlertDialogCustomList)
    val bind: DialogFilterListEmployeBinding = DialogFilterListEmployeBinding.inflate(fragment.layoutInflater)

    companion object {
        val employeFilterSelect = mutableListOf<Long>()
        val employeFilterSelectDetails = mutableListOf<ItemItem>()
    }


    init {
        dialog.setView(bind.root)
        dialog.setCancelable(true)
        dialog.setPositiveButton(fragment.getString(R.string.anadir)) { _, _ -> }
        dialog.setNeutralButton(fragment.getString(R.string.list_company_negative)) { _, _ -> }
        searchEmploye.searchEmploye(bind.etFilter, bind.progressIn)
        SearchEmploye.listUser.observe(fragment) { listUser ->
            bind.miLista.adapter =
                ListFilterEmployeeAdapter(listUser, freeStationVM, fragment, this)
        }
        dialog.create()
    }

    fun showDialogList() {
        try {
            bind.miLista.adapter?.notifyDataSetChanged()
            dialog.show()
        } catch (e: IllegalStateException) {
            (bind.root.parent as ViewGroup).removeView(bind.root)
            dialog.show()
        }
    }

    override fun onClick(item: ItemItem, position: Int) {
        try {
            if (employeFilterSelectDetails.contains(item)) {
                employeFilterSelectDetails.remove(item)
            } else {
                employeFilterSelectDetails.add(item)
            }
            if (employeFilterSelect.contains(item.numEmp.toLong())) {
                employeFilterSelect.remove(item.numEmp.toLong())
            } else {
                item.numEmp.let {
                    employeFilterSelect.add(it.toLong())
                }
            }
            freeStationVM.listEmployeFilterSelect.value = employeFilterSelect
            freeStationVM.detailsEmployeFilterSelect.value = employeFilterSelectDetails
        } catch (_: java.lang.Exception) {
        }

        bind.miLista.adapter?.notifyItemChanged(position)
    }
}