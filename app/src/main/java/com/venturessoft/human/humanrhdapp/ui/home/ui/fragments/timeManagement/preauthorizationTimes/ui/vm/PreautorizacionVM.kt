package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionTiemposResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreaturoizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreautResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.PreautorizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.net.AddPreauTiemposService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.net.EditPreauTiemposService
import kotlinx.coroutines.launch

class PreautorizacionVM : ViewModel() {
    val respository = AddPreauTiemposService()
    val respositoryEdit = EditPreauTiemposService()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusEdit = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataPreauthorization = MutableLiveData<AddPreautorizacionTiemposResponse?>(null)
        private set
    var dataAddPreauthorization = MutableLiveData<AddAusentismosResponse?>(null)
        private set
    var dataEditPreauthorization = MutableLiveData<EditPreautResponse?>(null)
        private set
    fun preautorizacionTiempos(preautorizacionRequest: PreautorizacionRequest) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.preautorizacionTiempos(preautorizacionRequest)
            if (responce is ApiResponceStatus.Success) {
                dataPreauthorization.value = responce.data
            }else{
                dataPreauthorization.value = null
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }

    fun addPreautorizacionTiempos(addPreautorizacionRequest: AddPreautorizacionRequest) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.addPreautorizacionTiempos(addPreautorizacionRequest)
            if (responce is ApiResponceStatus.Success) {
                dataAddPreauthorization.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }

    fun editPreautorizacionTiempos(editPreaturoizacionRequest: EditPreaturoizacionRequest) {
        viewModelScope.launch {
            statusEdit.value = ApiResponceStatus.Loading()
            val responce = respositoryEdit.editPreautorizacionTiempos(editPreaturoizacionRequest)
            if (responce is ApiResponceStatus.Success) {
                dataEditPreauthorization.value = responce.data
            }
            statusEdit.value = responce as ApiResponceStatus<Any>
        }
    }
}