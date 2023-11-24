package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.net

import com.venturessoft.human.humanrhdapp.core.ApiResponceStatus
import com.venturessoft.human.humanrhdapp.core.RetrofitDataSource
import com.venturessoft.human.humanrhdapp.core.evaluateResponce
import com.venturessoft.human.humanrhdapp.core.makeNetworkCall
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models.BusquedaUsuarioResponse
import com.venturessoft.human.humanrhdapp.ui.login.data.models.UsuariosListaResponse
import com.venturessoft.human.humanrhdapp.utilis.complements.TypeServices
import com.venturessoft.human.humanrhdapp.utilis.complements.User
class BusquedaEmpleadoRepositorio {
    val autservice = RetrofitDataSource(TypeServices.BASE).getRetrofit()
    suspend fun empleadoList(mr : Boolean): ApiResponceStatus<UsuariosListaResponse> {
        return makeNetworkCall {
            val response = autservice.create(BusquedaEmpleadoApiClient::class.java).getEmpleadoList(User.token, User.numCia, User.usuario, size = 30, mr)
            evaluateResponce(response.codigo.toString())
            response
        }
    }
    suspend fun busquedEmpleado(token: String, numCia: Long, supervisor: String, size: Long, mr: Boolean, cadenaBusqueda: String): ApiResponceStatus<BusquedaUsuarioResponse> {
        return makeNetworkCall {
            val response = autservice.create(BusquedaEmpleadoApiClient::class.java).getBusquedaEmpleadoList(token, numCia, supervisor, size, mr, cadenaBusqueda)
            evaluateResponce(response.codigo)
            response
        }
    }
}