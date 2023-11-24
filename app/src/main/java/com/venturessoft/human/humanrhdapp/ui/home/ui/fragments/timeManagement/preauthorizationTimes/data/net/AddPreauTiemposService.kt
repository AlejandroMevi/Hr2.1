package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.AddPreautorizacionTiemposResponse
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.PreautorizacionRequest
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class AddPreauTiemposService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun preautorizacionTiempos(preautorizacionRequest: PreautorizacionRequest): ApiResponceStatus<AddPreautorizacionTiemposResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddPreauTiemposApiClient::class.java).preautorizacionTiempos(User.token, preautorizacionRequest.cia,preautorizacionRequest.numEmp,preautorizacionRequest.mes,preautorizacionRequest.anio)
            evaluateResponce(response.codigo)
            response
        }
    }

    suspend fun addPreautorizacionTiempos(addPreautorizacionRequest: AddPreautorizacionRequest): ApiResponceStatus<AddAusentismosResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddPreauTiemposApiClient::class.java).addPreautorizacionTiempos(User.token, addPreautorizacionRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
}