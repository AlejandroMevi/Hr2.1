package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.ui.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.ReportRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.models.ReportResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportes.reportHolidayControlAusentismo.data.net.ReportVacacionesCaService
import kotlinx.coroutines.launch

class ReportHolidayCAViewModel : ViewModel() {

    val respository = ReportVacacionesCaService()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataReportVacacionesCa = MutableLiveData<ReportResponse?>(null)
        private set
    fun getReportVacacionesCa(reportRequest: ReportRequest, context: Context) {
        dataReportVacacionesCa.value = null
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.reportVacacionesCa(reportRequest, context)
            if (responce is ApiResponceStatus.Success) {
                dataReportVacacionesCa.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
}