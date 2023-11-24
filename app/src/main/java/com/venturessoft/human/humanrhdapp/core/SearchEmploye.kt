package com.venturessoft.human.humanrhdapp.core

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.R
import com.venturessoft.human.humanrhdapp.ui.home.ui.HomeActivity
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.ListEmployeeFragment
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm.ListEmployeeViewModel
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities
import com.venturessoft.human.humanrhdapp.utilis.complements.Utilities.Companion.observeOnce
import es.dmoral.toasty.Toasty

class SearchEmploye(private val viewModel: ListEmployeeViewModel, private val fragment: Fragment) {
    companion object{
        var listUser = MutableLiveData<List<ItemItem>>(listOf())
    }
    init {
        User.listUsuFalse?.let { listUser.value = it }
    }
    fun searchEmploye(etFilter: EditText, progressIn: LinearProgressIndicator){
        etFilter.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                corutineEmployListService(text.toString(), progressIn)
            } else {
                User.listUsuFalse?.let { listUser.value = it }
            }
        }
    }
    private fun corutineEmployListService(newText: String, progressIn: LinearProgressIndicator) {
        viewModel.busquedaEmpleado(User.token, User.numCia, User.usuario, size = 300, false, newText)
        statusObserve(progressIn)
        viewModel.dataBusquedaEmpleado.observeOnce(fragment) { response ->
            if (response != null) {
                if (response.codigo == "0") {
                    val list = ArrayList<ItemItem>()
                    if (response.items.item?.indices != null){
                        for (i in response.items.item.indices) {
                            val dataModel = ItemItem()
                            dataModel.nombreCompleto = response.items.item[i].nombreCompleto
                            dataModel.puesto = response.items.item[i].puesto
                            dataModel.fotografia = response.items.item[i].fotografia
                            dataModel.numEmp = response.items.item[i].numEmp
                            list.add(dataModel)
                            ListEmployeeFragment.usuariosListaArrayResponse = list
                            listUser.value = ListEmployeeFragment.usuariosListaArrayResponse
                        }
                    }
                }
            }
        }
    }
    private fun clearService(progressIn: LinearProgressIndicator) {
        progressIn.visibility = View.INVISIBLE
        viewModel.status.removeObservers(fragment)
        viewModel.status.value = null
    }
    private fun statusObserve(progressIn: LinearProgressIndicator) {
        viewModel.status.observe(fragment) { status ->
            if (status != null) {
                when (status) {
                    is ApiResponceStatus.Loading -> {
                        progressIn.visibility = View.VISIBLE
                    }
                    is ApiResponceStatus.Success -> {
                        clearService(progressIn)
                    }
                    is ApiResponceStatus.Error -> {
                        clearService(progressIn)
                        val errorCode = Utilities.textcode(status.messageId, fragment.requireContext())
                        Utilities.showToastyGeneral(fragment.requireContext(), errorCode)
                    }
                }
            }
        }
    }
}