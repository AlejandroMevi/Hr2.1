package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.net.AusentismosService
import kotlinx.coroutines.launch

class AusentismosViewModel : ViewModel() {

    private val respositoryAddAusentismos = AusentismosService()

    var statusAddAusentismos = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataAddAusentismos = MutableLiveData<AddAusentismosResponse?>(null)
        private set


    fun addAusentismos(
        token: String, addAusentismosRequest: AddAusentismosRequest
    ) {
        viewModelScope.launch {
            statusAddAusentismos.value = ApiResponceStatus.Loading()
            val responce = respositoryAddAusentismos.addAusentismos(token, addAusentismosRequest)
            if (responce is ApiResponceStatus.Success) {
                dataAddAusentismos.value = responce.data
            }
            statusAddAusentismos.value = responce as ApiResponceStatus<Any>
        }
    }
}