package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.KardexResponse
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.MenuModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.DashBoardResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.net.DashBoardService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.net.KardexReportesService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.kardexReporteRequest
import com.venturessoft.human.humanrhdapp.utilis.complements.Constants
import kotlinx.coroutines.launch

class WelcomeFragmentViewModel : ViewModel() {

    val repositoryKardeReporte = KardexReportesService()
    val repositoryDashBoard = DashBoardService()
    var statusKardex = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusDashBoard = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataDashBoard = MutableLiveData<DashBoardResponse?>(null)
        private set
    var dataKardex = MutableLiveData<KardexResponse?>(null)
        private set
    var idMenu = MutableLiveData<MenuModel>(null)

    fun kardexReporte(token: String, kardexReporteRequest: kardexReporteRequest) {
        dataKardex.value = null
        viewModelScope.launch {
            statusKardex.value = ApiResponceStatus.Loading()
            val responce = repositoryKardeReporte.kardexReporte(token, kardexReporteRequest)
            if (responce is ApiResponceStatus.Success) {
                dataKardex.value = responce.data
            }
            statusKardex.value = responce as ApiResponceStatus<Any>
        }
    }
    fun dashBoard(token: String, supervisor: String, dias: Int, numCia: Long,isRetry:Boolean) {
        dataDashBoard.value = null
        viewModelScope.launch {
            statusDashBoard.value = ApiResponceStatus.Loading()
            val responce = repositoryDashBoard.dashBoard(token, supervisor, dias, numCia)
            if (responce is ApiResponceStatus.Success) {
                dataDashBoard.value = responce.data
            }else{
                responce as ApiResponceStatus.Error
                if (responce.messageId.contains(Constants.TIMEOUT) && isRetry){
                    dashBoard(token, supervisor, dias, numCia,false)
                }else{
                    dataDashBoard.value = null
                }
            }
            statusDashBoard.value = responce as ApiResponceStatus<Any>
        }
    }
}