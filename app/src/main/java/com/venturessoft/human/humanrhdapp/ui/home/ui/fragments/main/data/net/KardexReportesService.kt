package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.net

import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.kardexReporteRequest
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.main.data.models.KardexResponse
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
class KardexReportesService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun kardexReporte(token: String, kardexReporteRequest: kardexReporteRequest): ApiResponceStatus<KardexResponse> {
        return makeNetworkCall {
            val response = autservice.create(KardexReporteApiClient::class.java).getKardex(token, kardexReporteRequest)
            response
        }
    }
}