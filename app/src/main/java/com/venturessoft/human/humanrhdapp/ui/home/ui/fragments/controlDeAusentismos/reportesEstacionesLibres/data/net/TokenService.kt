package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.TokenResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class TokenService {

    val autservice = RetrofitDataSource(TypeServices.FREESTATION).getRetrofit()

    suspend fun token(tokenBasi : String): ApiResponceStatus<TokenResponse> {

        return makeNetworkCall {
            val response = autservice.create(FreeStationApiClient::class.java).token(tokenBasi)

            evaluateResponce(response.codigo)
            response
        }
    }
}