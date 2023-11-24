package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.ListaSolicitudesResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class ListaSolicitudesService {
    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun listaSolicitudes(
        token: String, numCia: Long, numEmp: Long, anio: Long
    ): ApiResponceStatus<ListaSolicitudesResponse> {
        return makeNetworkCall {
            val response = autservice.create(ListaSolicitudesApiClient::class.java)
                .listaSolicitudes(token, numCia, numEmp, anio)
            evaluateResponce(response.codigo.toString())
            response
        }
    }
}