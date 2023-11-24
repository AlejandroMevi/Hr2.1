package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.GetListaAusentismosResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class GetListaAusentismosService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun getListaAusentismos(
        token: String, cia: Long, numEmp: Long
    ): ApiResponceStatus<GetListaAusentismosResponse> {

        return makeNetworkCall {
            val response = autservice.create(GetListaAusentismosApiClient::class.java)
                .getAusentismos(token, cia, numEmp)

            evaluateResponce(response.codigo.toString())
            response
        }
    }
}