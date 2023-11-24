package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.controlDeAusentismos.permisos.data.models.GetPermisosResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices

class GetPermisosService {

    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()

    suspend fun getPermisos(
        token: String, numCia: Long, anio: Long, numEmp: Long
    ): ApiResponceStatus<GetPermisosResponse> {

        return makeNetworkCall {
            val response = autservice.create(GetPermisosApiClient::class.java)
                .getPermisos(token, numCia, anio, numEmp)

            evaluateResponce(response.codigo.toString())
            response
        }
    }
}