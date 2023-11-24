package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualModel
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.models.KardexAnualResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.anualKardex.data.net.KardexAnualService
import kotlinx.coroutines.launch

class KardexAnualViewModel : ViewModel() {
    val respository = KardexAnualService()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var data = MutableLiveData<KardexAnualResponse?>(null)
        private set
    var dataKardexYear = MutableLiveData<KardexAnualModel?>(null)
    fun kardexAnual(token: String, kardexAnualRequest: KardexAnualRequest) {
        data.value = null
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.kardexAnual(token, kardexAnualRequest)
            if (responce is ApiResponceStatus.Success) {
                data.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun getKardexAnual(token: String, listRequest: List<KardexAnualRequest>,year:Int) {
        dataKardexYear.value = null
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.getKardexAnual(token, listRequest)
            if (responce.kardex.isNotEmpty() && responce.holydays.isNotEmpty() && responce.daysoff.isNotEmpty()) {
                dataKardexYear.value = responce
                status.value = ApiResponceStatus.Success("")
            }else{
                status.value = ApiResponceStatus.Error("")
            }
        }
    }
}