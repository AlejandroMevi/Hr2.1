package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.AddStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.EditStationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.InfoGenralRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.MRResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.models.StationResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.data.net.GeneralInfoRep
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.generalInformation.ui.GeneralStationsFragment
import kotlinx.coroutines.launch

class GeneralInformationVM : ViewModel() {

    private val generalInfoRep = GeneralInfoRep()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataMR = MutableLiveData<MRResponce?>(null)
        private set
    var dataResponce = MutableLiveData<GeneralResponse?>(null)
        private set
    var dataResponceStation = MutableLiveData<StationResponce?>(null)
        private set
    fun getMR(numEmp:Long) {
        dataMR.value = null
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = generalInfoRep.getMR(numEmp)
            if (responce is ApiResponceStatus.Success) {
                dataMR.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun addAdministrarMR(infoGenralRequest: InfoGenralRequest) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = generalInfoRep.addAdministrarMR(infoGenralRequest)
            if (responce is ApiResponceStatus.Success){
                dataResponce.value = responce.data
            }else{
                dataResponce.value = null
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun editAdministrarMR(numEmp:Long,fechaAplicacion:String,infoGenralRequest: InfoGenralRequest) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = generalInfoRep.editAdministrarMR(numEmp,fechaAplicacion,infoGenralRequest)
            if (responce is ApiResponceStatus.Success) {
                dataResponce.value = responce.data
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun getStation(numEmp:Long,gafete:Long) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = generalInfoRep.getStation(numEmp,gafete)
            if (responce is ApiResponceStatus.Success) {
                dataResponceStation.value = responce.data
            }else{
                dataResponceStation.value = null
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun addStations(addStationRequest: AddStationRequest,editStationRequest: List<EditStationRequest>) {
        var resultStatus:ApiResponceStatus<Any>?=null
        if (addStationRequest.estaciones.isNotEmpty()){
            viewModelScope.launch {
                status.value = ApiResponceStatus.Loading()
                val responce = generalInfoRep.addStations(addStationRequest)

                if (responce is ApiResponceStatus.Success) {
                    dataResponce.value = responce.data
                }else{
                    dataResponce.value = null
                    resultStatus = responce as ApiResponceStatus<Any>
                }
                editStations(addStationRequest.numEmp,addStationRequest.gafete,editStationRequest,resultStatus)
            }
        }else{
            editStations(addStationRequest.numEmp,addStationRequest.gafete,editStationRequest,null)
        }
    }
    private fun editStations(numEmp:Long,gafete:Long,editStationRequest: List<EditStationRequest>,resultStatus:ApiResponceStatus<Any>?=null) {
        if (editStationRequest.isNotEmpty()){
            viewModelScope.launch {
                status.value = ApiResponceStatus.Loading()
                val responce = generalInfoRep.editStations(numEmp,gafete,editStationRequest)
                if (responce is ApiResponceStatus.Success) {
                    dataResponce.value = responce.data
                }else{
                    dataResponce.value = null
                }
                if (resultStatus != null){
                    status.value = resultStatus
                }
                status.value = responce as ApiResponceStatus<Any>
            }
        }else{
            if (resultStatus != null){
                status.value = resultStatus
            }else{
                status.value = ApiResponceStatus.Success("")
            }
        }
    }
}