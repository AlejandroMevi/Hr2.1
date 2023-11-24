package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.AddProgAnualVacRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.ListaSolicitudesResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.ProgAnualVacResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.net.AddProgAnualVacService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.net.ListaSolicitudesService
import kotlinx.coroutines.launch

class ProgAnualVacacionesViewModel: ViewModel() {
    private val respositoryPAVacaciones = AddProgAnualVacService()
    private val repositoryListaSolicitudes = ListaSolicitudesService()
    var statusAddPAVacaciones = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusListaSolicitudes = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataPAVacaciones = MutableLiveData<ProgAnualVacResponse?>(null)
        private set
    var dataListaSolicitudes = MutableLiveData<ListaSolicitudesResponse?>(null)
        private set
    fun addPAVacaciones(token: String, addProgAnualVacRequest: AddProgAnualVacRequest) {
        dataPAVacaciones.value = null
        viewModelScope.launch {
            statusAddPAVacaciones.value = ApiResponceStatus.Loading()
            val responce = respositoryPAVacaciones.addProgAnualVacaciones(token, addProgAnualVacRequest)
            if (responce is ApiResponceStatus.Success) {
                dataPAVacaciones.value = responce.data
            }
            statusAddPAVacaciones.value = responce as ApiResponceStatus<Any>
        }
    }
    fun listaSolicitudes(token: String, numCia: Long, numEmp: Long, anio: Long) {
        dataListaSolicitudes.value = null
        viewModelScope.launch {
            statusListaSolicitudes.value = ApiResponceStatus.Loading()
            val responce = repositoryListaSolicitudes.listaSolicitudes(token, numCia, numEmp, anio)
            if (responce is ApiResponceStatus.Success) {
                dataListaSolicitudes.value = responce.data
            }
            statusListaSolicitudes.value = responce as ApiResponceStatus<Any>
        }
    }
}