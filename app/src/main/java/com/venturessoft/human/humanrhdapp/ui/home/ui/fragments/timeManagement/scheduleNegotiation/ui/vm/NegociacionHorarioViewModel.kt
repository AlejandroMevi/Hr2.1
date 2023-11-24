package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.inputOutput.data.models.AddInputOutputRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationResponce
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.net.AddNegociacionHorarioService
import com.venturessoft.human.humanrhdapp.utilis.complements.User
import kotlinx.coroutines.launch
import java.time.Year

class NegociacionHorarioViewModel : ViewModel() {

    val respository = AddNegociacionHorarioService()
    var status = MutableLiveData<ApiResponceStatus<Any>?>(null)
        private set
    var dataScheduleNegotiation = MutableLiveData<ScheduleNegotiationResponce?>(null)
        private set
    var dataGeneralResponse = MutableLiveData<GeneralResponse?>(null)
        private set
    fun getScheduleNegotiation(numEmp:Long,mes:Int,anio:Int) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.getScheduleNegotiation(numEmp, mes,anio)
            if (responce is ApiResponceStatus.Success) {
                dataScheduleNegotiation.value = responce.data
            }else{
                dataScheduleNegotiation.value = null
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun postScheduleNegotiation(scheduleNegotiationRequest: ScheduleNegotiationRequest) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.postScheduleNegotiation(scheduleNegotiationRequest)
            if (responce is ApiResponceStatus.Success) {
                dataGeneralResponse.value = responce.data
            }else{
                dataGeneralResponse.value = null
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }
    fun putScheduleNegotiation(numEmp: Long, fechaAplicacion: String,scheduleNegotiationRequest: ScheduleNegotiationRequest) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.putScheduleNegotiation(numEmp, fechaAplicacion, scheduleNegotiationRequest)
            if (responce is ApiResponceStatus.Success) {
                dataGeneralResponse.value = responce.data
            }else{
                dataGeneralResponse.value = null
            }
            status.value = responce as ApiResponceStatus<Any>
        }
    }

    fun deleteScheduleNegotiation(numEmp: Long, fechaAplicacion: String,month:Int,year: Int) {
        viewModelScope.launch {
            status.value = ApiResponceStatus.Loading()
            val responce = respository.deleteScheduleNegotiation(numEmp, fechaAplicacion)
            if (responce is ApiResponceStatus.Success) {
                getScheduleNegotiation(numEmp,month,year)
            }else{
                dataGeneralResponse.value = null
                responce as ApiResponceStatus.Error
                status.value = ApiResponceStatus.Error(responce.messageId)
            }
        }
    }
}