package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.GeneralResponse
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.scheduleNegotiation.data.models.ScheduleNegotiationResponce
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class AddNegociacionHorarioService {
    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun getScheduleNegotiation(numEmp:Long,mes:Int,anio:Int): ApiResponceStatus<ScheduleNegotiationResponce> {
        return makeNetworkCall {
            val response = autservice.create(AddNegociacionHorarioApiClient::class.java).getScheduleNegotiation(User.token,User.numCia,numEmp,mes,anio)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun postScheduleNegotiation(scheduleNegotiationRequest: ScheduleNegotiationRequest): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddNegociacionHorarioApiClient::class.java).postScheduleNegotiation(User.token, scheduleNegotiationRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun putScheduleNegotiation(numEmp: Long, fechaAplicacion: String,scheduleNegotiationRequest: ScheduleNegotiationRequest): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddNegociacionHorarioApiClient::class.java).putScheduleNegotiation(User.token,User.numCia, numEmp, fechaAplicacion, scheduleNegotiationRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
    suspend fun deleteScheduleNegotiation(numEmp: Long, fechaAplicacion: String): ApiResponceStatus<GeneralResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddNegociacionHorarioApiClient::class.java).deleteScheduleNegotiation(User.token,User.numCia, numEmp, fechaAplicacion)
            evaluateResponce(response.codigo)
            response
        }
    }
}