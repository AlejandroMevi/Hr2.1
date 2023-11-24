package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasDescansosResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class GetDiasDescansosService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun getDiasDescansos(
        token: String,
        diasDescansosRequest: DiasDescansosRequest
    ): ApiResponceStatus<DiasDescansosResponse> {
        return makeNetworkCall {
            val response = autservice.create(GetDiasDescansosApiClient::class.java)
                .getDiasDescansos(token, diasDescansosRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
}