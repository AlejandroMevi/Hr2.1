package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net

import com.venturessoft.human.humanrhdapp.network.Response.AddPermisosResponse
import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.AddPermisosRequest
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class AddPermisosService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun addPermisos(
        token: String, addPermisosRequest: AddPermisosRequest
    ): ApiResponceStatus<AddPermisosResponse> {
        return makeNetworkCall {
            val response = autservice.create(AddPermisosApiClient::class.java)
                .getAddPermisosCA(token, addPermisosRequest)
            evaluateResponce(response.codigo.toString())
            response
        }
    }
}