package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models.BusquedaUsuarioResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.net.BusquedaEmpleadoRepositorio
import kotlinx.coroutines.launch

class ListEmployeeViewModel : ViewModel() {

    private val repository = BusquedaEmpleadoRepositorio()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataBusquedaEmpleado = MutableLiveData<BusquedaUsuarioResponse?>(null)
        private set
    fun busquedaEmpleado(token: String, numCia: Long, supervisor: String, size: Long, mr:Boolean, cadenaBusqueda: String) {
        status.value = ApiResponceStatus.Loading()
        viewModelScope.launch {
            val responce = repository.busquedEmpleado(token, numCia, supervisor, size, mr, cadenaBusqueda)
            if (responce is ApiResponceStatus.Success) {
                dataBusquedaEmpleado.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
}