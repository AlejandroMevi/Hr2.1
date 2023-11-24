package com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.net

import com.venturessoft.human.humanrhdapp.network.URL
import com.venturessoft.human.humanrhdapp.ui.home.ui.fragments.listEmployee.data.models.BusquedaUsuarioResponse
import com.venturessoft.human.humanrhdapp.ui.login.data.models.UsuariosListaResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface BusquedaEmpleadoApiClient {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.Login.EMPLEADO_LISTADO)
    suspend fun getEmpleadoList(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long,
        @Query("supervisor") supervisor: String,
        @Query("size") size: Long,
        @Query("mr") mr: Boolean,
    ) : UsuariosListaResponse
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET(URL.Login.BUSQUEDA_EMPLEADO)
    suspend fun getBusquedaEmpleadoList(
        @Header("Authorization") token: String,
        @Query("numCia") numCia: Long,
        @Query("supervisor") supervisor: String,
        @Query("size") size: Long,
        @Query("mr") mr: Boolean,
        @Query("cadenaBusqueda") cadenaBusqueda: String
    ): BusquedaUsuarioResponse
}