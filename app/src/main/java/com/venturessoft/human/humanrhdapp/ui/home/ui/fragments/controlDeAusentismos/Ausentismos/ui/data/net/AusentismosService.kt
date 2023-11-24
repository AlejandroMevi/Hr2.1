package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.Ausentismos.ui.data.models.AddAusentismosResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class AusentismosService {


    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun addAusentismos(
        token: String, addAusentismosRequest: AddAusentismosRequest
    ): ApiResponceStatus<AddAusentismosResponse> {
        return makeNetworkCall {
            val response = autservice.create(AusentismosApiClient::class.java)
                .getAddAusentismos(token, addAusentismosRequest)
            evaluateResponce(response.codigo.toString())
            response
        }
    }
}