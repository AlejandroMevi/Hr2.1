package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.AddProgAnualVacRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.ProgAnualVacaciones.ui.data.models.ProgAnualVacResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class AddProgAnualVacService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun addProgAnualVacaciones(
        token: String, addProgAnualVacRequest: AddProgAnualVacRequest
    ): ApiResponceStatus<ProgAnualVacResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddProgAnualVacApiClient::class.java)
                .addProgAnualVacacionesCA(token, addProgAnualVacRequest)
            evaluateResponce(response.codigo.toString())
            response
        }
    }
}