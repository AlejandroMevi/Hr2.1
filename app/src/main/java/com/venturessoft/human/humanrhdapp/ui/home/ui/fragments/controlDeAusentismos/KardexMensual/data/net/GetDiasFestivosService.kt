package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.KardexMensual.data.models.DiasFestivosResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class GetDiasFestivosService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun getDiasFestivos(
        token: String,
        diasFestivosRequest: DiasFestivosRequest
    ): ApiResponceStatus<DiasFestivosResponse> {
        return makeNetworkCall {
            val response = autservice.create(GetDiasFestivosApiClient::class.java).getDiasFestivos(token, diasFestivosRequest)
            evaluateResponce(response.codigo)
            response
        }
    }
}