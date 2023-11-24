package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net.GetDiasDescansosService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net.GetDiasFestivosService
import kotlinx.coroutines.launch

class KardexMensualViewModel : ViewModel() {
    val respositoryGetDiasFestivos = GetDiasFestivosService()
    val respositoryGetDiasDescanso = GetDiasDescansosService()
    var statusGetDiasFestivos = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusGetDiasDescanso = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataGetDiasFestivos = MutableLiveData<DiasFestivosResponse?>(null)
        private set
    var dataGetDiasDescanso = MutableLiveData<DiasDescansosResponse?>(null)
        private set
    fun getDiasFestivos(token: String, diasFestivosRequest: DiasFestivosRequest) {
        dataGetDiasFestivos.value = null
        viewModelScope.launch {
            statusGetDiasFestivos.value = ApiResponceStatus.Loading()
            val responce = respositoryGetDiasFestivos.getDiasFestivos(token, diasFestivosRequest)
            if (responce is ApiResponceStatus.Success) {
                dataGetDiasFestivos.value = responce.data
            }
            statusGetDiasFestivos.value = responce as ApiResponceStatus<Any>
        }
    }
    fun getDiasDescansos(token: String, diasDescansosRequest: DiasDescansosRequest) {
        dataGetDiasDescanso.value = null
        viewModelScope.launch {
            statusGetDiasDescanso.value = ApiResponceStatus.Loading()
            val responce = respositoryGetDiasDescanso.getDiasDescansos(token, diasDescansosRequest)
            if (responce is ApiResponceStatus.Success) {
                dataGetDiasDescanso.value = responce.data
            }
            statusGetDiasDescanso.value = responce as ApiResponceStatus<Any>
        }
    }
}