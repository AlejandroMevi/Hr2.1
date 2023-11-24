package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports.reportesAT.ui.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports.reportesAT.data.models.ReporteAdmiTiemposRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports.reportesAT.data.models.ReporteAdmiTiemposResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.reports.reportesAT.data.net.ReporteAdmiTiemposService
import kotlinx.coroutines.launch

class ReporteAdministracionTiemposViewModel : ViewModel() {

    private val respository = ReporteAdmiTiemposService()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var data = MutableLiveData<ReporteAdmiTiemposResponse?>(null)
        private set
    fun reportesAdmiTiempos(typeReport: String, reportRequest: ReporteAdmiTiemposRequest, context: Context) {
        data.value = null
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.reporteAdmiTiempos(typeReport, reportRequest, context)
            if (responce is ApiResponceStatus.Success) {
                data.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
}