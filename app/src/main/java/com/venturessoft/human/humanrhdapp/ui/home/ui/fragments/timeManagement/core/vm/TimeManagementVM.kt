package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.CalendarResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.CategoryResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.models.DepartamentResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.core.data.net.TimeManagementRep
import com.venturessoft.human.humanrhdapp.uiFragment.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.CatalogResponse
import com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.models.GetCatalogoRolResponse
import com.venturessoft.human.humanrhdapp.uiFragment.timeManagement.generalInformation.data.models.GetCatalogoTurnoResponse
import kotlinx.coroutines.launch

class TimeManagementVM : ViewModel() {

    private val timeManagementRep = TimeManagementRep()
    var dataGetCatalogoTurno = MutableLiveData<GetCatalogoTurnoResponse?>(null)
        private set
    var dataGetCatalogoRol = MutableLiveData<GetCatalogoRolResponse?>(null)
        private set
    var dataAreaCentroLinea = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataZona = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataConcepto = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataSupervisor = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataReasons= MutableLiveData<CatalogResponse?>(null)
        private set
    var dataCalendar= MutableLiveData<CalendarResponce?>(null)
        private set
    var dataDepartament= MutableLiveData<DepartamentResponce?>(null)
        private set
    var dataCategory = MutableLiveData<CategoryResponce?>(null)
        private set
    var dataCodid = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataCatalogoPermisos = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataStation = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataCatalogoGrupos = MutableLiveData<CatalogResponse?>(null)
        private set
    var dataCatalogoProceso= MutableLiveData<CatalogResponse?>(null)
        private set
    fun getRolTurn() {
        viewModelScope.launch {
            val responce = timeManagementRep.getRolTurn()
            if (responce is ApiResponceStatus.Success) {
                dataGetCatalogoRol.value = responce.data[0] as GetCatalogoRolResponse
                dataGetCatalogoTurno.value = responce.data[1] as GetCatalogoTurnoResponse
            }
        }
    }
    fun getAreaCentroLinea(area: String? = null, centro: String?= null) {
        dataAreaCentroLinea.value = null
        viewModelScope.launch {
            val responce = timeManagementRep.getAreaCentroLinea(area, centro)
            if (responce is ApiResponceStatus.Success) {
                dataAreaCentroLinea.value = responce.data
            }else{
                dataAreaCentroLinea.value = CatalogResponse()
            }
        }
    }
    fun getConcepto() {
        viewModelScope.launch {
            val responce = timeManagementRep.getConceptos()
            if (responce is ApiResponceStatus.Success) {
                dataConcepto.value = responce.data
            }
        }
    }
    fun getZona() {
        viewModelScope.launch {
            val responce = timeManagementRep.getZona()
            if (responce is ApiResponceStatus.Success) {
                dataZona.value = responce.data
            }
        }
    }
    fun getSupervisor() {
        viewModelScope.launch {
            val responce = timeManagementRep.getSupervisor()
            if (responce is ApiResponceStatus.Success) {
                dataSupervisor.value = responce.data
            }
        }
    }
    fun getReasons() {
        viewModelScope.launch {
            val responce = timeManagementRep.getReasons()
            if (responce is ApiResponceStatus.Success) {
                dataReasons.value = responce.data
            }
        }
    }
    fun getCalendar() {
        viewModelScope.launch {
            val responce = timeManagementRep.getCalendar()
            if (responce is ApiResponceStatus.Success) {
                dataCalendar.value = responce.data
            }
        }
    }
    fun getDepartment() {
        viewModelScope.launch {
            val responce = timeManagementRep.getDepartment()
            if (responce is ApiResponceStatus.Success) {
                dataDepartament.value = responce.data
            }
        }
    }
    fun getCategory() {
        viewModelScope.launch {
            val responce = timeManagementRep.getCategory()
            if (responce is ApiResponceStatus.Success) {
                dataCategory.value = responce.data
            }
        }
    }
    fun codid() {
        viewModelScope.launch {
            val responce = timeManagementRep.codid()
            if (responce is ApiResponceStatus.Success) {
                dataCodid.value = responce.data
            }
        }
    }
    fun catalogoPermisos() {
        viewModelScope.launch {
            val responce = timeManagementRep.catalogoPermisos()
            if (responce is ApiResponceStatus.Success) {
                dataCatalogoPermisos.value = responce.data
            }
        }
    }
    fun station() {
        viewModelScope.launch {
            val responce = timeManagementRep.station()
            if (responce is ApiResponceStatus.Success) {
                dataStation.value = responce.data
            }
        }
    }
    fun catalogoGrupos() {
        viewModelScope.launch {
            val responce = timeManagementRep.catalogoGrupos()
            if (responce is ApiResponceStatus.Success) {
                dataCatalogoGrupos.value = responce.data
            }
        }
    }

    fun catalogoProceso() {
        viewModelScope.launch {
            val responce = timeManagementRep.catalogoProceso()
            if (responce is ApiResponceStatus.Success) {
                dataCatalogoProceso.value = responce.data
            }
        }
    }
}