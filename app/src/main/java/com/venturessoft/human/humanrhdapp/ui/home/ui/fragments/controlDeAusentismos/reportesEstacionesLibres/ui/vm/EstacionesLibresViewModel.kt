package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.network.Response.ItemItem
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsEstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsFreeStationResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.FilterFreeStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.net.EstacionesLibresService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.FreeStationResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.TokenResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.net.DetailsFreeStationService
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.net.TokenService
import kotlinx.coroutines.launch

class EstacionesLibresViewModel : ViewModel() {

    private val repository = EstacionesLibresService()
    private val repositoryDetails = DetailsFreeStationService()
    private val repositoryToken = TokenService()
    var statusFreeStation = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusDetailsFreeStation = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var statusToken = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataFreeStation = MutableLiveData<FreeStationResponse?>(null)
        private set
    var dataDetailsFreeStation = MutableLiveData<DetailsFreeStationResponse?>(null)
        private set
    var dataToken = MutableLiveData<TokenResponse?>(null)
        private set

    var listEmployeFilterSelect = MutableLiveData<MutableList<Long>?>(null)
    var detailsEmployeFilterSelect = MutableLiveData<MutableList<ItemItem>?>(null)

    var activeFilters = MutableLiveData<Boolean>(null)
    var validateFilters = MutableLiveData<Boolean>(null)

    fun token(tokenBasic: String) {
        viewModelScope.launch {
            statusToken.value = ApiResponceStatus.Loading()
            val responce = repositoryToken.token(tokenBasic)
            if (responce is ApiResponceStatus.Success) {
                dataToken.value = responce.data
            }
            statusToken.value = responce as ApiResponceStatus<Any>
        }
    }

    fun freeStation(estacionesLibresRequest: EstacionesLibresRequest, token: String) {
        viewModelScope.launch {
            statusFreeStation.value = ApiResponceStatus.Loading()
            val responce = repository.getFreeStation(estacionesLibresRequest, token)
            if (responce is ApiResponceStatus.Success) {
                dataFreeStation.value = responce.data
            }
            statusFreeStation.value = responce as ApiResponceStatus<Any>
        }
    }
    fun detailsFreeStation(detailsEstacionesLibresRequest: DetailsEstacionesLibresRequest, token: String) {
        viewModelScope.launch {
            statusDetailsFreeStation.value = ApiResponceStatus.Loading()
            val responce = repositoryDetails.getDetailsFreeStation(detailsEstacionesLibresRequest, token)
            if (responce is ApiResponceStatus.Success) {
                dataDetailsFreeStation.value = responce.data
            }
            statusDetailsFreeStation.value = responce as ApiResponceStatus<Any>
        }
    }
}