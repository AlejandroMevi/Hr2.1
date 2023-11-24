package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsEstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.DetailsFreeStationResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.EstacionesLibresRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.reportesEstacionesLibres.data.models.FreeStationResponse

class DetailsFreeStationService {

    val autservice = RetrofitDataSource(TypeServices.FREESTATION).getRetrofit()

    suspend fun getDetailsFreeStation(
        detailsEstacionesLibresRequest: DetailsEstacionesLibresRequest, token: String
    ): ApiResponceStatus<DetailsFreeStationResponse> {

        return makeNetworkCall {
            val response = autservice.create(FreeStationApiClient::class.java)
                .detailsFreeStation(detailsEstacionesLibresRequest, token)

            evaluateResponce(response.codigo)
            response
        }
    }
}