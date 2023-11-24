package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreaturoizacionRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.timeManagement.preauthorizationTimes.data.models.EditPreautResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User

class EditPreauTiemposService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun editPreautorizacionTiempos(editPreaturoizacionRequest: EditPreaturoizacionRequest): ApiResponceStatus<EditPreautResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddPreauTiemposApiClient::class.java).editPreautorizacionTiempos(User.token, editPreaturoizacionRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
}