package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.network.Response.AddPermisosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.AddPermisosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.GetListaAusentismosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.GetPermisosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net.AddPermisosService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net.GetListaAusentismosService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net.GetPermisosService
import kotlinx.coroutines.launch

class PermisosViewModel : ViewModel() {

    private val respository = GetPermisosService()
    private val respositoryAddPermisos = AddPermisosService()
    private val repositoryGetListaAusentismos = GetListaAusentismosService()
    var statusGetPermisos = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusAddPermisos = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusGetListaAusentismos = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataGetPermisos = MutableLiveData<GetPermisosResponse?>(null)
        private set
    var dataAddPermisos = MutableLiveData<AddPermisosResponse?>(null)
        private set
    var dataGetListaAusentismos = MutableLiveData<GetListaAusentismosResponse?>(null)
        private set

    fun getPermisos(token: String, numCia: Long, anio: Long, numEmp: Long) {
        viewModelScope.launch {
            statusGetPermisos.value = ApiResponceStatus.Loading()
            val responce = respository.getPermisos(token, numCia, anio, numEmp)
            if (responce is ApiResponceStatus.Success) {
                dataGetPermisos.value = responce.data
            }
            statusGetPermisos.value = responce as ApiResponceStatus<Any>
        }
    }
    fun getListaAusentismos(token: String, cia: Long, numEmp: Long) {
        viewModelScope.launch {
            statusGetListaAusentismos.value = ApiResponceStatus.Loading()
            val responce = repositoryGetListaAusentismos.getListaAusentismos(token, cia, numEmp)
            if (responce is ApiResponceStatus.Success) {
                dataGetListaAusentismos.value = responce.data
            }
            statusGetListaAusentismos.value = responce as ApiResponceStatus<Any>
        }
    }
    fun addPermisos(
        token: String, addPermisosRequest: AddPermisosRequest
    ) {
        viewModelScope.launch {
            statusAddPermisos.value = ApiResponceStatus.Loading()
            val responce = respositoryAddPermisos.addPermisos(token, addPermisosRequest)
            if (responce is ApiResponceStatus.Success) {
                dataAddPermisos.value = responce.data
            }
            statusAddPermisos.value = responce as ApiResponceStatus<Any>
        }
    }
}