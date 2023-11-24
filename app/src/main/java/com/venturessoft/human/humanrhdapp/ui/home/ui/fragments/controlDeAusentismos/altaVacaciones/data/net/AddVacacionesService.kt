package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.models.AddVacacionesRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.altaVacaciones.data.models.AddVacacionesResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class AddVacacionesService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun getVacaciones(
        token: String, addVacacionesRequest: AddVacacionesRequest
    ): ApiResponceStatus<AddVacacionesResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddVacacionesApiClient::class.java)
                .addVacacionesCA(token, addVacacionesRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
}